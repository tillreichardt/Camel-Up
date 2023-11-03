import java.util.Stack;

public class BetCards {
    private static final String[] COLORS = {"white", "green", "blue", "yellow", "orange"};
    private Stack<BetCard>[] betCardStacks; // Array of stacks for each camel color

    public BetCards() {
        betCardStacks = new Stack[COLORS.length];
        for (int i = 0; i < betCardStacks.length; i++) {
            betCardStacks[i] = new Stack<>();
            // Populate each stack with 5 BetCard objects
            for (int j = 0; j < 5; j++) {
                betCardStacks[i].push(new BetCard(5 - j)); // Assign coin values 5, 4, 3, 2, 1
            }
        }
    }

    public int getCoinsForBet(String camelColor, int position) {
        int colorIndex = -1;
        for (int i = 0; i < COLORS.length; i++) {
            if (COLORS[i].equalsIgnoreCase(camelColor)) {
                colorIndex = i;
                break;
            }
        }
        
        if (colorIndex == -1) {
            throw new IllegalArgumentException("Invalid camel color");
        }

        // fehlt noch logik für coins 
        
        return 0;
    }
    public BetCard useBetCard(String camelColor) {
        int colorIndex = -1;
        for (int i = 0; i < COLORS.length; i++) {
            if (COLORS[i].equalsIgnoreCase(camelColor)) {
                colorIndex = i;
                break;
            }
        }
        
        if (colorIndex == -1) {
            throw new IllegalArgumentException("Invalid camel color");
        }

        // gucke, ob der Stack von einer Farbe noch nicht leer ist. 
        if (!betCardStacks[colorIndex].isEmpty()) {
            return betCardStacks[colorIndex].pop();
        } else {
            System.out.println("No bet cards left for " + camelColor + " camel.");
            return null;
        }
    }


    private class BetCard {
        int coins;

        public BetCard(int coins) {
            this.coins = coins;
        }

        
        public int getCoins() {
            return coins;
        }
    }
}
