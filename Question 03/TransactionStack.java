public class TransactionStack {
    private char[] stack;
    private int top;

    public TransactionStack(int capacity) {
        stack = new char[capacity];
        top = -1;
    }

    public void push(char c) {
        if (top < stack.length - 1) {
            stack[++top] = c;
        }
    }

    public char pop() {
        if (!isEmpty()) {
            return stack[top--];
        }
        return '\0';
    }

    public char peek() {
        if (!isEmpty()) {
            return stack[top];
        }
        return '\0';
    }

    public boolean isEmpty() {
        return top == -1;
    }
}