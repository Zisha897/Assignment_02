public class AsyncWebEngineApp {
    public static void main(String[] args) {
        System.out.println("Default Master Thread: " + Thread.currentThread().getName());

        TelemetryThread telemetryWorker = new TelemetryThread();
        telemetryWorker.setName("Telemetry-Worker");

        Thread logWorker = new Thread(new LogProcessorTask(), "Log-Worker");

        System.out.println("Spawning concurrent execution threads...");
        telemetryWorker.start();
        logWorker.start();

        System.out.println("State - " + telemetryWorker.getName() + ": " + telemetryWorker.getState());
        System.out.println("State - " + logWorker.getName() + ": " + logWorker.getState());
    }
}