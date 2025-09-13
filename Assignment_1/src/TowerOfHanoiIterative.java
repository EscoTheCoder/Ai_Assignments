import java.util.*;

public class TowerOfHanoiIterative {
    public static int counter = 0;
    static char[] towers = {'A', 'B', 'C'};
    static Stack<Integer>[] stacks = new Stack[3];

    static {
        for (int i = 0; i < 3; i++) {
            stacks[i] = new Stack<>();
        }
    }
    public static void moveDisk(int a, int b) {
        if (stacks[b].isEmpty() || (!stacks[a].isEmpty() && stacks[a].peek() < stacks[b].peek())) {
            counter++;
            System.out.println(counter + ") Move disk " + stacks[a].peek() + " from " + towers[a] + " to " + towers[b]);
            stacks[b].push(stacks[a].pop());
        } else {
            moveDisk(b, a);
        }
    }

    public static void towerOfHanoi(int n) {
        int first = 0, second = 1, third = 2;

        for (int i = n; i >= 1; i--) {
            stacks[first].push(i);
        }

        int totalMoves = (int) Math.pow(2, n) - 1;

        if (n % 2 == 0) {
            int temp = second;
            second = third;
            third = temp;
        }

        for (int i = 1; i <= totalMoves; i++) {
            if (i % 3 == 1) {
                moveDisk(first, third);
            } else if (i % 3 == 2) {
                moveDisk(first, second);
            } else {
                moveDisk(second, third);
            }
        }
    }

    public static void main(String[] args) {
        int n = 3;
        towerOfHanoi(n);
    }
}
