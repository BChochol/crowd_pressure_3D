package agh.projects.crowd_pressure.engine.simulation.computation.thread;

import agh.projects.crowd_pressure.engine.simulation.computation.task.Task;

import java.util.concurrent.CountDownLatch;

public class WorkerThread implements Runnable {

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
            System.out.println("Computation thread \"" + Thread.currentThread().getName() + "\" is dead. Details: " + exception.getMessage()); // todo: use logger
        }
        if (cdl != null) cdl.countDown();
    }
}
