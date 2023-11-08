import greenfoot.*;
import java.util.*;
public class Game extends World {
    private static final int GRID_SIZE = 50;
    private String[] camelColors = {"white", "green", "blue", "yellow", "orange"};

    private boolean gameEnded;  
    private boolean etappeEnded; 

    private CamelTrack rennBahn;

    private DesertCard dc;
    private OasisCard oc;

    private int amoutOfPyramidCards = 5;
    private Player[] players;
    private PyramidCards pyramidCards = new PyramidCards();
    private DiceSet dicersSet = new DiceSet(camelColors);
    private BetCards betCards = new BetCards();
    public Game(/*int numberOfPlayers*/){
        super (18*GRID_SIZE, 18*GRID_SIZE, 1);
        gameEnded = false; 
        etappeEnded = false;
        rennBahn = new CamelTrack();
        addObject(rennBahn, 450, 775);
        setup(/*numberOfPlayers*/);
    }

    private void setup(/*int numberOfPlayers*/){

        int numberOfPlayers = 8;

        dc = new DesertCard();
        oc = new OasisCard();

        players = new Player[numberOfPlayers];
        String[] mustHaveNames = {"CockInspector" , "LongSchlongJohnson", "NullPointerNinja", "ExceptionExplorer", "ClassClown", "DebugDragon", "PixelPirate", "BugHunter"};

        if (numberOfPlayers <= 8){
            System.out.println("\n\n");
            for (int i = 0; i < numberOfPlayers; i++) {
                players[i] = new Player(mustHaveNames[i]);
                System.out.println(mustHaveNames[i] + " ist dem Spiel beigetreten");
            }
            System.out.println("\n\n");
        }
        else {
            System.out.println("Du kannst maximal nur 8 Spieler haben");
        }
        
        rennBahn.addCamelsToBoard();

        startGame();

    }
    // 4 Möglichkeiten
    // case 1 - 4; 
    // aktive player --> Player player Parameter = aktiv player;
    // case 1: etappen Wettlätchen 
    // case 2: desertCard nach etappen ende wieder bei spieler
    // case 3: würfeln und usePCard nach etappen ende wieder voll 
    // case 4: Olle Tolle Camel, when gameEnded = true
    public void act(){

        Scanner scan = new Scanner(System.in);
        if (!isStageEnded()) {
            for (int i = 0; i < players.length; i++){

                Player activePlayer = players[i];
                int response = (int) getUserInput(scan, "\n" + activePlayer + " gib eine Option (1 - 4) ein: ");
                System.out.println(response);

                switch(response){
                    case 1:
                        String pColor = (String) getUserInput(scan, "Auf welches Kamel wettest du? Farbe: ");
                        activePlayer.addBetCard(betCards.drawBetCard(pColor));
                        break;

                    case 2: 
                        String actionCard = (String) getUserInput(scan, "Soll eine DesertCard (dc) oder OasisCard (oc) gespielt werden?: ");
                        int pPosition = (int) getUserInput(scan, "Auf welche Position soll diese ActionCard?: ");

                        if (actionCard.equals("dc") || actionCard.equals("oc")) {
                            if (!activePlayer.getActionCardPlayed()){
                                if (actionCard.equals("dc")) {
                                    rennBahn.addActionCard(activePlayer.getDesertCard(), pPosition, activePlayer);
                                    Greenfoot.delay(1);
                                } else if (actionCard.equals("oc")) {
                                    rennBahn.addActionCard(activePlayer.getOasisCard(), pPosition, activePlayer);
                                    Greenfoot.delay(1);
                                }
                            }
                            else {
                                System.out.println("Du hast bereits eine ActionCard gespielt, wähle eine andere Option");
                            }
                        } else {
                            System.out.println("Ungültige Eingabe für die ActionCard. Bitte wähle 'dc' oder 'oc'.");
                        }
                        break;

                    case 3:

                        activePlayer.addPyramidCard(pyramidCards.getPyramidCard());
                        Dice dice = dicersSet.rollRandomDice();
                        rennBahn.moveCamel(dice.getColor(), dice.getValue());
                        Greenfoot.delay(1);
                        amoutOfPyramidCards--;
                        System.out.println("Es gibt noch "+ amoutOfPyramidCards  + " Pyramiden Karten");
                        break;
                    case 4:
                        //work in progress
                        break; 
                    case 5: 
                        Greenfoot.stop();
                        break; 
                    default:
                        System.out.println("Ungültige Auswahl. Bitte wähle 1 oder 5.");
                        break;
                }
                if (isStageEnded()) {
                    break; // Exit the for-loop immediately
                }
            } 
        } else {
            System.out.println("Die Etappe ist vorbei");
        }
    }

