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
    public Player(String name){
        name = name; 
        dc = new DesertCard();
        oc = new OasisCard();
        betCardsForPlayer = new ArrayList <>();
        actionCardPlayed = false;
    }
    
    public void setActionCardPlayed(boolean value){
        actionCardPlayed = value;
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
    
    public void addBetCard(BetCard pBetcard){
        betCardsForPlayer.add(pBetcard);
    }   
    
    public List<BetCard> getAllBetCards(){
        return betCardsForPlayer; 
    }
    
    public void clearBetCards(){
        betCardsForPlayer.clear();
    }
    
    public void setCoins(int coins){
        this.coins = coins; 
    }
    
    public void addPyramidCard(PyramidCard pyramidCard){
        pyramidCardsForPlayer.add(pyramidCard);
    }
    
    public List<PyramidCard> getAllPyramidCards(){
        return pyramidCardsForPlayer;
    }
}
