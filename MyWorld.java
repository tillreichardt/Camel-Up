import greenfoot.*; 
import java.util.*;
public class MyWorld extends World
{
    private Camel greenCamel = new Camel(-1, "green");
    private Camel blueCamel = new Camel(-1, "blue");
    private Camel yellowCamel = new Camel(-1, "yellow");
    private Camel whiteCamel = new Camel(-1, "white"); 
    private Camel orangeCamel = new Camel(-1, "orange");
    private Camel SpecialCard = new Camel (-1, "SpecialCard");

    private Board playingBoard = new Board();

    private dice greenDice = new dice(); 
    private dice blueDice = new dice(); 
    private dice yellowDice = new dice(); 
    private dice whiteDice = new dice(); 
    private dice orangeDice = new dice(); 

    private Scanner scanner = new Scanner(System.in);

    private boolean gameOver;
    int newLocation = 0;
    public MyWorld(){    
        super(16, 1, 50);
        System.out.println("enter 'throw [color]' or 'end' in order to end the game.");
        playingBoard.moveCamelStack(greenCamel.getLocation(), 14, greenCamel);
        playingBoard.moveCamelStack(orangeCamel.getLocation(), 14, orangeCamel);
        playingBoard.moveCamelStack(whiteCamel.getLocation(), 14, whiteCamel);
        playingBoard.moveCamelStack(yellowCamel.getLocation(), 14, yellowCamel);
        playingBoard.moveCamelStack(blueCamel.getLocation(), 14, blueCamel);
        playingBoard.printBoard();
        //playingBoard.printCards();
        playingBoard.createCard(1, 5);
        playingBoard.printCards();
    }

    public void act() {
        String input = scanner.nextLine();
        Camel activeCamel; 
        dice activeDice;
        if (!gameOver) {
                if(input.equals("throw specialcard")){
                        System.out.println("Welcher Type? (1 / -1)");
                        String type = scanner.nextLine();
                        int typeNum = Integer.parseInt(type);
                        System.out.println("Welches Feld, das noch nicht belegt ist?");
                        String pos = scanner.nextLine();
                        int PosNum = Integer.parseInt(pos);
                        if (typeNum == 1 || typeNum == -1) {
                            playingBoard.createCard(typeNum, PosNum);
                        }
                        playingBoard.moveCamelStack(SpecialCard.getLocation(), PosNum, SpecialCard);
                        playingBoard.printBoard(); 
                        
                }
                switch (input) {
                    case "throw green":
                        activeCamel = greenCamel;
                        activeDice = greenDice;
                        break;
                    case "throw blue":
                        activeCamel = blueCamel;
                        activeDice = blueDice;
                        break;
                    case "throw yellow":
                        activeCamel = yellowCamel;
                        activeDice = yellowDice;
                        break;
                    case "throw white":
                        activeCamel = whiteCamel;
                        activeDice = whiteDice;
                        break;
                    case "throw orange":
                        activeCamel = orangeCamel;
                        activeDice = orangeDice;
                        break;
                    default:
                        System.out.println("Ungültige Eingabe, bitte verwende das Format: 'throw [color]' oder 'end' zum Beenden. Du Sohn");
                        return; // Ungültige Farbe, also brechen wir ab.
                }

                //eigentliche Bewegung, da man jezt das aktive Kamel und den aktiven Würfel haben.
                newLocation = activeCamel.getLocation() + activeDice.rollSimple();
                if(newLocation > 14){
                    //Wenn die neue Location größer als 14 ist, dann ist das Spiel vorbei
                    gameOver = true;
                    playingBoard.moveCamelStack(activeCamel.getLocation(), 15, activeCamel);
                    playingBoard.printBoard();
                } else { 
                    //Wenn nicht, wird normal weiter gemacht 
                    playingBoard.moveCamelStack(activeCamel.getLocation(), newLocation, activeCamel);
                    playingBoard.printBoard();
                }
            } else if (input.equals("end")) {
                // Das Spiel beenden, wenn der Befehl "end" eingegeben wird.
                Greenfoot.stop();
            } 
        
        // Gewinner ermitteln
        if(gameOver){
            Camel winner = playingBoard.getTopCamelAtField(15);
            System.out.println("The winner is: " + winner);
            Greenfoot.stop();
        }
    }
}

