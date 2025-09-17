import java.util.Scanner;
import java.util.Stack;

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
            int disk = stacks[a].peek();
            stacks[b].push(stacks[a].pop());

            System.out.print(counter + ". Move disk " + disk + " from " + towers[a] + " to " + towers[b] + ".  ");
            printState();
        } else {
            moveDisk(b, a);
        }
    }

    public static void towerOfHanoi(int n) {
        int first = 0, second = 1, third = 2;


        for (int i = n; i >= 1; i--) {
            stacks[first].push(i);
        }


        System.out.print("Initial state  ");
        printState();

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

    public static void printState() {
        System.out.print("A=");
        convertStackToTuple(stacks[0]);
        System.out.print(", B=");
        convertStackToTuple(stacks[1]);
        System.out.print(", C=");
        convertStackToTuple(stacks[2]);
        System.out.println(".");
    }

    public static void convertStackToTuple(Stack<Integer> s) {
        System.out.print("(");
        for (int j = 0; j < s.size(); j++) {
            System.out.print(s.get(j));
            if (j < s.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.print(")");
    }

    public static void main(String[] args) {
        Scanner obj = new Scanner(System.in);
        System.out.print("Give N: ");
        int N = obj.nextInt();
        towerOfHanoi(N);
        obj.close();
    }
}
