import java.util.Random;

public class SensorGridMonitorApp {
    private static double globalMin = Double.MAX_VALUE;
    private static double globalMax = -Double.MAX_VALUE;

    public static void main(String[] args) throws InterruptedException {
        int totalElements = 50000;
        double[] gridData = new double[totalElements];
        Random rand = new Random(100);

        for (int i = 0; i < totalElements; i++) {
            gridData[i] = -20 + rand.nextDouble() * 120;
        }

        int threads = 4;
        int chunkSize = totalElements / threads;
        Thread[] minWorkers = new Thread[threads];
        Thread[] maxWorkers = new Thread[threads];

        double[] minResults = new double[threads];
        double[] maxResults = new double[threads];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threads; i++) {
            final int tIdx = i;
            int start = i * chunkSize;
            int end = (i == threads - 1) ? totalElements : (i + 1) * chunkSize;

            minWorkers[i] = new Thread(() -> {
                ParallelMinSearch minEngine = new ParallelMinSearch(gridData, start, end);
                minResults[tIdx] = minEngine.processGrid();
            });

            maxWorkers[i] = new Thread(() -> {
                ParallelMaxSearch maxEngine = new ParallelMaxSearch(gridData, start, end);
                maxResults[tIdx] = maxEngine.processGrid();
            });

            minWorkers[i].start();
            maxWorkers[i].start();
        }

        for (int i = 0; i < threads; i++) {
            minWorkers[i].join();
            maxWorkers[i].join();
            if (minResults[i] < globalMin) globalMin = minResults[i];
            if (maxResults[i] > globalMax) globalMax = maxResults[i];
        }

        long executionTime = System.currentTimeMillis() - startTime;

        System.out.println("=== PARALLEL IOT SENSOR GRID REDUCTION ===");
        System.out.printf("Global Min Temperature: %.2f °C%n", globalMin);
        System.out.printf("Global Peak Vibration Spike: %.2f%n", globalMax);
        System.out.println("Parallel Execution Time: " + executionTime + " ms");
    }
}