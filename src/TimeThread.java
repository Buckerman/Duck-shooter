public class TimeThread extends Thread implements Runnable {

    private static int hours = 0;
    private static int minutes = 0;
    private static int seconds = 0;
    private static int secundos = 0;

    private static int harder = 0;
    private static boolean isRunning = true;

    @Override
    public void run() {
        synchronized (this) {
            while (isRunning) {
                try {
                    if (secundos % 5 == 0 && secundos > 0) harder += 20;
                    if (seconds == 60) {
                        seconds = 0;
                        minutes += 1;
                        if (minutes == 60) {
                            minutes = 0;
                            hours += 1;
                        }
                    }
                    Thread.sleep(1000);

                } catch (InterruptedException ignored) {}
                seconds++;
                secundos++;
            }
        }
    }

    public void setIsRunning(boolean isRunning) {
        TimeThread.isRunning = isRunning;
    }

    public static String getTime() {
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }

    public void stopTime() {
        isRunning = false;
        harder = 0;
        secundos = -1;
        hours = 0;
        minutes = 0;
        seconds = -1;
    }

    public static int getHarder() {
        return harder;
    }
}
