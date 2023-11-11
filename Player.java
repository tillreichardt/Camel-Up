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
    public Player(String pname){
        this.name = pname; 
        dc = new DesertCard();
        oc = new OasisCard();
        betCardsForPlayer = new ArrayList <>();
        pyramidCardsForPlayer = new ArrayList <>();
    }

    // clearersss 
    public void clearBetCards(){
        betCardsForPlayer.clear();
    }

    public void clearPyramidCards(){
        pyramidCardsForPlayer.clear();
    }

    // sedders
    public void updateCoins(int coins){
        this.coins += coins; 
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

    @Override // ist richtig lost, lieber mit .getName() oder so 
    public String toString() {
        return name;
    }
}
