package org.example;

import org.example.models.GameBoard;
import org.example.models.Player;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player();
        player1.setId(1);
        player1.setName("Shubham Patel");
        player1.setSymbol('X');

        Player player2 = new Player();
        player2.setId(2);
        player2.setName("Kishan Kumar Gupta");
        player2.setSymbol('O');

        GameBoard gameBoard = new GameBoard(3, player1, player2);
        gameBoard.startGame();

    }
}