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
        
        Camel co = rennBahn.getCamelByColor("orange");
        Camel cw = rennBahn.getCamelByColor("white");
        
        cw.carry(co);
    }
    public void act(){
        rennBahn.moveCamel("white", 1);
    }
    public void moveOrange(){
        rennBahn.moveCamel("orange", 1);
    }
}