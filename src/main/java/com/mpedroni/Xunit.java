package com.mpedroni;

public class Xunit {
    static class TestCase {
        public String name;

        public TestCase(String name) {
            this.name = name;
        }

        public void run() {
            try {
                var method = this.getClass().getMethod(this.name);
                method.invoke(this);
            } catch (Exception e) {}
        }
    }


    static class WasRun extends TestCase {
        public boolean wasRun;

        public WasRun(String name) {
            super(name);
            this.wasRun = false;
        }

        public void testMethod() {
            this.wasRun = true;
        }
    }

    public static void main(String[] args) {
        var wasRun = new WasRun("testMethod");
        System.out.println(wasRun.wasRun);
        wasRun.run();
        System.out.println(wasRun.wasRun);
    }
}