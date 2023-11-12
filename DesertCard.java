import greenfoot.*;
public class DesertCard extends ActionCard
{
    public DesertCard(){
        super("DesertCard");
    }
    
    @Override 
    public void activate(Camel camel){
        // Bewege das Kamel um -1 zurück
        // Wenn es einen Kamelstapel auf der vorherigen Position gibt, hebe diesen Stapel auf und setze ihn auf den bewegten Stapel
        Camel camelAtTarget = camelTrack.getCamelAtPosition(camel.getPositionOnTrack() - 1);
        if (camelAtTarget != null) {
            Camel highestCamel = camel.getHighestCamel();
            Camel lowestCamel = camelAtTarget.getLowestCamel();
            camel.move(-1);
            highestCamel.carry(lowestCamel);
        }
        else {
            camelTrack.moveCamel(camel.getColor(), -1);
        }
        camelTrack.updateBoard();
        System.out.println(this.name + " wurde von dem  " + camel.getColor() + " Camel aktiviert (moved -1)"); 
    }
}
