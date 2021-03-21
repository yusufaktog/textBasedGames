package cardguessgame;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;




public class CardGuessGame  {
    Random rnd =  ThreadLocalRandom.current();
    Scanner scanner = new Scanner(System.in);
    private final String[] letters = {"A","B","C","D","E","F","G","H"};
    private final ArrayList<String> symbols = new ArrayList<>();
    private Cards[][] cards = new Cards[4][4];
    
    String exitInfo = "to exit type 0 ";
    String exitQuestion = "Do you want to save the game ?  "
                        + "yes:0 , no:1";
    String savedGameQuestion = "There is a saved game \n"
                             + "Do you want to continue with it or play a new game ?\n"
                             + "yes:0 , no:1\n:";    

    public void createCards(){
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                
                Cards card = new Cards();                
                String symbol = symbols.get( rnd.nextInt(symbols.size()) );
                card.setValue(symbol);
                cards[i][j] = card;
                symbols.remove(symbol);
            }
            
        }
    }
    
    public void fill(){ // copy each element two times to the arraylist
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                symbols.add(letters[i]);
            }            
        }   
    }
    
    public void stream(){
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                if(!cards[i][j].isVisible()){
                    System.out.print("?" + "\t");
                    
                }
                else{
                    System.out.print(cards[i][j].getValue() + "\t");
                    
                }
            }
            System.out.print("\n");
        }
    }
 
    public  void initiateTheGame() throws InterruptedException{
        File file = new File("cards.bin");
        if(file.exists()){
            System.out.print(savedGameQuestion);
            String choice = scanner.nextLine();
            if(choice.equals("0")){
                System.out.println("Preparing the saved game...");
                Thread.sleep(3000);
                openSavedGame();
            }
            else{
                fill();
                createCards();                
                
            }
            
        }
        else{
            fill();
            createCards();
            
        }

    }
    
    
    private boolean winCondition(){
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards.length; j++) {
              if(! cards[i][j].isVisible() )
                  return false;
            }            
        }
        return true;
    }
    
    
    private void getCoordinates(int x, int y) throws InterruptedException{
        
        try{
            cards[x-1][y-1].setVisible(true);
            stream();
            
            System.out.print("a: b: ");
        
            int a = scanner.nextInt();  int b = scanner.nextInt();
            cards[a-1][b-1].setVisible(true);
            stream();
            
            if(cards[x-1][y-1].getValue().equals(cards[a-1][b-1].getValue())){
                System.out.println("Correct Guess, go on ....");
                cards[a-1][b-1].setVisible(true);
                
            }
            else{
                System.out.print("Miss match...\n");
                cards[x-1][y-1].setVisible(false);
                cards[a-1][b-1].setVisible(false);                
            }            
        }        
        catch(ArrayIndexOutOfBoundsException|InputMismatchException e){
            System.out.println("Invalid index, please type between 1 - 4");
            try{
                cards[x-1][y-1].setVisible(false); // when an eror occurs in the second round; visibility of the first card does not turn to false;
                                                   // so that in the next round it can be seen which is not legal
                                                   // therefore we have to deal with it in catch clause;


            }catch(ArrayIndexOutOfBoundsException a){                
                
            }

            
        }            
    }
    public void saveGame(){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("cards.bin"))){
            output.writeObject(cards);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CardGuessGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CardGuessGame.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
    public void openSavedGame(){

        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("cards.bin"))){
            cards = (Cards[][]) input.readObject();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CardGuessGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(CardGuessGame.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
    public boolean exitCondition(int a , int b) throws InterruptedException{
        boolean bool = false;
        if(a == 0 || b == 0){
            System.out.print(exitQuestion + "\n:");
            int choice = scanner.nextInt();
            if(choice == 0){
                System.out.println("Saving the game...");
                Thread.sleep(2000);
                saveGame();
                bool = true;

            }
            else if (choice == 1){
                System.out.println("Exit without saving...");
                bool = true;
                
            }
        }
        return bool;
    }
    public void play() throws InterruptedException{
            
        initiateTheGame();
        System.out.println(exitInfo);   
        System.out.println("coordinates (1 - 4):");
        stream();// print the empty game board at first
        
        while(true){            
            System.out.print("x: y:");
            int x = scanner.nextInt();int y = scanner.nextInt();            
            try{                 
                if(exitCondition(x,y)){
                    break;                
                }
                else{
                    getCoordinates(x,y);
                }

                stream();
                if( winCondition()){
                    System.out.println("You won the game...");
                    break;
                }                
                
            }catch(InputMismatchException e){
                System.out.println("Invalid index, please type between 1 - 4");
            } 

        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        CardGuessGame game = new CardGuessGame();
        game.play();
    }
}