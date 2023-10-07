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
        }
    }


    static class WasRun extends TestCase {
        public boolean wasRun;
        public boolean wasSetUp;

        public WasRun(String name) {
            super(name);
            this.wasRun = false;
            this.wasSetUp = false;
        }


        @Override
        public void setUp() {
            this.wasSetUp = true;
        }

        public void testMethod() {
            this.wasRun = true;
        }
    }

    static class TestCaseTest extends TestCase {
        public TestCaseTest(String name) {
            super(name);
        }

        public void testRunning() {
            var wasRun = new WasRun("testMethod");
            assertFalse(wasRun.wasRun);
            wasRun.run();
            assertTrue(wasRun.wasRun);
        }

        public void testSetUp() {
            var wasRun = new WasRun("testMethod");
            wasRun.run();
            assertTrue(wasRun.wasSetUp);
        }
    }

    public static void main(String[] args) {
        new TestCaseTest("testRunning").run();
        new TestCaseTest("testSetUp").run();
    }
}