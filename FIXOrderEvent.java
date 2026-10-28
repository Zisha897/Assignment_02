public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;

    public FIXOrderEvent(String senderId, long timestamp, double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }

    @Override
    public void dispatch() {
        double totalWithFee = basePrice * 1.0015; // 0.15% fee
        System.out.printf("[FIX Gateway Adapter] Stock Purchase Executed | Client Order ID: %s | Sender: %s | Base: $%.2f | Total (inc 0.15%% fee): $%.2f%n",
                clOrdId, senderId, basePrice, totalWithFee);
    }
}