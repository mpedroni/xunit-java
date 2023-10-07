package com.mpedroni;

public class Xunit {
    static class WasRun {
        public boolean wasRun;

        public WasRun(String name) {
            this.wasRun = false;
        }

        public void run() {
            this.testMethod();
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