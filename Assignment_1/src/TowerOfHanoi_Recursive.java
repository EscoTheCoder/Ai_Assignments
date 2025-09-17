import java.util.Scanner;
import java.util.Stack;

public class TowerOfHanoi_Recursive {

    static int i = 0;
    static Stack<Integer> A = new Stack<>();
    static Stack<Integer> B = new Stack<>();
    static Stack<Integer> C = new Stack<>();

    public static void TowerOfHanoi(int n, char from_tower, char to_tower, char extra_tower) {
        if (n <= 0) {
            return;
        }
        TowerOfHanoi(n - 1, from_tower, extra_tower, to_tower);

        i++;
        moveDisk(from_tower, to_tower , n);
        TowerOfHanoi(n - 1, extra_tower, to_tower, from_tower);
    }

    public static void moveDisk(char from, char to, int disk) {
        Stack<Integer> tower_a = getRod(from);
        Stack<Integer> tower_c = getRod(to);

        tower_c.push(tower_a.pop());

        // Print step
        System.out.print(i + ". Move disk " + disk + " from " + from + " to " + to + ".  ");
        printState();
    }

    // Helper to return correct rod
    public static Stack<Integer> getRod(char rod) {
        if(rod=='A'){
            return A;
        }
        else if(rod=='B'){
            return B;
        }
        else if(rod=='C'){
            return C;
        }
        else {
            throw new IllegalArgumentException("Invalid rod: " + rod);
        }
    }

    // Print current state of rods
    public static void printState() {
        System.out.print("A=");
        convert_stack_to_tuple(A);
        System.out.print(", ");
        System.out.print("B=");
        convert_stack_to_tuple(B);
        System.out.print(", ");
        System.out.print("C=");
        convert_stack_to_tuple(C);
        System.out.print(".");
        System.out.println();
    }

    public static void convert_stack_to_tuple(Stack<Integer> s) {
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
        Scanner sc = new Scanner(System.in);
        System.out.print("Give N: ");
        int N = sc.nextInt();

        for (int i = N; i >= 1; i--) {
            A.push(i);
        }

        System.out.print("Initial state  ");
        printState();

        TowerOfHanoi(N, 'A', 'C', 'B');
        sc.close();
    }
}
