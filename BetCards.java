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
            if (betCardList.get(i).peek().getColor() == color){
                return betCardList.get(i).pop();
            }
        }
        return null;
    }

    public void resetBetCardList(){
        betCardList.clear();
        HashMap<Integer, Integer> punkteMap = new HashMap<>();
        punkteMap.put(2, 1);
        for (int i = 0; i < betCardList.size(); i++){
            punkteMap.put(1, 2);
            betCardList.get(i).push(new BetCard(CAMEL_COLORS[i], new HashMap<>(punkteMap)));
            punkteMap.put(1, 3);
            betCardList.get(i).push(new BetCard(CAMEL_COLORS[i], new HashMap<>(punkteMap)));
            punkteMap.put(1, 5);
            betCardList.get(i).push(new BetCard(CAMEL_COLORS[i], new HashMap<>(punkteMap)));
        }
    }
    
}
