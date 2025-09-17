class KNIGHTS_TOUR {

    public static int N = 5;
    public int NN = N * N;
    public static int[][] board = new int[N][N];
    public static int[][] offsets;
    public static long trials = 0;

    public static void main(String[] args) {
        KNIGHTS_TOUR kt = new KNIGHTS_TOUR();
        kt.initialize();

        int startX = 1;
        int startY = 1;

        board[startX-1][startY-1] = 1;

        System.out.println("PART 1. Data");
        System.out.println(" 1) Board: " + N + "x" + N);
        System.out.println(" 2) Initial position: X="+(startX)+", "+ "Y="+(startY)+". "+ "L="+1+"."+"\n");

        System.out.println("PART 2. Trace");

        boolean yes = kt.TRY(startX-1, startY-1, 2, 1);

        System.out.println("PART 3. Results");
        if (yes) {
            System.out.println(" 1) Path is found. Trials=" + trials + ".");
            System.out.println(" 2) Path graphically:");
            System.out.println("Y,V");
            for (int y = N - 1; y >= 0; y--) {
                System.out.printf("%2d | ", (y + 1));
                for (int x = 0; x < N; x++) {
                    System.out.printf("%2d ", board[x][y]);
                }
                System.out.println();
            }
            System.out.print("   ------------------X,U\n    ");
            for (int x = 1; x <= N; x++) {
                System.out.printf(" %2d", x);
            }
            System.out.println();
        } else {
            System.out.println("Path does not exist. Trials=" + trials + ".");
        }
    }

    public void initialize() {
        offsets = new int[][]{
                {2, 1}, {1, 2}, {-1, 2}, {-2, 1},
                {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
        };
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = 0;
            }
        }
    }

    public boolean TRY(int X, int Y, int L, int depth) {
        if (L > NN) {
            return true; //all cells covered
        }

        int move = 0;
        for (int[] offset : offsets) {
            move++;
            trials++;

            int U = X + offset[0];
            int V = Y + offset[1];

            //prefix with - according to depth
            String prefix = "-".repeat(depth - 1);

            if (U >= 0 && U < N && V >= 0 && V < N) {
                if (board[U][V] != 0) {
                    //already visited
                    System.out.printf(" %d) %sR%d. U=%d, V=%d. L=%d. Thread.%n",
                            trials, prefix, move, U + 1, V + 1, L);
                    continue;
                }

                //free to move
                System.out.printf(" %d) %sR%d. U=%d, V=%d. L=%d. Free. BOARD[%d,%d]:=%d.%n",
                        trials, prefix, move, U + 1, V + 1, L, U + 1, V + 1, L);

                board[U][V] = L;

                if (TRY(U, V, L + 1, depth + 1)) {
                    return true;
                } else {
                    //backtrack
                    board[U][V] = 0;
                    System.out.print("Backtrack.\n");
                }

            } else {
                //ektos oriown
                System.out.printf(" %d) %sR%d. U=%d, V=%d. L=%d. Out.%n",
                        trials, prefix, move, U + 1, V + 1, L);
            }
        }
        return false;
    }
}