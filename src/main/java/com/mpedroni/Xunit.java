package com.mpedroni;

public class Xunit {
    static void assertTrue(Boolean condition) {
        if (!condition)
            throw new AssertionError();
    }

    static void assertFalse(Boolean condition) {
        if (condition)
            throw new AssertionError();
    }

    static  void assertEquals(Object a, Object b) {
        if(!a.equals(b))
            throw new AssertionError();
    }

    static class TestCase {
        public String name;

        public TestCase(String name) {
            this.name = name;
        }

        public void setUp() {}

        public TestResult run() {
            setUp();
            var result = new TestResult();

            try {
                var method = this.getClass().getMethod(this.name);
                method.invoke(this);
                result.run();
            } catch (Exception e) {
                e.printStackTrace();
            }

            tearDown();

            return result;
        }

        public void tearDown() {}
    }

    static class TestResult {
        private int runCount = 0;
        private int failedCount = 0;

        public String summary() {
            return String.format("%d run, %d failed", runCount, failedCount);
        }

        public void run() {
            runCount += 1;
        }

        public void failed() {
            failedCount += 1;
        }
    }


    static class WasRun extends TestCase {
        public boolean wasRun;
        public String log;

        public WasRun(String name) {
            super(name);
            this.wasRun = false;
            this.log = "";
        }


        @Override
        public void setUp() {
            this.log += "setUp ";
        }

        public void testMethod() {
            this.log += "testMethod ";
            this.wasRun = true;
        }

        @Override
        public void tearDown() {
            this.log += "tearDown ";
        }
    }

    static class TestCaseTest extends TestCase {
        public TestCaseTest(String name) {
            super(name);
        }

        public void testTemplateMethod() {
            var test = new WasRun("testMethod");
            test.run();
            assertEquals(test.log, "setUp testMethod tearDown ");
        }

        public void testResult() {
            var test = new WasRun("testMethod");
            var result = test.run();
            assertEquals(result.summary(), "1 run, 0 failed");
        }

        public void testFailedResultFormatting() {
            var result = new TestResult();
            result.run();
            result.failed();
            assertEquals(result.summary(), "1 run, 1 failed");
        }
    }

    public static void main(String[] args) {
        new TestCaseTest("testTemplateMethod").run();
        new TestCaseTest("testResult").run();
        new TestCaseTest("testFailedResultFormatting").run();
    }
}