package com.company;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {

        /**
         * 1. Instrumentation Layer -> PlainLogger
         * 2. MBeanServer
         * 3. JConsole/ Java VisualVM
         */

        PlainLogger plainLogger = new PlainLogger();
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(plainLogger, new ObjectName("profiling", "name", "plainLogger"));


        while (true) {
	        Thread.sleep(1000);
	        plainLogger.execute();
        }
    }
}

class PlainLogger implements PlainLoggerMBean {

    private boolean isProfilingEnabled = false;
    private String name = null;


    public boolean isProfilingEnabled() {
        return isProfilingEnabled;
    }

    public void setProfilingEnabled(boolean profilingEnabled) {
        isProfilingEnabled = profilingEnabled;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void execute() throws InterruptedException {
        System.out.println();

        long start = System.currentTimeMillis();

        if (isProfilingEnabled) {
            System.out.println("Staring profiling");
        }

        int randomInt = new Random().nextInt(3000);
        System.out.println("Method starts executing");
        if (name != null) {
            System.out.printf("Name is %s\n", name);
        }
        Thread.sleep(randomInt);
        System.out.println("Method finished executing");

        if (isProfilingEnabled) {
            long end = System.currentTimeMillis();
            System.out.println("Finished profiling");
            System.out.printf("Method execution took %s\n", (end - start));
        }

        System.out.println();

    }
}

