package ru.geekbrains.hw4;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static final int SIZE = 3;
    static final int WINSIZE = 3;
    static final char DOT_EMPTY = '•';
    static final char DOT_HUMAN = 'X';
    static final char DOT_AI = 'O';
    static final String HEADER_FIRST_EMPTY = "♥";
    static final String EMPTY = " ";
    static char[][] map = new char[SIZE][SIZE];
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        turnGame();
    }

    private static void turnGame() {
        initMap();
        printMap();
        playGame();
    }

    private static void initMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void printMap() {
        printMapHeader();
        printMapRow();
    }

    private static void printMapHeader() {
        System.out.print(HEADER_FIRST_EMPTY + EMPTY);
        for (int i = 0; i < SIZE; i++) {
            printMapNumber(i);
        }
        System.out.println();
    }

    private static void printMapNumber(int i) {
        System.out.print(i + 1 + EMPTY);
    }

    private static void printMapRow() {
        for (int i = 0; i < SIZE; i++) {
            printMapNumber(i);
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + EMPTY);
            }
            System.out.println();
        }
    }

    private static void playGame() {

        while (true) {
            humanTurn();
            printMap();
            checkEnd(DOT_HUMAN);

            aiTurn();
            printMap();
            checkEnd(DOT_AI);
        }
    }

    private static void humanTurn() {
        int rowNumber;
        int colNumber;

        System.out.println("\nХод человека! Введите номер строки и столбца!");
        do {
            System.out.print("Строка = ");
            rowNumber = scanner.nextInt();
            System.out.print("Столбец = ");
            colNumber = scanner.nextInt();
        } while (!isCellValid(rowNumber, colNumber));

        map[rowNumber - 1][colNumber - 1] = DOT_HUMAN;
    }

    private static boolean isCellValid(int rowNumber, int colNumber, boolean isAI) {

        if (!isAI && ((rowNumber < 1) || (rowNumber > SIZE) || (colNumber < 1) || (colNumber > SIZE))) {
            System.out.println("\nПроверьте значения строки и столбца");
            return false;
        }

        if (map[rowNumber - 1][colNumber - 1] != DOT_EMPTY) {
            if (!isAI) {
                System.out.println("\nВы выбрали занятую ячейку");
            }
            return false;
        }

        return true;
    }

    private static boolean isCellValid(int rowNumber, int colNumber) {
        return isCellValid(rowNumber, colNumber, false);
    }

    private static void checkEnd(char symbol) {

        boolean isEnd = false;

        if (checkWin(symbol)) {
            String winMessage;
            if (symbol == DOT_HUMAN) {
                winMessage = "УРА! Вы победили!";
            } else {
                winMessage = "Восстание близко! AI победил";
            }
            isEnd = true;
            System.out.println(winMessage);
        }

        if (!isEnd && isMapFull()) {
            System.out.println("Ничья!");
            isEnd = true;
        }

        if (isEnd) {
            System.exit(0);
        }
    }

    private static boolean checkWin(char symbol) {

        if (checkHorizontalValues(symbol)) return true;
        if (chackVerticalValues(symbol)) return true;
        if (chekRightDiagonalValues(symbol)) return true;
        if (chekLeftDiagonalValues(symbol)) return true;

        return false;
    }

    private static boolean checkHorizontalValues(char symbol) {

        for (int i = 0; i < map.length; i++) {
            int winChars = 0;
            for (int j = 0; j < map[i].length; j++) {
                if (symbol == map[i][j]) {
                    winChars++;
                    if (winChars == WINSIZE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean chackVerticalValues(char symbol) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                int winChars = 0;
                if (symbol == map[i][j]) {
                    winChars++;
                    for (int k = i + 1; k < map.length; k++) {
                        if (symbol == map[k][j]) {
                            winChars++;
                            if (winChars == WINSIZE) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean chekRightDiagonalValues(char symbol) {
        for (int i = 0; i <= (map.length - WINSIZE); i++) {
            for (int j = WINSIZE - 1; j < map[i].length; j++) {
                int winChars = 0;
                if (map[i][j] == symbol) {
                    winChars=1;
                    for (int k = 1; k < WINSIZE; k++) {
                        if (map[i + k][j - k] == symbol) {
                            winChars++;
                        }
                    }
                    if (winChars == WINSIZE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean chekLeftDiagonalValues(char symbol) {
        for (int i = 0; i <= map.length - WINSIZE; i++) {
            for (int j = 0; j <= map[i].length - WINSIZE; j++) {
                int winChars = 0;
                if (map[i][j] == symbol) {
                    winChars = 1;
                    for (int k = 1; k < WINSIZE; k++) {
                        if (map[i + k][j + k] == symbol) {
                            winChars++;
                        }
                    }
                    if (winChars == WINSIZE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private static boolean isMapFull() {
        for (char[] chars : map) {
            for (char aChar : chars) {
                if (aChar == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void aiTurn() {
        int rowNumber;
        int colNumber;
        System.out.println("\nХод компьютера!\n");
        do {
            rowNumber = random.nextInt(SIZE) + 1;
            colNumber = random.nextInt(SIZE) + 1;
        } while (!isCellValid(rowNumber, colNumber, true));
        map[rowNumber - 1][colNumber - 1] = DOT_AI;
    }
}
