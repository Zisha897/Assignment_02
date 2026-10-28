public class CacheLocalityBenchmark {
    private static final int N = 2000;
    private static double[][] matrix = new double[N][N];

    public static double traverseRowMajor() {
        double sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public static double traverseColumnMajor() {
        double sum = 0;
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = i + j;
            }
        }

        int iterations = 10;
        long totalRowTime = 0;
        long totalColTime = 0;

        traverseRowMajor();
        traverseColumnMajor();

        for (int k = 0; k < iterations; k++) {
            long start = System.nanoTime();
            traverseRowMajor();
            totalRowTime += (System.nanoTime() - start);

            start = System.nanoTime();
            traverseColumnMajor();
            totalColTime += (System.nanoTime() - start);
        }

        double avgRowMs = (totalRowTime / (double) iterations) / 1e6;
        double avgColMs = (totalColTime / (double) iterations) / 1e6;
        double speedup = avgColMs / avgRowMs;

        System.out.println("=== CPU CACHE LOCALITY BENCHMARK (2000x2000) ===");
        System.out.printf("Average Row-Major Time: %.3f ms%n", avgRowMs);
        System.out.printf("Average Column-Major Time: %.3f ms%n", avgColMs);
        System.out.printf("Row-Major Speedup Factor: %.2fx faster%n", speedup);
    }
}