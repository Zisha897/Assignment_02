public class RequestQueue {
    private char[] queue;
    private int front;
    private int rear;
    private int size;

    public RequestQueue(int capacity) {
        queue = new char[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(char c) {
        if (size < queue.length) {
            rear = (rear + 1) % queue.length;
            queue[rear] = c;
            size++;
        }
    }

    public char dequeue() {
        if (!isEmpty()) {
            char val = queue[front];
            front = (front + 1) % queue.length;
            size--;
            return val;
        }
        return '\0';
    }

    public boolean isEmpty() {
        return size == 0;
    }
}