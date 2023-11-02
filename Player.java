import greenfoot.*;
public class Player extends Actor
{
    private int coins;
    private boolean actionCardPlayed;
    public Player(){
        actionCardPlayed = false;
    }
    
    public void setActionCardPlayed(boolean value){
        actionCardPlayed = value;
    }
    
    public boolean getActionCardPlayed(){
        return actionCardPlayed;
    }
    
}
