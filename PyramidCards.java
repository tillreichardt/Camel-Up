import java.util.*;
public class PyramidCards{
    private Stack<PyramidCard> cards;
    
    public PyramidCards(){
        cards = new Stack<>();
        this.reset();
    }
    
    public void reset(){
        cards.clear();
        for (int i = 0; i <= 5; i++){
            this.cards.push(new PyramidCard());
        }
    }
    
    public PyramidCard getPyramidCard(){
        if (!cards.isEmpty()) {
            System.out.println("Es wurde eine Pyramiden Karte gezogen");
            return cards.pop();
        } else {
            System.out.println("Keine Karten mehr vorhanden.");
            return null;
        }
    }
}