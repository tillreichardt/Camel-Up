import greenfoot.*; 
import java.util.*;

public class PyramidCards extends Actor{
    private Stack<PyramidCard> cards;
    
    public PyramidCards(){}
    
    public void fillStack(){
        cards.push(new PyramidCard());
    }
    
    public PyramidCard usePyramidCard(){
        if (!cards.isEmpty()) {
            System.out.println("Es wurde eine Pyramiden Karte benutzt und es wurde gewürfelt.");
            return cards.pop();
        } else {
            System.out.println("Keine Karten mehr vorhanden.");
            return null;
        }
    }
    
    public Stack<PyramidCard> giveOutCards(){
        return cards;
    }
}

