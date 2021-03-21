
package numberplacementgame;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
All of the numbers from 0 to 24 must be placed successfully on the board.
Numbers must be placed in sequential order starting from 0.
After placing a number, if there is an empty cell, the next number can be placed in the same column or row after 2 cells or in a diagonal after 1 cell.
*/

public class NumberPlacementGame { // 


    Object[][] gameBoard = new Object[5][5];
    ArrayList< int[] > possiblePositions = new ArrayList<>(); 
    final int GAME_BOARD_SIZE = 25;
    public void fill(){
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                gameBoard[i][j] = "-";
                
            }
        }
        
    }
    public boolean checkDiagonal(int posX, int posY){ 

        boolean flag = false;
        try{
            if( ( gameBoard[ posX + 2][posY + 2] == "-" ) ){
                possiblePositions.add(new  int[] { (posX + 2) , (posY + 2) } );
                flag = true;  
                
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            
        }
        
        try{
            if(( gameBoard[ posX + 2][posY - 2] == "-" )){
                possiblePositions.add(new  int[] { (posX + 2) , (posY - 2) } );
                flag = true;
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            
        }
        
        try{
            if(( gameBoard[ posX - 2][posY - 2] == "-" )){
                possiblePositions.add(new  int[] { (posX - 2) , (posY - 2) } );
                flag = true;
            } 
        }catch(ArrayIndexOutOfBoundsException e ){
            
        }
        
        try{
           if(( gameBoard[ posX - 2][posY + 2] == "-" )){
                possiblePositions.add(new  int[] { (posX - 2) , (posY + 2) } );
                flag = true;
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            
        }        
        return flag;
 
    }    
    public boolean checkHorizontal(int posX, int posY){
        boolean flag = false;
        try{
            if( ( gameBoard[ posX ][posY + 3] == "-" ) ){
                possiblePositions.add(new  int[] { (posX) , (posY + 3) } );
                flag = true;
                
            }
        }catch(ArrayIndexOutOfBoundsException e){
            
        }
        try{
            if(  (gameBoard[ posX ][posY - 3] == "-")  ){
                possiblePositions.add(new  int[] { (posX) , (posY - 3) } );
                flag = true;
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            
        }     
        return flag;
    }
    public boolean checkVertical(int posX, int posY){
        boolean flag = false;
        try{
            if( ( gameBoard[posX + 3][ posY ] == "-" )  ){
                possiblePositions.add(new  int[] { (posX + 3) , (posY) } );
                flag = true;
            }
            
        }catch(ArrayIndexOutOfBoundsException e){
                
        }
        try{
            if( (gameBoard[posX - 3][ posY ] == "-") ){
                possiblePositions.add(new  int[] { (posX - 3) , (posY) } );
                flag = true;
            } 
        }catch(ArrayIndexOutOfBoundsException e ){
            
        }           
               
         
        return flag;
    } 
    public int chooseRandomPossiblePosition(){        
        return (int) ( Math.random()*possiblePositions.size() );
    }
    
    public void play() throws InterruptedException{
        placeNumber();       

    }
    
    public void stream(){
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                System.out.print(gameBoard[i][j] + " \t");
                
            }
            System.out.println("\n");
        }
        
    }
    public void placeNumber() throws InterruptedException{
        fill();

        int startingPointX = ThreadLocalRandom.current().nextInt(gameBoard.length);
        int startingPointY = ThreadLocalRandom.current().nextInt(gameBoard.length);
        gameBoard[startingPointX][startingPointY] = 0;
        System.out.println("Starting Point:(" + startingPointX + "," + startingPointY + ")");
        stream();
        TimeUnit.SECONDS.sleep(5);
        
        int posX = 0;int posY = 0;
        
        if(isPlayable(startingPointX,startingPointY)){
            posX = possiblePositions.get(chooseRandomPossiblePosition())[0];
            posY = possiblePositions.get(chooseRandomPossiblePosition())[1];
            gameBoard[posX][posY] = 1;
            System.out.println("New Position:(" + posX + "," + posY + ")");
            possiblePositions.clear();
            
        }   
        stream();
        TimeUnit.SECONDS.sleep(5);
        for (int i = 2; i < GAME_BOARD_SIZE; i++) {  
           
            if(isPlayable(posX , posY)){
                
                posX = possiblePositions.get(chooseRandomPossiblePosition())[0];
                posY = possiblePositions.get(chooseRandomPossiblePosition())[1];
                gameBoard[posX][posY] = i;
                System.out.println("(" + posX + " , " + posY +")" + " is chosen");
                stream();
                TimeUnit.SECONDS.sleep(5);
                
                possiblePositions.clear();
                
            }
            else{
                System.out.println("There is no any other empty spaces to place\nThe Game is OVER!");
                break;
            }
            
        }
    }
    
    public boolean isPlayable(int posX, int posY){
        return checkDiagonal( posX , posY ) || checkVertical( posX , posY ) || checkHorizontal( posX , posY ) ;
    }
  
        
}