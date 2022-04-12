package org.ntnu.k2.g2.quizmaker.data;

public class Util {
    /**
     * Checks whenever or not the database is running inside a test by searching through the stack trace.
     * @return True if the code is running inside a test, false if not.
     */
    static boolean isTest() {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraces) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}
