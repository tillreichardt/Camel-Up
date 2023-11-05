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
    private Dice[] dicers = new Dice[ALL_STACK_SIZE];
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

        for (int i = 0; i < 5; i++){
            dicers[i] = new Dice(CAMEL_COLORS[i]);
        }
        
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
        
        for (int i = 0; i < players.length; i++){
            Player activePlayer = players[i];
            Scanner scan = new Scanner(System.in);
            int response = scan.nextInt();
            switch(response){
                case 1: 
                    activePlayer.addBetCard(drawBetCard(color));
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

    public void stageEvalutation(){
        for (int i = 0  
    }
    

    
    public void playerMoveToDo(){

    }
}