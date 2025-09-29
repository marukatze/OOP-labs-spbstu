package task1;

public class AbstractProgram implements Runnable {
    private ProgramState state = ProgramState.UNKNOWN;

    public synchronized ProgramState getState() {
        return state;
    }

    public synchronized void setState(ProgramState newState) {
        if (this.state == newState) return; // no need to notify if current state and newState are the same
        this.state = newState;
        System.out.println("Состояние программы -> " + newState);
        notifyAll();
    }

    @Override
    public void run() {
        // in process
    }
}
