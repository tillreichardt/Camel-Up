import greenfoot.*;  

public class Camel extends Actor
{
    private int positionOnTrack;
    private String color;
    private Camel camelAbove; //der über mir 
    private Camel camelBelow; 
    public Camel(String pColor,  int posAufStrecke){
        color = pColor;
        //setLocation(x, y);
        positionOnTrack = posAufStrecke;
        camelBelow = null; 
        camelAbove = null; 
    } 

    
    public void carry(Camel camel) { // z.b. c1 soll auf c2. --> c2.carry(c1)
        camelAbove = camel;
        camel.setcamelBelow(this); 
    }

    public int getPositionOnTrack(){
        return positionOnTrack;
    }

    public void dropAbove(){
        if(camelAbove != null ){
            camelAbove.setcamelBelow(null); 
            camelAbove = null;
        } 
    }

    public void dropSelf(){
        if(camelBelow != null){
            camelBelow.setcamelAbove(null);
            camelBelow  = null; 
        } 

    }

    public void setcamelAbove(Camel camel){
        camelAbove = camel;
    }

    public boolean isCarrying() {
        return camelAbove != null;
    }

    public void setcamelBelow(Camel camel){
        camelBelow = camel; 
    }

    public boolean gettingCarried(){
        return camelBelow != null; 
    }

    public String getColor(){
        return color; 
    }

    public void move(int schritte) {
        if (isCarrying()) {
            // Wenn das Kamel auf einem anderen Kamel sitzt, bewege beide gemeinsam
            camelAbove.move(schritte);
        }
        this.positionOnTrack += schritte;
    }

    public int camelsAbove(){
        if (isCarrying()) { 
            return camelAbove.camelsAbove() + 1;
        }
        return 0;
    }

    public int camelsBelow(){
        if (gettingCarried()) { 
            return camelBelow.camelsBelow() + 1;
        }
        return 0;
    }

    public Camel getHighestCamel() {
        if (this.camelAbove != null) {
            return this.camelAbove.getHighestCamel();
        } else {
            return this;
        }
    }
    
    public Camel getLowestCamel() {
        if (this.camelBelow != null) {
            return this.camelBelow.getLowestCamel();
        } else {
            return this;
        }
    }

    @Override 
    public String toString() {
        return color + "; pos = " + positionOnTrack + "; cAbove = " +  camelsAbove() + "; cBelow = " + camelsBelow(); // Dies gibt z.B. "greenCamel" für ein grünes Kamel aus

    }
}