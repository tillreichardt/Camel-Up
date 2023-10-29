import greenfoot.*;
public class myWorld extends World {
    private static final int GRID_SIZE = 50;
    
    private CamelTrack rennBahn;
    private Camel greenCamel;
    private Camel whiteCamel;
    
    
    
    
    public myWorld(){
        super (18*GRID_SIZE, 18*GRID_SIZE, 1);
        setup();
        greenCamel.WelchesCamelSitztAufMir(whiteCamel);
        System.out.println(greenCamel.getX());
        System.out.println(greenCamel.getY());
        rennBahn.printBoard(); 
    }
    private void setup(){
        rennBahn = new CamelTrack();
        addObject(rennBahn, 450, 775);
        
        greenCamel = new Camel(0,4, "green");
        rennBahn.addCamelToBoard(4,1, greenCamel);
        addObject(greenCamel, 1*GRID_SIZE, 18*GRID_SIZE);
        
        whiteCamel = new Camel(0,3, "white"); 
        rennBahn.addCamelToBoard(3,1, whiteCamel);
        addObject(whiteCamel, 1*GRID_SIZE, 17*GRID_SIZE);
        
    }
    public void act(){
        
        
    }
    public void moveGreen(){
        rennBahn.moveCamel(greenCamel, 2);
        
        System.out.println("");
        System.out.println("wX: " + greenCamel.getX());
        System.out.println("wY: " + greenCamel.getY());
        System.out.println("wX: " + whiteCamel.getX());
        System.out.println("wY: " + whiteCamel.getY());
        
        rennBahn.printBoard(); 
    }
    public void moveWhite(){
        rennBahn.moveCamel(whiteCamel, 2);
        
        System.out.println("");
        System.out.println("wX: " + greenCamel.getX());
        System.out.println("wY: " + greenCamel.getY());
        System.out.println("wX: " + whiteCamel.getX());
        System.out.println("wY: " + whiteCamel.getY());
        
        rennBahn.printBoard(); 
    }
}