import greenfoot.*;
import java.util.*;

public class Player extends Actor
{
    private int coins;
    private String name;
    private DesertCard dc;
    private OasisCard oc;
    private boolean actionCardPlayed;
    private List<BetCard> betCardsForPlayer;
    private List<PyramidCard> pyramidCardsForPlayer;
    private List<EndBetCard> endBetCardsForPlayer;
    public Player(String pname){
        this.name = pname; 
        dc = new DesertCard();
        oc = new OasisCard();
        betCardsForPlayer = new ArrayList <>();
        pyramidCardsForPlayer = new ArrayList <>();
        endBetCardsForPlayer = new ArrayList <>();
        resetEndBetCards();
    }

    // clearersss 
    public void clearBetCards(){
        betCardsForPlayer.clear();
    }

    public void clearPyramidCards(){
        pyramidCardsForPlayer.clear();
    }
    
    public void resetEndBetCards(){
        endBetCardsForPlayer.clear();
        for(String color : Game.CAMEL_COLORS){
            endBetCardsForPlayer.add(new EndBetCard(this, color));
        }
    }

    // sedders
    public void addCoins(int pCoins){
        this.coins = Math.max(0, this.coins + pCoins);
    }

    public void addPyramidCard(PyramidCard pyramidCard){
        pyramidCardsForPlayer.add(pyramidCard);
    }

    public void addBetCard(BetCard pBetcard){
        betCardsForPlayer.add(pBetcard);
    }  

    public void setActionCardPlayed(boolean value){
        actionCardPlayed = value;
    }

    // gedders
    public List<BetCard> getAllBetCards(){
        return betCardsForPlayer; 
    }

    public List<PyramidCard> getAllPyramidCards(){
        return pyramidCardsForPlayer;
    }
    
    public List<EndBetCard> getEndBetCardsForPlayer(){
        return endBetCardsForPlayer;
    }
    
    public OasisCard getOasisCard(){
        return oc;
    }

    public DesertCard getDesertCard(){
        return dc;
    }

    public boolean getActionCardPlayed(){
        return actionCardPlayed;
    }

    public String getName(){
        return name; 
    }
    
    public int getCoins(){
        return coins;
    }
    
    public int getEndBetsCardsSize(){
        return endBetCardsForPlayer.size();
    }
    
    // EndBetCard verbrauchen
    public EndBetCard getEndBetsCard(String color){
        if (getEndBetsCardsSize() == 0){
            System.out.println(name + " hat keine EndBetCards mehr.");
            return null;
        }
        for (EndBetCard endbetcard : endBetCardsForPlayer){
            if (endbetcard.getColor().equals(color)){
                EndBetCard result = endbetcard;
                endBetCardsForPlayer.remove(endbetcard);
                return result;
            }
        }
        System.out.println("Die Farbe wurde nicht gefunden");
        return null; 
    }
    
    public List<EndBetCard> getAvailableEndBetCards(){
        return endBetCardsForPlayer;
    }
    
    
    @Override // damit spart man sich immer .getName() und wenn man z.b. eine Liste von Spielern printen möchte, kann ich einfach System.out.println(liste); machen, anstatt dann durch diese zu iterieren und jedes mal System.out.println(liste.get(i).getName())
    public String toString() {
        return name;
    }
}
