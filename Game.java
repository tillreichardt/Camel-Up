import greenfoot.*;
import java.util.*;
public class Game extends World {
    private static final int GRID_SIZE = 50;
    private static final String[] CAMEL_COLORS = {"white", "green", "blue", "yellow", "orange"};

    private boolean gameEnded;  
    private boolean etappeEnded; 

    private CamelTrack rennBahn;

    private DesertCard dc;
    private OasisCard oc;

    private Player[] players;
    private PyramidCards pyramidCards = new PyramidCards();
    private DiceSet dicersSet = new DiceSet(CAMEL_COLORS);
    private BetCards betCards = new BetCards();
    public Game(int numberOfPlayers){
        super (18*GRID_SIZE, 18*GRID_SIZE, 1);
        gameEnded = false; 
        etappeEnded = false;

        setup(numberOfPlayers);        
    }

    private void setup(int numberOfPlayers){
        rennBahn = new CamelTrack();
        addObject(rennBahn, 450, 775);

        dc = new DesertCard();
        addObject(dc, 0, 0);
        oc = new OasisCard();
        addObject(oc, 0, 0);

        players = new Player[numberOfPlayers];
        String[] mustHaveNames = {"CockInspector" , "LongSchlongJohnson", "NullPointerNinja", "ExceptionExplorer", "ClassClown", "DebugDragon", "PixelPirate", "BugHunter"};

        if (numberOfPlayers <= 8){
            for (int i = 0; i < numberOfPlayers; i++) {
                players[i] = new Player(mustHaveNames[i]);
            }
        }
        else {
            System.out.println("Du kannst maximal nur 8 Spieler haben");
        }
        rennBahn.addCamelsToBoard();
        rennBahn.addActionCard(dc, 11, players[0]);
        rennBahn.addActionCard(oc, 6, players[1]);
        rennBahn.updateBoard();
        // Reihenfolge: cw unten, co mitte, cb oben
        Camel co = rennBahn.getCamelByColor("orange");
        Camel cw = rennBahn.getCamelByColor("white");
        Camel cb = rennBahn.getCamelByColor("blue");

        cw.carry(co);
        co.carry(cb);
    }
    // 4 Möglichkeiten
    // case 1 - 4; 
    // aktive player --> Player player Parameter = aktiv player;
    // case 1: etappen Wettlätchen 
    // case 2: desertCard nach etappen ende wieder bei spieler
    // case 3: würfeln und usePCard nach etappen ende wieder voll 
    // case 4: Olle Tolle Camel, when gameEnded = true
    public void act(){
        // Idee: Spieler gibt 1 - 4 in der Konsole an und 
        for (int i = 0; i < players.length; i++){
            Player activePlayer = players[i];
            Scanner scan = new Scanner(System.in);
            int response = scan.nextInt();
            switch(response){
                case 1: 
                    System.out.println("Auf welches Kamel wettest du? Gib eine Farbe an!");
                    String pColor = scan.nextLine();
                    activePlayer.addBetCard(betCards.drawBetCard(pColor));
                    break;
                    
                case 2: 
                    System.out.println("Soll eine DesertCard (dc) oder OasisCard (oc) gespielt werden?");
                    String actionCard = scan.nextLine();
                    System.out.println("Auf welche Position soll diese ActionCard?");
                    int pPosition = scan.nextInt();

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
                    activePlayer.addPyramidCard(pyramidCards.usePyramidCard());
                    rennBahn.moveCamel(dicersSet.rollRandomDice().getColor(), dicersSet.rollRandomDice().getValue());
                    break;
                case 4:
                    //work in progress
                    break; 
                default:
                    System.out.println("Ungültige Auswahl. Bitte wähle 1 oder 2.");
                    break;
            }
        }
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
        if (pyramidCards.giveOutCards().isEmpty()){
            return true; 
        } else{
            return false;
        }
    }

    // public void stageEvalutation(){
    // for (int i = 0; 
    // }

    public void startStage(){

    }

    public void playerMoveToDo(){

    }
}