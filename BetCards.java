import java.util.*;

public class BetCards {
    private static final String[] CAMEL_COLORS = {"white", "green", "blue", "yellow", "orange"};
    private List<Stack<BetCard>> betCardList;

    public BetCards(){
        betCardList = new ArrayList<>(CAMEL_COLORS.length);
        resetBetCardList(); // damit man kein duplicate Code hat
    }

    public BetCard drawBetCard(String color){
    for (int i = 0; i < betCardList.size(); i++){
        if (betCardList.get(i).peek().getColor().equals(color)){
            return betCardList.get(i).pop();
        }
    }
    return null;
}


    public void resetBetCardList(){
        betCardList.clear();
        HashMap<Integer, Integer> punkteMap = new HashMap<>();
        punkteMap.put(2, 1);

        // Erstellen Sie zuerst die Stacks für jede Farbe
        for (String color : CAMEL_COLORS) {
            Stack<BetCard> stack = new Stack<>();
            punkteMap.put(1, 2);
            stack.push(new BetCard(color, new HashMap<>(punkteMap)));
            punkteMap.put(1, 3);
            stack.push(new BetCard(color, new HashMap<>(punkteMap)));
            punkteMap.put(1, 5);
            stack.push(new BetCard(color, new HashMap<>(punkteMap)));
            betCardList.add(stack); // Fügen Sie den Stack zur Liste hinzu
        }
    }

    public void printBetCardsInBetCardsClass(){
        System.out.println(betCardList);
    }
}
