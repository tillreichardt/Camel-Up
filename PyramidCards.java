import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class PyramidCards extends Card{
    private boolean used;
    
    public PyramidCards(){
        super("Pyramid Card");
    }
    
    public void setUsed(boolean value){
        used = value;
    }
    
    public boolean getUsed(){
        return used;
    }
    
}
