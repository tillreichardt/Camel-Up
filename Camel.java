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
    
    // sedders
    public void setcamelAbove(Camel camel){
        camelAbove = camel;
    }

    public void setcamelBelow(Camel camel){
        camelBelow = camel; 
    }
    
    public void setPositionOnTrack(int value){
        if (isCarrying()) {
            // Wenn das Kamel auf einem anderen Kamel sitzt, bewege beide gemeinsam
            camelAbove.setPositionOnTrack(value);
        }
        this.positionOnTrack = value; 
    }

    // gedders
    public String getColor(){
        return color; 
    }

    public int getPositionOnTrack(){
        return positionOnTrack;
    }

    // methods 
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
    
    public boolean gettingCarried(){
        return camelBelow != null; 
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

    public boolean isCarrying() {
        return camelAbove != null;
    }
    
    public void carry(Camel camel) { // z.b. c1 soll auf c2. --> c2.carry(c1)
        camelAbove = camel;
        camel.setcamelBelow(this); 
    }
    
    @Override 
    public String toString() {
        return color + "; pos = " + positionOnTrack + "; cAbove = " +  camelsAbove() + "; cBelow = " + camelsBelow(); // Dies gibt z.B. "greenCamel" für ein grünes Kamel aus
    }
}