package agh.projects.crowd_pressure.engine.simulation.computation.thread;

import agh.projects.crowd_pressure.engine.simulation.computation.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class WorkerThread implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Task task;
    private final CountDownLatch cdl;

    public WorkerThread(Task task) {
        this.task = task;
        this.cdl = null;
    }

    public WorkerThread(Task task, CountDownLatch cdl) {
        this.task = task;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try {
            task.execute();
        } catch (Exception exception) {
            logger.error(String.format("Computation thread '%s' is dead - omitting task. Details [%s]", Thread.currentThread().getName(), exception.getMessage()), exception);
        }
        if (cdl != null) cdl.countDown();
    }
}
