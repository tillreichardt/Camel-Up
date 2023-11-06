import java.util.Random;

public class DiceSet {
    private Dice[] dices;

    public DiceSet(String[] colors) {
        dices = new Dice[colors.length];
        for (int i = 0; i < colors.length; i++) {
            dices[i] = new Dice(colors[i]);
        }
    }
    
    public int rollRandomDice() {
        int randomIndex = new Random().nextInt(dices.length);
        if (!dices[randomIndex].getLocked()){
            dices[randomIndex].setLocked(true);
            return dices[randomIndex].roll();
        }
        else {
            System.out.println("Dieser Würfel wurde bereits gewürfelt");
            return 0;
        }
    }
    
    public int rollDiceByColor(String color) { // zum debuggen 
        for (Dice dice : dices) {
            if(!dice.getLocked()){
                if (dice.getColor().equals(color)) {
                    dice.setLocked(true);
                    return dice.roll();
                }
            } else {
                System.out.println("Dieser Würfel wurde bereits gewürfelt");
            }
        }
        System.out.println("Dieser Würfelfarbe wurde nicht gefunden");
        return -1; // Color not found
    }

    public int[] rollAllDices() {
        int[] results = new int[dices.length];
        for (int i = 0; i < dices.length; i++) {
            results[i] = dices[i].roll();
        }
        return results;
    }
}