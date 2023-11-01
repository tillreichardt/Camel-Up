import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Card extends Actor
{
    protected String name;
    protected Player owner;
    
    public Card(String name){
        this.name = name; 
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
