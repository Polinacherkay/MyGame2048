package game;

import java.util.Random;

public class Game {

    public static final int SIZE = 4;
    static final int WIN_SCORE = 2048;
    private int[][] matrix = new int[SIZE][SIZE];
    private static Random random = new Random();
    private int score = 0;
    private Status status;

    public Game()
    {
        initialize();
    }

    int get(int i, int j)
    {
        return matrix[i][j];
    }

    public Status getStatus() {
        return status;
    }

    private void clear()
    {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public void initialize() {
        status = Status.Played;
        score = 0;
        clear();

        for (int i = 0; i < 2; i++) {
            generateNumber();
        }
    }

    private void generateNumber()
    {
        if (checkEmpty()) {
            int k = random.nextInt(10);
            int data;
            if (k < 9)
                data = 2;
            else
                data = 4;

            int row, column;
            do {
                row = random.nextInt(SIZE);
                column = random.nextInt(SIZE);
            }
            while (matrix[row][column] != 0);

            matrix[row][column] = data;
        }
        else return;
    }

    private void moveTop(int row, int column)
    {
        int data = matrix[row][column];
        matrix[row][column] = 0;
        int endRow = row;
        int endColumn = column;
        boolean isDelete = false;

        for (int i = row - 1; i >= 0 ; i--) {
            if (matrix[i][column] != 0)
            {
                if (matrix[i][column] == data)
                {
                    sideMove(i, column);
                    isDelete = true;
                }
                break;
            }
            endRow--;
        }
        if (!isDelete)
            matrix[endRow][endColumn] = data;
    }

    private void moveBottom(int row, int column)
    {
        int data = matrix[row][column];
        matrix[row][column] = 0;
        int endRow = row;
        int endColumn = column;
        boolean isDelete = false;
        int j = column;

        for (int i = row + 1; i < SIZE; i++) {
            if (matrix[i][column] != 0)
            {
                if (matrix[i][column] == data)
                {
                    sideMove(i, column);
                    isDelete = true;
                }
                break;
            }
            endRow++;
        }
        if (!isDelete)
            matrix[endRow][endColumn] = data;
    }

    private void moveLeft(int row, int column)
    {
        int data = matrix[row][column];
        matrix[row][column] = 0;
        int endRow = row;
        int endColumn = column;
        boolean isDelete = false;
        int i = row;

        for (int j = column - 1; j >= 0; j--) {
            if (matrix[row][j] != 0)
            {
                if (matrix[row][j] == data)
                {
                    sideMove(row, j);
                    isDelete = true;
                }
                break;
            }
            endColumn--;
        }
        if (!isDelete)
            matrix[endRow][endColumn] = data;
    }

    private void moveRight(int row, int column)
    {
        int data = matrix[row][column];
        matrix[row][column] = 0;
        int endRow = row;
        int endColumn = column;
        boolean isDelete = false;
        int i = row;

        for (int j = column + 1; j < SIZE; j++) {
            if (matrix[row][j] != 0)
            {
                if (matrix[row][j] == data)
                {
                    sideMove(row, j);
                    isDelete = true;
                }
                break;
            }
            endColumn++;
        }
        if (!isDelete)
            matrix[endRow][endColumn] = data;
    }

    private void sideMove(int i, int j)
    {
        matrix[i][j] *= 2;
        if (matrix[i][j] == WIN_SCORE)
            status = Status.Win;
        score += matrix[i][j];
    }

    private boolean checkEmpty()
    {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] == 0)
                    return true;
            }
        }
        return false;
    }

    private void checkMove()
    {
        if (checkEmpty())
            return;

        for (int j = 0; j < SIZE; j++) {
            for (int i = 1; i < SIZE; i++) {
                int data = matrix[i][j];
                if (matrix[i - 1][j] != 0) {
                    if (matrix[i - 1][j] == data) {
                        return;
                    }
                }
            }
        }

        for (int j = 0; j < SIZE; j++) {
            for (int i = SIZE - 2; i >= 0; i--) {
                int data = matrix[i][j];
                if (matrix[i + 1][j] != 0) {
                    if (matrix[i + 1][j] == data) {
                        return;
                    }
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                int data = matrix[i][j];
                if (matrix[i][j - 1] != 0) {
                    if (matrix[i][j - 1] == data) {
                        return;
                    }
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 2; j >= 0; j--) {
                int data = matrix[i][j];
                if (matrix[i][j + 1] != 0) {
                    if (matrix[i][j + 1] == data) {
                        return;
                    }
                }
            }
        }
        status = Status.Lose;
    }

    void move(Direction direction)
    {
        if (status != Status.Played)
            return;

        if (direction == Direction.Up) {
            for (int j = 0; j < SIZE; j++) {
                for (int i = 1; i < SIZE; i++) {
                    moveTop(i, j);
                }
            }
        }
        else if (direction == Direction.Down)
        {
            for (int j = 0; j < SIZE; j++) {
                for (int i = SIZE - 2; i >= 0; i--) {
                    moveBottom(i, j);
                }
            }
        }
        else if (direction == Direction.Left)
        {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 1; j < SIZE; j++) {
                    moveLeft(i, j);
                }
            }
        }
        else
        {
            for (int i = 0; i < SIZE; i++) {
                for (int j = SIZE - 2; j >= 0; j--) {
                    moveRight(i, j);
                }
            }
        }

        generateNumber();

        checkMove();
    }

    int getScore() {
        return score;
    }

}

