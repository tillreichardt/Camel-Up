import greenfoot.*; 
import java.util.*;

public class ActionCard extends Card {
    // Konstanten zur Definition der Kartennamen
    protected int positionOnTrack; 
    protected boolean played;
    protected CamelTrack camelTrack;
    public ActionCard(String name) {
        super(name); // Setzen des Kartentyps beim Erstellen der Karte
        played = false;
    }
    
    public void activate(Camel camel){
        System.out.println(this.name + "wurde von " + camel.getColor() + " aktiviert"); 
    }
    
    public void setCamelTrack(CamelTrack camelTrack){
        this.camelTrack = camelTrack;
    }
    
    public void setPlayed(boolean value){
        played = value;
    }
    
    public boolean getPlayed(){
        return played;
    }
    
    public int getPositionOnTrack(){
        return this.positionOnTrack;
    }
    
    public void setPositionOnTrack(int pos){
        positionOnTrack = pos;
    }
}