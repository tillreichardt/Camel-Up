import greenfoot.*; 
import java.util.Random;

public class Dice extends Actor{
    private int randomNumber;
    private String color; 
    private boolean used;
    Random rand = new Random();
    
    public Dice(String pColor){
        used = false;
        color = pColor;
    }
    
    public int roll(){
        if (used == false){
            randomNumber = rand.nextInt(3) + 1;
            System.out.println("You threw a " + randomNumber);
            return randomNumber; 
        }
        return 0;
    }
    
    public void setUsed(boolean value){
        used = value;
    }
    
    public boolean getUsed(){
        return used;
    }
    
    public String getColor(){
        return color;
    }
}
