package util;

import lombok.extern.log4j.Log4j2;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

@Log4j2
public class MemoryUtil {

    private static final int dataSize = 1024 * 1024;

    public static void printMemory() {
        Memory memory = new Memory();
        log.info("Memory: " + StringUtil.toJsonString(memory));
    }

    private static class Memory {
        long totalMemory = 0L;
        long usedMemory  = 0L;
        long freeMemory  = 0L;
        int  numOfThreads = 0;

        Memory() {
            this.totalMemory = Runtime.getRuntime().totalMemory() / dataSize;
            this.freeMemory = Runtime.getRuntime().freeMemory() / dataSize;
            this.usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / dataSize;

            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            this.numOfThreads = bean.getThreadCount();
        }
    }
}
