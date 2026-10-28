import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int datasetSize = 10000;
        double[] assets = new double[datasetSize];
        Random rand = new Random(42);

        for (int i = 0; i < datasetSize; i++) {
            assets[i] = 100 + rand.nextDouble() * 900;
        }

        ForkJoinPool pool = new ForkJoinPool();

        long start = System.nanoTime();
        ParallelRiskCalculator riskTask = new ParallelRiskCalculator(assets, 0, datasetSize);
        double totalRisk = pool.invoke(riskTask);

        PortfolioThresholdTask stressTestTask = new PortfolioThresholdTask(assets, 0, datasetSize, 500.0);
        pool.invoke(stressTestTask);
        long duration = System.nanoTime() - start;

        System.out.println("=== FORK/JOIN PARALLEL PORTFOLIO ENGINE ===");
        System.out.printf("Calculated Cumulative Risk Score: %.2f%n", totalRisk);
        System.out.printf("Parallel Processing Execution Time: %.3f ms%n", duration / 1e6);
    }
}