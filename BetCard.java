import greenfoot.*;
import java.util.*;

public class BetCard{        
    private String color;        
    private HashMap<Integer, Integer> punkteMap;        
    
    public BetCard(String camelColor, HashMap<Integer, Integer> punkteMap) {            
        this.color = camelColor;            
        this.punkteMap = punkteMap;
    }        

    public String getColor() {            
        return color;        
    }        

    public int getPointsForPlacement(int platzierung) {            
        if (punkteMap.containsKey(platzierung)) {                
            return punkteMap.get(platzierung);            
        } else {                
            return -1;            
        }        
    }    
    
    @Override 
    public String toString(){
        return color + " " + getPointsForPlacement(1);
    }
}