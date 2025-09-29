package task1;

public class Supervisor implements Runnable {
    private final AbstractProgram program;

    public Supervisor(AbstractProgram program) {
        this.program = program;
    }

    @Override
    public void run() {
        // in progress
    }
}
