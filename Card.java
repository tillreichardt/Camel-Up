import greenfoot.*; 

public class Card extends Actor
{
    protected String name;
    protected Player owner;

    public Card(String pName){
        this.name = pName; 
    }

    public void setOwner(Player pPlayer){
        this.owner = pPlayer;
    }

    public Player getOwner(){
        return this.owner;
    }

    public String getName() {
        return name;
    }
}
