import greenfoot.*;
public class DesertCard extends ActionCard
{
    public DesertCard(){
        super("DesertCard");
    }
    
    @Override 
    public void activate(Camel pCamel){
        // Bewege das Kamel um -1 zurück
        // Wenn es einen Kamelstapel auf der vorherigen Position gibt, hebe diesen Stapel auf und setze ihn auf den bewegten Stapel
        Camel camelAtTarget = camelTrack.getCamelAtPosition(pCamel.getPositionOnTrack() - 1);
        if (camelAtTarget != null) {
            Camel highestCamel = pCamel.getHighestCamel();
            Camel lowestCamel = camelAtTarget.getLowestCamel();
            pCamel.move(-1);
            highestCamel.carry(lowestCamel);
            // höchste Kamel vom bewegenden Stapel soll das unterste Kamel des vorherigen Stapels tragen 
        }
        else {
            // Wenn dort niemand ist, einfach nach hinten gehen
            camelTrack.moveCamel(pCamel.getColor(), -1);
        }
        camelTrack.updateBoard();
        System.out.println(this.name + " wurde von dem  " + pCamel.getColor() + " Camel aktiviert (moved -1)"); 
    }
}
