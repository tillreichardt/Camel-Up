import greenfoot.*; 
import java.util.*;

public class PyramidCards extends Actor{
    private Stack<PyramidCard> cards;
    
    public PyramidCards(){}
    
    public void fillStack(){
        cards.push(new PyramidCard());
    }
    
    public void usePyramidCard(Player player){
        if (!cards.isEmpty()) {
            PyramidCard card = cards.pop();
            card.setPlayer(player);
            System.out.println(player + " hat eine Pyramiden Karte benutzt und es wurde gewürfelt.");
        } else {
            System.out.println("Keine Karten mehr vorhanden.");
        }
    }
    
    public Stack<PyramidCard> giveOutCards(){
        return cards;
    }
}

