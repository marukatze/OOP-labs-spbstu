package task1;

public class AbstractProgram {
    private ProgramState state = ProgramState.UNKNOWN;
    private Thread workerThread;

    public synchronized ProgramState getState() {
        return state;
    }

    public synchronized void setState(ProgramState newState) {
        if (this.state == newState) return; // no need to notify if current state and newState are the same
        this.state = newState;
        System.out.println("Состояние программы -> " + newState);
        notifyAll();
    }

    public synchronized void startProgram() {
        if (workerThread != null && workerThread.isAlive()) {
            setState(ProgramState.RUNNING);
            return;
        }

        workerThread = new Thread(this::workerRun);
        setState(ProgramState.RUNNING);
        workerThread.start();
    }

    private void workerRun() {
        while(true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
