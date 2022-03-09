package simulator.launcher;



public class MainRunnerV2 {



    static private void run(String mode, Integer ticks) {
        try {
            simulator.launcher.Main.main(new String[]{"-m", mode, "-t", ticks.toString()  });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) {
        run("gui", 150);
    }
}
