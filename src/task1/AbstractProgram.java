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
}
