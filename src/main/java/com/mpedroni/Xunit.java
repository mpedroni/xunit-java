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

        public void run() {
            setUp();

            try {
                var method = this.getClass().getMethod(this.name);
                method.invoke(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

            tearDown();
        }

        public void tearDown() {}
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
            var wasRun = new WasRun("testMethod");
            wasRun.run();
            assertEquals(wasRun.log, "setUp testMethod tearDown ");
        }
    }

    public static void main(String[] args) {
        new TestCaseTest("testTemplateMethod").run();
    }
}