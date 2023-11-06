import java.util.Random;

public class Dice{
    private String color;
    private Random rand = new Random();
    private boolean locked;
    
    public Dice(String pcolor) {
        this.color = pcolor;
    }

    public int roll() {
        System.out.println("Der Würfel mit der Farbe " + color + " wurde gewürfelt");
        return rand.nextInt(3) + 1; // Generates a random number between 1 and 3
    }

    public String getColor() {
        return color;
    }
    
    public void setLocked(boolean value){
        locked = value;
    }
    
    public boolean getLocked(){
        return locked;    
    }
}
