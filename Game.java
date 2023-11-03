import greenfoot.*;
import java.util.*;
public class Game extends World {
    private static final int GRID_SIZE = 50;
    private Dice[] dicers = new Dice[ALL_STACK_SIZE];
    private static final int ALL_STACK_SIZE = 5;
    private static final String[] CAMEL_COLORS = {"white", "green", "blue", "yellow", "orange"};
    private boolean gameEnded;  
    private boolean etappeEnded; 
    
    private CamelTrack rennBahn;
    
    private Player CockInspector;
    private Player LongSchlongJohnson;
    
    private DesertCard dc;
    private OasisCard oc;
    
    private Stack<PyramidCards> pyramidCards = new Stack<>();
    public Game(){
        super (18*GRID_SIZE, 18*GRID_SIZE, 1);
        gameEnded = false; 
        etappeEnded = false; 
        setup();        
    }
    private void setup(){
        rennBahn = new CamelTrack();
        addObject(rennBahn, 450, 775);
        CockInspector = new Player();
        LongSchlongJohnson  = new Player();
        dc = new DesertCard();
        addObject(dc, 0, 0);
        oc = new OasisCard();
        addObject(oc, 0, 0);
                
        
        for (int i = 0; i < ALL_STACK_SIZE; i++){
            dicers[i] = new Dice(CAMEL_COLORS[i]);
            pyramidCards.push(new PyramidCards());
        }
    
                    
        rennBahn.addCamelsToBoard();
        rennBahn.addActionCard(dc, 11, CockInspector);
        rennBahn.addActionCard(oc, 6, LongSchlongJohnson);
        rennBahn.updateBoard();
        // Reihenfolge: cw unten, co mitte, cb oben
        Camel co = rennBahn.getCamelByColor("orange");
        Camel cw = rennBahn.getCamelByColor("white");
        Camel cb = rennBahn.getCamelByColor("blue");
        
        cw.carry(co);
        co.carry(cb);
    }
    public void act(){
        rennBahn.moveCamel("white", 2);
    }
    public void moveOrange(){
        rennBahn.moveCamel("orange", 1);
    }
    public void moveBlue(){
        rennBahn.moveCamel("blue", 1);
    }
    public void usePCard() {
        if (!pyramidCards.isEmpty()) {
            pyramidCards.pop();
        } else {
            System.out.println("Keine Karten mehr vorhanden.");
        }
    }
    // 4 Möglichkeiten
    // case 1 - 4; 
    // case 1: etappen Wettlätchen 
    // case 2: desertCard nach etappen ende wieder bei spieler
    // case 3: würfeln und usePCard nach etappen ende wieder voll 
    // case 4: Olle Tolle Camel, when gameEded = true
}