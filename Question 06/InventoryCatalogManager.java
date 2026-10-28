import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InventoryCatalogManager {
    private static final int NUM_THREADS = 10;
    private static final int OPS_PER_THREAD = 50000;

    public void benchmarkSynchronizedMap() throws InterruptedException {
        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
        for (int i = 0; i < 1000; i++) syncMap.put("PROD_" + i, 100);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < OPS_PER_THREAD; j++) {
                    String id = "PROD_" + (j % 1000);
                    syncMap.put(id, syncMap.get(id) + 1);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long elapsed = System.currentTimeMillis() - startTime;
        long totalOps = (long) NUM_THREADS * OPS_PER_THREAD;
        double throughput = (totalOps / (elapsed / 1000.0));

        System.out.println("--- SynchronizedMap Benchmark ---");
        System.out.println("Time Elapsed: " + elapsed + " ms");
        System.out.printf("Throughput: %.2f ops/sec%n%n", throughput);
    }

    public void benchmarkConcurrentMap() throws InterruptedException {
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 1000; i++) concurrentMap.put("PROD_" + i, 100);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < OPS_PER_THREAD; j++) {
                    String id = "PROD_" + (j % 1000);
                    concurrentMap.compute(id, (k, v) -> (v == null ? 1 : v + 1));
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long elapsed = System.currentTimeMillis() - startTime;
        long totalOps = (long) NUM_THREADS * OPS_PER_THREAD;
        double throughput = (totalOps / (elapsed / 1000.0));

        System.out.println("--- ConcurrentHashMap Benchmark ---");
        System.out.println("Time Elapsed: " + elapsed + " ms");
        System.out.printf("Throughput: %.2f ops/sec%n", throughput);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== E-COMMERCE INVENTORY CATALOG BENCHMARK ===");
        InventoryCatalogManager manager = new InventoryCatalogManager();
        manager.benchmarkSynchronizedMap();
        manager.benchmarkConcurrentMap();
    }
}