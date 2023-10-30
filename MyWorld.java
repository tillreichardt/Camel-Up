import greenfoot.*;
public class myWorld extends World {
    private static final int GRID_SIZE = 50;
    
    private CamelTrack rennBahn;
    
    private boolean gameEnded;  
    
    
    public myWorld(){
        super (18*GRID_SIZE, 18*GRID_SIZE, 1);
        setup();
        //rennBahn.printBoard(); 
        
    }
    private void setup(){
        rennBahn = new CamelTrack();
        addObject(rennBahn, 450, 775);
        rennBahn.addCamelsToBoard();
        // Reihenfolge: cw unten, co mitte, cb oben
        Camel co = rennBahn.getCamelByColor("orange");
        Camel cw = rennBahn.getCamelByColor("white");
        Camel cb = rennBahn.getCamelByColor("blue");
        
        cw.carry(co);
        co.carry(cb);
    }
    public void act(){
        rennBahn.moveCamel("white", 1);
    }
    public void moveOrange(){
        rennBahn.moveCamel("orange", 1);
    }
    public void moveBlue(){
        rennBahn.moveCamel("blue", 1);
    }
}