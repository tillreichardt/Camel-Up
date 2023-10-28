import greenfoot.*; 

public class dice extends Actor{
    private boolean greenLocked;
    private boolean whiteLocked;
    private boolean orangeLocked;
    private boolean blueLocked;
    private boolean yellowLocked;

    private int greenTemp;
    private int whiteTemp;
    private int orangeTemp;
    private int blueTemp;
    private int yellowTemp;
    
    
    public void act()
    {
        // Add your action code here.
    }
    public int rollSimple(){
        int zahl = (int) ((Math.random() * (4 - 1)) + 1);
        System.out.println("You threw a " + zahl);
        return zahl; 
    }
    public int roll(String colour) {
        switch(colour){
            case "green":
                return (int) ((Math.random() * (3 - 1)) + 1);
            case "white":
                return (int) ((Math.random() * (3 - 1)) + 1);
            case "orange":
                return (int) ((Math.random() * (3 - 1)) + 1);
            case "blue":
                return (int) ((Math.random() * (3 - 1)) + 1);
            case "yellow":
                return (int) ((Math.random() * (3 - 1)) + 1);
            default:
                return (int) ((Math.random() * (3 - 1)) + 1);
        }
    }

    public int rollOne() {
        int colour = (int) ((Math.random() * (4 - 0)) + 0);
        switch(colour) {
            case 0: //green
                if (!greenLocked){
                    greenLocked = true;    
                    return (int) ((Math.random() * (3 - 1)) + 1);
                } else {
                    rollOne();
                }   
            case 1: //white
                if(!whiteLocked) {
                    whiteLocked = true;
                    return (int) ((Math.random() * (3 - 1)) + 1);
                } else {
                    rollOne();
                }      

            case 2: //orange
                if(!orangeLocked) {
                    orangeLocked = true;    
                    return (int) ((Math.random() * (3 - 1)) + 1);
                } else {
                    rollOne();
                }      

            case 3: //blue
                if(!blueLocked) {
                    blueLocked = true;   
                    return (int) ((Math.random() * (3 - 1)) + 1);
                } else {
                    rollOne();
                }      

            case 4: //yellow
                if(!yellowLocked) {
                    yellowLocked = true;    
                    return (int) ((Math.random() * (3 - 1)) + 1);
                } else {
                    rollOne();
                }     

            default:
                return 0;
        }
    }

    public void rollAll(){
        greenTemp = 0;
        whiteTemp = 0;
        orangeTemp = 0;
        blueTemp = 0;
        yellowTemp = 0;  
        
        greenTemp = (int) ((Math.random() * (3 - 1)) + 1);;
        whiteTemp = (int) ((Math.random() * (3 - 1)) + 1);;
        orangeTemp = (int) ((Math.random() * (3 - 1)) + 1);;
        blueTemp = (int) ((Math.random() * (3 - 1)) + 1);;
        yellowTemp = (int) ((Math.random() * (3 - 1)) + 1);;
    }
}
