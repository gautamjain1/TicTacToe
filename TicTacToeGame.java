import Model.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {

    Deque<Player> players ;
    Board gameBoard ;

    public void initializeGame(){
        players = new LinkedList<>();
        PieceX cross = new PieceX();
        Player player1 = new Player("Player1" , cross);
        PieceO nought = new PieceO();
        Player player2 = new Player("Player2" , nought);

        players.add(player1);
        players.add(player2);

        gameBoard = new Board(3);
    }

    public String startGame(){
        boolean noWinner = true;

        while(noWinner){
            Player playerTurn = players.removeFirst();
            gameBoard.printBoard();
            List<Pair<Integer , Integer>> freeSpaces = gameBoard.getFreeCells();
            if(freeSpaces.isEmpty()){
                noWinner = false;
                continue;
            }
            System.out.print("Player: " + playerTurn.getName() + " Enter row , column: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.parseInt(values[0]);
            int inputCol = Integer.parseInt(values[1]);


            // line 47 might give us error. we will need to work on it.
            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow , inputCol , playerTurn.getPlayingPiece());
            if(!pieceAddedSuccessfully){
                System.out.println("Incorrect position chosen , Try again ");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = inThereWinner(inputRow , inputCol , playerTurn.playingPiece.pieceType);

            if(winner){
                gameBoard.printBoard();
                return playerTurn.name;
            }
        }

        return "Game Tie";
    }

    private boolean inThereWinner(int row , int column, PieceType pieceType) {

        boolean rowMatch =  true;
        boolean colMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;


        for(int i = 0 ; i<gameBoard.size; i++){
            if(gameBoard.board[row][i] == null || gameBoard.board[row][i].pieceType != pieceType){
                rowMatch = false;
            }
        }

        for(int i = 0 ; i<gameBoard.size ; i++){
            if(gameBoard.board[i][column] == null || gameBoard.board[i][column].pieceType != pieceType){
                colMatch =  false;
            }
        }

        for(int i = 0 , j = 0 ; i< gameBoard.size ; i++ , j++){
            if(gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType){
                diagonalMatch = false;
            }
        }

        for(int i = 0 , j = gameBoard.size - 1 ; i<gameBoard.size ; i++ , j--){
            if(gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType){
                antiDiagonalMatch = false;
            }
        }

        return rowMatch || colMatch || diagonalMatch || antiDiagonalMatch;


    }
}
