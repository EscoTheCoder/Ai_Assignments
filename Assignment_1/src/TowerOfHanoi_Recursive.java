public class TowerOfHanoi_Recursive {
    static int i = 0;
    public static void TowerOfHanoi(int n, char from_tower, char to_tower, char extra_tower) {
        if (n <= 0) {
            return;
        }
        TowerOfHanoi(n - 1, from_tower, extra_tower, to_tower);

        i++;
        System.out.println(i + ") Move disk " + n + " from " + from_tower + " to " + to_tower);

        TowerOfHanoi(n - 1, extra_tower, to_tower, from_tower);
    }

    public static void main(String[] args) {
        int N = 3;
        TowerOfHanoi(N, 'A', 'C', 'B');
    }
}
