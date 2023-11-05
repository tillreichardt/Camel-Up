import greenfoot.*;
import java.util.*;

public class Player extends Actor
{
    private int coins;
    private String name;
    private boolean actionCardPlayed;
    private List<BetCard> betCardsForPlayer;
    public Player(String name){
        name = name; 
        betCardsForPlayer = new ArrayList <>();
        actionCardPlayed = false;
    }
    
    public void setActionCardPlayed(boolean value){
        actionCardPlayed = value;
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
}
