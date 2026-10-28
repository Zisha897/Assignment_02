public class PacketSymmetryAuditor {
    public static void main(String[] args) {
        String networkPayload = "TXN_HEADER_REDACTED_HEADER_TXN"; 
        int len = networkPayload.length();

        TransactionStack stack = new TransactionStack(len);
        RequestQueue queue = new RequestQueue(len);

        for (int i = 0; i < len; i++) {
            char c = networkPayload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }

        boolean isSymmetric = true;
        while (!stack.isEmpty() && !queue.isEmpty()) {
            if (stack.pop() != queue.dequeue()) {
                isSymmetric = false;
                break;
            }
        }

        System.out.println("=== ZERO-TRUST API GATEWAY PACKET AUDITOR ===");
        System.out.println("Ingested Payload: " + networkPayload);
        System.out.println("Bi-directional Payload Symmetry (Palindrome): " + isSymmetric);
        if (isSymmetric) {
            System.out.println("Status: PACKET VERIFIED AND ACCEPTED.");
        } else {
            System.out.println("Status: PACKET CORRUPTED / REJECTED.");
        }
    }
}