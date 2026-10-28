public class TradingEngineApp {
    public static void main(String[] args) {
        System.out.println("=== COMPLEX EVENT PROCESSING (CEP) ENGINE ===");
        
        TradeEvent fixEvent = new FIXOrderEvent("FIX_GW_01", System.currentTimeMillis(), 1500.00, "ORD-99281");
        TradeEvent reutersEvent = new ReutersQuoteEvent("REUTERS_GW_02", System.currentTimeMillis(), 1500.00, 1499.50, 1500.25);

        fixEvent.dispatch();
        reutersEvent.dispatch();
    }
}