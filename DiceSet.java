import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceSet {
    private List<dice> availableDices;
    private List<dice> rolledDices;

    public DiceSet(String[] colors) {
        availableDices = new ArrayList<>();
        rolledDices = new ArrayList<>();
        for (String color : colors) {
            availableDices.add(new dice(color));
        }
    }

    public dice rollDiceByColor(String color) {
        for (dice dice : availableDices) {
            if (dice.getColor().equals(color)) {
                dice.roll();
                moveDiceToRolledDices(dice);
                return dice;
            }
        }

        for (dice dice : rolledDices) {
            if (dice.getColor().equals(color)) {
                System.out.println("Dieser Würfel mit der Farbe '" + color + "' wurde bereits geworfen");
                return null;
            }
        }

        System.out.println("Dieser Würfel mit der Farbe '" + color + "' existiert nicht");
        return null;
    }

    public dice rollRandomDice() {
        if (availableDices.isEmpty()) {
            System.out.println("Es gibt keine verfügbaren Würfel mehr.");
            return null;
        }

        int randomIndex = new Random().nextInt(availableDices.size());
        dice randomDice = availableDices.get(randomIndex);
        randomDice.roll();
        moveDiceToRolledDices(randomDice);
        return randomDice;
    }

    public dice[] rollAllAvailableDices() {
        dice[] results = new dice[availableDices.size()];
        for (int i = 0; i < availableDices.size(); i++) {
            dice dice = availableDices.get(i);
            dice.roll();
            results[i] = dice;
        }
        // move all available dices to rolledDices for consistency
        rolledDices.addAll(availableDices);
        availableDices.clear();
        return results;
    }

    public void resetDiceSet() {
        // 1. Bewegt alle in rolledDices befindlichen Würfel zurück in availableDices
        availableDices.addAll(rolledDices);
        rolledDices.clear();

        // 2. Setzt den Zustand jedes Würfels zurück
        for (dice dice : availableDices) {
            dice.reset();
        }
    }

    private void moveDiceToRolledDices(dice dice) {
        availableDices.remove(dice);
        rolledDices.add(dice);
    }
       
}