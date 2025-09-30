package task1;

public class AbstractProgram {
    private volatile ProgramState state = ProgramState.UNKNOWN;
    private Thread workerThread;

    public AbstractProgram(Thread workerThread) {
        this.workerThread = workerThread;
    }

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
        if (workerThread != null && workerThread.isAlive()) {
            setState(ProgramState.RUNNING);
            return;
        }

        setState(ProgramState.RUNNING);
        workerThread.start();
    }

    public synchronized void stopProgram() {
        if (workerThread == null || !workerThread.isAlive()) {
            setState(ProgramState.STOPPING);
            return;
        }

        setState(ProgramState.STOPPING);
        workerThread.interrupt();
        try {
            workerThread.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        workerThread = null;
    }

    public synchronized void killProgram() {
        setState(ProgramState.FATAL_ERROR);
        workerThread.interrupt();
        try {
            workerThread.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        workerThread = null;
    }
}
