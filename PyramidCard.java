import greenfoot.*;
import java.util.*;

public class PyramidCard extends PyramidCards{
    private boolean used;
    private Player player;
    private int coinsValue;
    public PyramidCard(){
        coinsValue = 1;
    }

    public void setUsed(boolean value){
        used = value;
    }

    public boolean getUsed(){
        return used;
    }

    public void setPlayer(Player player){
        this.player = player; 
    }
    
    public Player getPlayer(){
        return player;
    }
}