    public void GameLoop(){
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < players.length; i++){

            Player activePlayer = players[i];
            int response = (int) getUserInput(scan, activePlayer + " gib eine Option (1 - 4) ein: ");
            System.out.println(response);
            if (!isStageEnded()){
                switch(response){
                    case 1:
                        String pColor = (String) getUserInput(scan, "Auf welches Kamel wettest du? Farbe: ");
                        activePlayer.addBetCard(betCards.drawBetCard(pColor));
                        break;

                    case 2: 
                        String actionCard = (String) getUserInput(scan, "Soll eine DesertCard (dc) oder OasisCard (oc) gespielt werden?");
                        int pPosition = (int) getUserInput(scan, "Auf welche Position soll diese ActionCard?");

                        if (actionCard.equals("dc") || actionCard.equals("oc")) {
                            if (!activePlayer.getActionCardPlayed()){
                                if (actionCard.equals("dc")) {
                                    rennBahn.addActionCard(activePlayer.getDesertCard(), pPosition, activePlayer);
                                } else if (actionCard.equals("oc")) {
                                    rennBahn.addActionCard(activePlayer.getOasisCard(), pPosition, activePlayer);
                                }
                            }
                            else {
                                System.out.println("Du hast bereits eine ActionCard gespielt, wähle eine andere Option");
                            }
                        } else {
                            System.out.println("Ungültige Eingabe für die ActionCard. Bitte wähle dc oder oc.");
                        }
                        break;

                    case 3:

                        activePlayer.addPyramidCard(pyramidCards.getPyramidCard());
                        Dice dice = dicersSet.rollRandomDice();
                        rennBahn.moveCamel(dice.getColor(), dice.getValue());

                        amoutOfPyramidCards--;
                        System.out.println("Es gibt noch "+ amoutOfPyramidCards  + " Pyramiden Karten");

                        break;
                    case 4:
                        //work in progress
                        break; 
                    case 5: 
                        Greenfoot.stop();
                        break; 
                    default:
                        System.out.println("Ungültige Auswahl. Bitte wähle 1 oder 5.");
                        break;
                }
            } else {
                System.out.println("Die Etappe ist vorbei");
            }
        }
    }

    public void resetAmoutOfPyramidCards(){
        amoutOfPyramidCards = 5;
    }

    public void moveOrange(){
        rennBahn.moveCamel("orange", 1);
    }

    public void moveBlue(){
        rennBahn.moveCamel("blue", 1);
    }

    public void moveYellow(){
        rennBahn.moveCamel("yellow", 1);
    }

    public void moveGreen(){
        rennBahn.moveCamel("green", 1);
    }

    public void moveWhite(){
        rennBahn.moveCamel("white", 2);
    }

    public void print(){
        System.out.println(rennBahn.getCamelSorted());
    }

    public boolean isStageEnded(){
        if (amoutOfPyramidCards == 0){
            return true; 
        } else{
            return false;
        }
    }

    public void startGame(){
        Dice[] würfel = dicersSet.rollAllAvailableDices();

        for(int i = 0; i < camelColors.length; i++){
            if (würfel[i] != null && rennBahn != null) {
                rennBahn.moveCamel(würfel[i].getColor(), würfel[i].getValue());
            } else {
                System.out.println("Ein Würfel ist null oder die RennBahn wurde nicht initialisiert.");
            }
        }
        dicersSet.reset();
    }

    private Object getUserInput(Scanner scanner, String prompt) {
        System.out.print(prompt);

        if (scanner.hasNextInt()) {
            int intValue = scanner.nextInt();
            scanner.nextLine(); // Konsumiere den Zeilenumbruch
            return intValue;
        } else {
            return scanner.nextLine();
        }
    }
}