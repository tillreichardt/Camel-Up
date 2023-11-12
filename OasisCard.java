import greenfoot.*;
public class OasisCard extends ActionCard
{
    public OasisCard(){
        super("OasisCard");
    }
    
    @Override 
    public void activate(Camel camel){
        camelTrack.moveCamel(camel.getColor(), 1);
        System.out.println(this.name + " wurde von dem  " + camel.getColor() + " Camel aktiviert (moved +1)"); 
    }
}
