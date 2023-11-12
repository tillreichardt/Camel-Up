import java.util.Random;

public class Dice {
    private String color;
    private Random rand = new Random();
    private int value = -1; // -1 als default value = nicht gerolled

    public Dice(String pcolor) {
        this.color = pcolor;
    }

    public int roll() {
        if (this.value != -1) {
            System.out.println("Warning (Dice: " + color + "): Rolled already rolled dice.");
        }
        this.value = rand.nextInt(3) + 1;
        System.out.println("Der Würfel mit der Farbe " + color + " hat eine " + this.value + " geworfen");
        return this.value;
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return this.value;
    }

    public void reset() {
        this.value = -1; 
    }
    
    @Override 
    public String toString() {
        return color + "Dice";
    }
}