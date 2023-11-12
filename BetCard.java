import greenfoot.*;
import java.util.*;

public class BetCard{        
    private String color;        
    private HashMap<Integer, Integer> punkteMap; // Coins       
    
    public BetCard(String camelColor, HashMap<Integer, Integer> punkteMap) {            
        this.color = camelColor;            
        this.punkteMap = punkteMap;
    }        

    public String getColor() {            
        return color;        
    }        

    public int getCoinsForPlacement(int pPlatzierung) {            
        if (punkteMap.containsKey(pPlatzierung)) {                
            return punkteMap.get(pPlatzierung);            
        } else {                
            return -1;            
        }        
    }    
    
    @Override 
    public String toString(){
        return color + " " + getCoinsForPlacement(1);
    }
}