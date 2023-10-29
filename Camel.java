import greenfoot.*;  

public class Camel extends Actor
{
    private int positionAufStrecke;
    private String color;
    private Camel camelAufMir; //der über mir 
    private Camel camelUnterMir; 
    public Camel(String pColor,  int posAufStrecke){
        color = pColor;
        //setLocation(x, y);
        positionAufStrecke = posAufStrecke;
        camelUnterMir = null; 
        camelAufMir = null; 
    } 
    
    
    
    public void carry(Camel camel) { // z.b. c1 soll auf c2. --> c2.carry(c1)
        camelAufMir = camel;
        camel.setCamelUnterMir(this); 
    }
    
    public int getPositionAufStrecke(){
        return positionAufStrecke;
    }
    
    public void dropAbove(){
        if(camelAufMir != null ){
            camelAufMir.setCamelUnterMir(null); 
            camelAufMir = null;
        } else {System.out.println("nobody is above me");}
    }
    
    public void dropSelf(){
        if(camelUnterMir != null){
           camelUnterMir.setCamelAufMir(null);
           camelUnterMir  = null; 
        } else {System.out.println("nobody is below me");}
        
    }
    
    public void setCamelAufMir(Camel camel){
        camelAufMir = camel;
    }
    
    public boolean isCarrying() {
        return camelAufMir != null;
    }
    
    public void setCamelUnterMir(Camel camel){
        camelUnterMir = camel; 
    }
    
    public boolean gettingCarried(){
        return camelUnterMir != null; 
    }
    
    public String getColor(){
        return color; 
    }
    
    public void move(int schritte, boolean first) {
        if (isCarrying()) {
            // Wenn das Kamel auf einem anderen Kamel sitzt, bewege beide gemeinsam
            camelAufMir.move(schritte);
        } else if (camelsBelow() >= 1){
            dropSelf();
        }
        this.positionAufStrecke += schritte;
    }
    
    public int camelsAbove(){
        if (isCarrying()) { 
            return camelAufMir.camelsAbove() + 1;
        }
        return 0;
    }
    
    public int camelsBelow(){
        if (gettingCarried()) { 
            return camelUnterMir.camelsBelow() + 1;
        }
        return 0;
    }
    
    @Override 
    public String toString() {
        return color + "; pos = " + positionAufStrecke + "; cAbove = " +  camelsAbove() + "; cBelow = " + camelsBelow(); // Dies gibt z.B. "greenCamel" für ein grünes Kamel aus
        
    }
}