import java.util.concurrent.RecursiveAction;

public class PortfolioThresholdTask extends RecursiveAction {
    private static final int THRESHOLD = 1000;
    private double[] assetValues;
    private int start;
    private int end;
    private double riskCap;

    public PortfolioThresholdTask(double[] assetValues, int start, int end, double riskCap) {
        this.assetValues = assetValues;
        this.start = start;
        this.end = end;
        this.riskCap = riskCap;
    }

    @Override
    protected void compute() {
        if ((end - start) <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                if (assetValues[i] > riskCap) {
                    assetValues[i] *= 0.95; // 5% penalty
                }
            }
        } else {
            int mid = start + (end - start) / 2;
            PortfolioThresholdTask left = new PortfolioThresholdTask(assetValues, start, mid, riskCap);
            PortfolioThresholdTask right = new PortfolioThresholdTask(assetValues, mid, end, riskCap);
            invokeAll(left, right);
        }
    }
}