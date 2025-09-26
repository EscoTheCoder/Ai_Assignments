import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class KNIGHTS_TOUR {
    private static int N;
    private static int[][] board;
    private static final int[] moveX = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] moveY = {1, 2, 2, 1, -1, -2, -2, -1};
    private static int trials;
    private static StringBuilder shortLog;
    private static boolean printOnScreen = true;

    private static FileWriter longWriter; // stream για το long αρχείο

    private static final int TRIAL_LIMIT_FOR_SCREEN = 250000; // πάνω από αυτό → όχι στο terminal

    public static void main(String[] args) {
        KNIGHTS_TOUR kt = new KNIGHTS_TOUR();

        // 7 tests
        kt.func(5, 0, 0); // Test 1
        kt.func(5, 4, 0); // Test 2
        kt.func(5, 0, 4); // Test 3
        kt.func(5, 1, 0); // Test 4
        kt.func(6, 0, 0); // Test 5
        kt.func(7, 0, 0); // Test 6
        kt.func(4, 0, 0); // Test 7
    }

    public void func(int size, int startX, int startY) {
        N = size;
        board = new int[N][N];
        for (int[] row : board) Arrays.fill(row, -1);
        board[startX][startY] = 1; // start position

        trials = 0;
        shortLog = new StringBuilder();

        String shortFile = String.format("out-short-%dx%d-%d-%d.txt", N, N, startX + 1, startY + 1);
        String longFile = String.format("out-long-%dx%d-%d-%d.txt", N, N, startX + 1, startY + 1);

        try {
            longWriter = new FileWriter(longFile);

            // PART 1
            String part1 = "PART 1. Data\n" +
                    "1) Board: " + N + "x" + N + "\n" +
                    "2) Initial position: X=" + (startX + 1) + ", Y=" + (startY + 1) + ", L=1.\n";
            shortLog.append(part1);
            longWriter.write(part1);
            longWriter.write("PART 2. Trace\n");

            // solve
            boolean found = solveKnightTour(startX, startY, 1, 0);

            // PART 3
            String part3;
            if (found) {
                part3 = "\nPART 3. Results\n1) Path is found. Trials=" + trials + "\n";
                shortLog.append(part3);
                longWriter.write(part3);
                printBoard(); // append board to shortLog and longWriter
            } else {
                part3 = "\nPART 3. Results\nNo tour. Trials=" + trials + "\n";
                shortLog.append(part3);
                longWriter.write(part3);
            }

            // write short file
            writeToFile(shortFile, shortLog.toString());

            // --- Terminal output ---
            if (printOnScreen) {
                if (trials > TRIAL_LIMIT_FOR_SCREEN) {
                    // Μόνο περίληψη
                    System.out.printf("N=%d, X=%d, Y=%d → %s Trials=%d (μόνο στο αρχείο)\n",
                            N, startX + 1, startY + 1, (found ? "Path found." : "No tour."), trials);
                } else {
                    // Κανονικά δείξε όλο το shortLog
                    System.out.println(shortLog);
                }
            }

            longWriter.close();

        } catch (IOException e) {
            System.err.println("Error writing to file " + longFile);
        }
    }

    private static boolean solveKnightTour(int x, int y, int moveNum, int depth) throws IOException {
        if (moveNum == N * N) return true;

        for (int i = 0; i < 8; i++) {
            int nextX = x + moveX[i];
            int nextY = y + moveY[i];
            trials++;

            String indent = (depth == 0) ? "" : "-".repeat(depth);

            if (isValidMove(nextX, nextY)) {
                board[nextX][nextY] = moveNum + 1;

                longWriter.write(String.format("%d) %sR%d. U=%d, V=%d. L=%d. Free. BOARD[%d,%d]:=%d.\n",
                trials, indent, i + 1, nextX + 1, nextY + 1, moveNum + 1,
                nextX + 1, nextY + 1, moveNum + 1));
                if (solveKnightTour(nextX, nextY, moveNum + 1, depth + 1)) return true;

                board[nextX][nextY] = -1;
                longWriter.write(String.format("%d) %sR%d. U=%d, V=%d. L=%d. Backtrack.\n",
                trials, indent, i + 1, nextX + 1, nextY + 1, moveNum + 1));
            } 
            else {
                String status = (nextX < 0 || nextX >= N || nextY < 0 || nextY >= N) ? "Out." : "Thread.";
                longWriter.write(String.format("%d) %sR%d. U=%d, V=%d. L=%d. %s\n",
                trials, indent, i + 1, nextX + 1, nextY + 1, moveNum + 1, status));
            }
        }
        return false;
    }

    private static boolean isValidMove(int x, int y) {
        return (x >= 0 && x < N && y >= 0 && y < N && board[x][y] == -1);
    }

    private static void printBoard() throws IOException {
        shortLog.append("2) Path graphically:\n\n");
        longWriter.write("2) Path graphically:\n\n");

        shortLog.append("Y,V^\n");
        longWriter.write("Y,V^\n");

        for (int y = N - 1; y >= 0; y--) {
            String rowLabel = String.format("%2d | ", (y + 1));
            shortLog.append(rowLabel);
            longWriter.write(rowLabel);
            for (int x = 0; x < N; x++) {
                String cell = String.format("%3d ", board[x][y]);
                shortLog.append(cell);
                longWriter.write(cell);
            }
            shortLog.append("\n");
            longWriter.write("\n");
        }

        shortLog.append("   -----------------------> X,U\n    ");
        longWriter.write("   -----------------------> X,U\n    ");
        for (int x = 1; x <= N; x++) {
            String label = String.format(" %3d", x);
            shortLog.append(label);
            longWriter.write(label);
        }
        shortLog.append("\n");
        longWriter.write("\n");
    }

    private static void writeToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename);
        }
    }
}
