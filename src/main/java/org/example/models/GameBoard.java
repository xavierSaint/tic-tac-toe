package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

@Getter
@Setter
public class GameBoard {
    char [][] board;
    private int boardSize;
    Queue<Player> nextTurn;
    Scanner input;


    public GameBoard(int boardSize, Player player1, Player player2) {
        this.boardSize = boardSize;
        this.board = new char[2*boardSize-1][2*boardSize-1];
        initializeBoard(board);
        nextTurn = new LinkedList<>();
        nextTurn.add(player1);
        nextTurn.add(player2);
        input = new Scanner(System.in);
    }

    private void printBoard() {
        int n = board.length;
        int m = board[0].length;
        for (int i=0;i<n;i++) {
            for (int j=0;j<m;j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    private void initializeBoard(char[][] board) {
        int n = board.length;
        int m = board[0].length;
        for (int i=0;i<n;i++) {
            for (int j=0;j<m;j++) {
                if (i%2==0 && j%2!=0) {
                    board[i][j] = '|';
                }
                else if (i%2!=0 && j%2==0) {
                    board[i][j] = '-';
                }
                else if (i%2!=0 && j%2!=0) {
                    board[i][j] = '+';
                } else {
                    board[i][j] = ' ';
                }
            }
        }
    }

    public void startGame() {
        int count = 0;
        while (true) {
            count++;
            if (count > boardSize*boardSize) {
                System.out.println("Match is drawn");
                break;
            }
            Player currentPlayer = nextTurn.poll();
            char currentPlayerSymbol = currentPlayer.getSymbol();
            int position = getUserInput(currentPlayer);
            int row = 2*((position % boardSize == 0)?(position/boardSize)-1 : position/boardSize);
            int col = 2*((position % boardSize == 0?boardSize : position%boardSize)-1);
            board[row][col] = currentPlayerSymbol;
            System.out.println("Board after the move");
            printBoard();
            System.out.println();
            if (count >= boardSize && checkEndGame(currentPlayer, row, col)) {
                System.out.println(currentPlayer.getName() + " has won");
                break;
            }
            nextTurn.add(currentPlayer);
        }
    }

    private boolean checkEndGame(Player currentPlayer, int row, int col) {
        String winString = "";
        for (int i=0;i<boardSize;i++) {
            winString += currentPlayer.getSymbol();
        }
        String rowString = "";
        for (int i=0;i<board.length;i+=2) {
            rowString += board[row][i];
        }
        String colString = "";
        for (int i=0;i<board.length;i+=2) {
            colString += board[i][col];
        }
        String diagonalString = "";
        if(row == col) {
            for (int i=0;i<board.length;i+=2) {
                diagonalString += board[i][i];
            }
        }
        String reverseDiagonalString = "";
        if((row + col) == board.length-1) {
            for (int i=0;i<board.length;i+=2) {
                reverseDiagonalString += board[i][board.length-i-1];
            }
        }

        return rowString.equals(winString) ||  colString.equals(winString) || diagonalString.equals(winString) || reverseDiagonalString.equals(winString);
    }

    private int getUserInput(Player currentPlayer) {
        printBoard();
        System.out.println(currentPlayer.getName() + " please enter a number between 1 - " + (boardSize*boardSize) + ": ");
        int val = input.nextInt();
        while (!isValidInput(val)) {
            System.out.println("Wrong position - " + currentPlayer.getName() + "Please enter a number between 1 - " + (boardSize*boardSize) + ": ");
            val = input.nextInt();
        }
        return val;
    }

    private boolean isValidInput(int position) {
        if (position < 1 || position > boardSize*boardSize) {
            return false;
        }
        int row = 2*((position % boardSize == 0)?(position/boardSize)-1 : position/boardSize);
        int col = 2*((position % boardSize == 0?boardSize : position%boardSize)-1);
        return board[row][col] == ' ';
    }


}
