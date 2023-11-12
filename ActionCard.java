import greenfoot.*; 
import java.util.*;

public class ActionCard extends Card {
    // Konstanten zur Definition der Kartennamen
    protected int positionOnTrack; 
    protected boolean played;
    protected CamelTrack camelTrack;
    public ActionCard(String pName) {
        super(pName); // Setzen des Kartentyps beim Erstellen der Karte
    }
    
    public void activate(Camel pCamel){
        System.out.println(this.name + "wurde von " + pCamel.getColor() + " aktiviert"); 
    }
    
    public void setCamelTrack(CamelTrack pCamelTrack){
        this.camelTrack = pCamelTrack;
    }
    
    public void setPlayed(boolean pPlayed){
        played = pPlayed;
    }
    
    public boolean getPlayed(){
        return played;
    }
    
    public int getPositionOnTrack(){
        return this.positionOnTrack;
    }
    
    public void removeActionCard(ActionCard pActioncard){
        getWorld().removeObject(pActioncard);
    }
    
    public void setPositionOnTrack(int pos){
        positionOnTrack = pos;
    }
}