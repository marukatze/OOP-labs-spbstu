package task1;

public abstract class AbstractProgram {
    private volatile ProgramState state = ProgramState.UNKNOWN;
    private Thread workerThread;
    private volatile boolean shouldStop = false;

    public synchronized ProgramState getState() {
        return state;
    }

    public synchronized void setState(ProgramState newState) {
        if (this.state == newState) return;

        this.state = newState;
        System.out.println("Состояние программы -> " + newState);
        notifyAll();
    }

    public synchronized void startProgram() {
        if (workerThread != null && workerThread.isAlive()) return;

        shouldStop = false;
        setState(ProgramState.RUNNING);

        workerThread = new Thread(this::workLoop);
        workerThread.start();
    }

    public void stopProgram() {
        shouldStop = true;
        setState(ProgramState.STOPPING);
        waitForWorker();
    }

    public void killProgram() {
        shouldStop = true;
        setState(ProgramState.FATAL_ERROR);
        waitForWorker();
    }

    private void waitForWorker() {
        if (workerThread != null) {
            try {
                workerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            workerThread = null;
        }
    }

    private void workLoop() {
        try {
            while (!shouldStop) doWork();
        } finally {
            cleanup();
        }
    }

    public abstract void doWork();
    public abstract void cleanup();
}
