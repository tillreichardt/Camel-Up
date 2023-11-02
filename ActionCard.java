import greenfoot.*; 
import java.util.*;

public class ActionCard extends Card {
    // Konstanten zur Definition der Kartennamen
    protected int positionOnTrack; 
    
    protected CamelTrack camelTrack;
    public ActionCard(String name) {
        super(name); // Setzen des Kartentyps beim Erstellen der Karte
    }
    
    public void activate(Camel camel){
        System.out.println(this.name + "wurde von " + camel.getColor() + " aktiviert"); 
    }
    
    public void setCamelTrack(CamelTrack camelTrack){
        this.camelTrack = camelTrack;
        System.out.println(this.camelTrack);
    }
    
    public int getPositionOnTrack(){
        return this.positionOnTrack;
    }
    
    public void setPositionOnTrack(int pos){
        positionOnTrack = pos;
    }
}