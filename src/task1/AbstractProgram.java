package task1;

class AbstractProgram implements Runnable {
    private ProgramState state = ProgramState.UNKNOWN;

    public synchronized ProgramState getState() {
        return state;
    }

    public synchronized void setState(ProgramState newState) {
        this.state = newState;
        System.out.println("Состояние программы -> " + newState);
        notifyAll();
    }

    @Override
    public void run() {
        // in process
    }
}
