package com.mpedroni;

import java.util.ArrayList;
import java.util.List;

public class Xunit {
    static void assertEquals(Object a, Object b) {
        if (!a.equals(b)) {
            var error = new AssertionError(String.format("Expected \"%s\" but got \"%s\"", a, b));
            error.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var suite = new TestSuite();
        suite.add(new TestCaseTest("testTemplateMethod"));
        suite.add(new TestCaseTest("testResult"));
        suite.add(new TestCaseTest("testFailedResult"));
        suite.add(new TestCaseTest("testFailedResultFormatting"));
        suite.add(new TestCaseTest("testSuite"));
        suite.add(new TestCaseTest("testFailedSetUp"));

        var result = new TestResult();
        suite.run(result);

        System.out.println(result.summary());
    }

    static class TestCase {
        public String name;

        public TestCase(String name) {
            this.name = name;
        }

        public void setUp() {
        }

        public void run(TestResult result) {
            result.testStarted();

            try {
                setUp();
            } catch (Error error) {
                result.testFailed();
                return;
            }

            try {
                var method = this.getClass().getMethod(this.name);
                method.invoke(this);
            } catch (Exception e) {
                result.testFailed();
            }

            tearDown();
        }

        public void tearDown() {
        }
    }

    static class TestResult {
        private int runCount = 0;
        private int failedCount = 0;

        public String summary() {
            return String.format("%d run, %d failed", runCount, failedCount);
        }

        public void testStarted() {
            runCount += 1;
        }

        public void testFailed() {
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

        public void testBrokenMethod() throws Exception {
            throw new Exception("Broken method");
        }

        @Override
        public void tearDown() {
            this.log += "tearDown ";
        }
    }

    static class WasRunWithFailedSetUp extends WasRun {
        public WasRunWithFailedSetUp(String name) {
            super(name);
        }

        @Override
        public void setUp() {
            log += "setUp ";
            throw new Error("Failed setUp");
        }
    }

    static class TestSuite {
        private final List<TestCase> tests = new ArrayList<>();

        public void add(TestCase test) {
            tests.add(test);
        }

        public void run(TestResult result) {
            for (var test : tests) {
                test.run(result);
            }
        }
    }

    static class TestCaseTest extends TestCase {
        private TestResult result;

        public TestCaseTest(String name) {
            super(name);
        }

        @Override
        public void setUp() {
            result = new TestResult();
        }

        public void testTemplateMethod() {
            var test = new WasRun("testMethod");
            test.run(result);
            assertEquals(test.log, "setUp testMethod tearDown ");
        }

        public void testResult() {
            var test = new WasRun("testMethod");
            test.run(result);
            assertEquals(result.summary(), "1 run, 0 failed");
        }

        public void testFailedResult() {
            var test = new WasRun("testBrokenMethod");
            test.run(result);
            assertEquals(result.summary(), "1 run, 1 failed");
        }

        public void testFailedResultFormatting() {
            var result = new TestResult();
            result.testStarted();
            result.testFailed();
            assertEquals(result.summary(), "1 run, 1 failed");
        }

        public void testSuite() {
            var suite = new TestSuite();
            suite.add(new WasRun("testMethod"));
            suite.add(new WasRun("testBrokenMethod"));
            suite.run(result);
            assertEquals(result.summary(), "2 run, 1 failed");
        }

        public void testFailedSetUp() {
            var test = new WasRunWithFailedSetUp("testMethod");
            test.run(result);
            assertEquals(test.log, "setUp ");
            assertEquals(result.summary(), "1 run, 1 failed");
        }
    }
}