import java.util.*;

public class BetCards {
    private List<Stack<BetCard>> betCardList;
    private boolean successful;
    public BetCards(){
        betCardList = new ArrayList<>(Game.CAMEL_COLORS.length);
        successful = false;
        resetBetCardList(); // damit man kein duplicate Code hat
    }

    public BetCard drawBetCard(String color){
        for (int i = 0; i < betCardList.size(); i++){
            if (!betCardList.get(i).isEmpty()){
                if (betCardList.get(i).peek().getColor().equals(color)){
                    return betCardList.get(i).pop();
                }
            } else {
                System.out.println("Der " + color + " Stack ist leider schon leer.");
                return null;
            }
        }
        return null;
    }

    public void resetBetCardList(){
        betCardList.clear();
        HashMap<Integer, Integer> punkteMap = new HashMap<>();
        punkteMap.put(2, 1);

        // zuerst die Stacks für jede Farbe erstellen
        for (String color : Game.CAMEL_COLORS) {
            Stack<BetCard> stack = new Stack<>();
            punkteMap.put(1, 2);
            stack.push(new BetCard(color, new HashMap<>(punkteMap)));
            punkteMap.put(1, 3);
            stack.push(new BetCard(color, new HashMap<>(punkteMap)));
            punkteMap.put(1, 5);
            stack.push(new BetCard(color, new HashMap<>(punkteMap)));
            betCardList.add(stack); // den Stack zur Liste hinzufügen
        }
    }
    
    public boolean getSuccessful(){
        return successful;
    }
    
    public void printBetCardsInBetCardsClass(){
        System.out.println(betCardList); // dafür @Override ^^
    }
}
