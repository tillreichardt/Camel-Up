import java.util.*;
import greenfoot.*;
public class CamelTrack extends Actor{
    private static final int BOARD_COLS = 18; // 18 Felder lang, 1. Felder spawnen die Kamele, 18. Feld ist Siegerfeld  
    private static final int BOARD_ROWS = 5; // 5 Felder hoch 
    
    private Camel[] camels; 
    private List<ActionCard> actionCardsOnTrack; // Liste, weil man weißt nicht, wie lange es wird.
    private GameLoopInterface glInterface;
    public CamelTrack(GameLoopInterface pGlInterface) {
        this.glInterface = pGlInterface; 
        actionCardsOnTrack = new ArrayList<>();
        camels = new Camel[Game.CAMEL_COLORS.length];
    }
    
    public int getSizeActionCardsOnTrack(){
        return actionCardsOnTrack.size();
    }
    
    public void removeActionCardsOnTrack(){
        for (ActionCard ac : actionCardsOnTrack){
            ac.removeActionCard(ac);
        }
        updateBoard();
    }
    
    public void moveCamel(String pCamelColor, int pSteps) {
        Camel camelToMove = getCamelByColor(pCamelColor);
        camelToMove.dropSelf();

        int targetPosition = camelToMove.getPositionOnTrack() + pSteps;
        Camel camelAtTarget = getCamelAtPosition(targetPosition);

        if (camelAtTarget != null){ // Falls targetPosition leer ist, ist highestCamel auch null und dann bekommt man nullPointerExeption
            camelAtTarget.getHighestCamel().carry(camelToMove);
        }
        camelToMove.move(pSteps);
        
        ActionCard cardAtTarget = getActionCardAtPosition(targetPosition);
        // Prüfen, ob an targetPosition eine SpecialCard liegt
        if (cardAtTarget != null){
            cardAtTarget.activate(camelToMove); 
        }
        
        // Prüfen, ob ein Kamel ins Ziel kommen wird (Feld 17 oder weiter
        if (targetPosition > BOARD_COLS-2){
            camelToMove.setPositionOnTrack(BOARD_COLS-1); // wenn targetPosition möglicherweise 19 sein sollte, wird diese auf 17 gesetzt, damit die Gewinner immer im Bild angezeigt werden 
            glInterface.setGameEnded(true);
        }
        updateBoard();
    }

    public void updateBoard(){
        int ownWidth = getImage().getWidth();
        int ownHeight = getImage().getHeight();

        int ownX = getX();
        int ownY = getY();

        float cellWidth = ownWidth / (float) BOARD_COLS; // Pixel pro Array-Einheit (Breite)
        float cellHeight = ownHeight / (float) BOARD_ROWS; // Pixel pro Array-Einheit (Höhe)

        for(int i = 0; i < camels.length; i++){
            float x = ownX - ownWidth / 2 + camels[i].getPositionOnTrack() * cellWidth + cellWidth/ 2;
            // Mitte von CamelTrack liegt bei P(450|125) bzw. P2(450|775)
            // x Koordinate (450) - eigene Bildlänge (900 / 2 = 450) = 0 --> man ist links am Rand des Bildes (x=0) 
            // 0 + posAufTrack * 50 + 25 --> damit man richtige posAufTrack hat und die halbe Zellebreite, damit man in der mitte von der Zelle ist ("Anckerpunkt" eines Bildes / Objekt ist immer in der Mitte von diesem)
            
            float y = ownY + ownHeight / 2 - camels[i].camelsBelow() * cellHeight - cellHeight / 2;
            // Das gleiche mit der y Koordinate, damit man die richtige höhe bekommt
            
            camels[i].setLocation((int)x, (int)y);
        }
        for (int i = 0; i < actionCardsOnTrack.size(); i++){
            float x = ownX - ownWidth / 2 + actionCardsOnTrack.get(i).getPositionOnTrack() * cellWidth + cellWidth/ 2;
            float y = 225;
            
            // gleiche, wie oben, für die ActionCards

            actionCardsOnTrack.get(i).setLocation((int)x, (int)y);
        }
    }

    public List<Camel> getCamelSorted(){
        // gibt eine Liste von den sortierten Kamelen zurück (erster Index der Liste ist erster Platz des Spiel während des Aufrufs, beinhaltet, dass oberster im Kamelturm erster ist ) 
        List<Camel> sortedCamels = new ArrayList<>();
        for (int i = 0; i < camels.length; i++){
            sortedCamels.add(camels[i]);
        }
        sortedCamels.sort(new Comparator<Camel>() {
                @Override
                public int compare(Camel camel1, Camel camel2) {
                    // Vergleiche die Positionen auf der Rennstrecke
                    int positionDiff = camel2.getPositionOnTrack() - camel1.getPositionOnTrack();

                    // Wenn die Positionen gleich sind, vergleiche die Anzahl der CamelsAbove
                    if (positionDiff == 0) {
                        int camelsAboveDiff = camel1.camelsAbove() - camel2.camelsAbove();
                        if (camelsAboveDiff == 0) {
                            // Wenn die CamelsAbove gleich sind, vergleichen Sie die CamelsBelow
                            return camel1.camelsBelow() - camel2.camelsBelow();
                        }
                        return camelsAboveDiff;
                    }

                    return positionDiff;
                }
            });
        return sortedCamels;
    }

    public boolean addActionCard(ActionCard pCard, int pPosition, Player pOwner) {
        if (pPosition < 1 && pPosition > BOARD_COLS-2) {
            System.out.println("Position befindet sich außerhalb des Definitionsbereiches. Muss (2-16)");
            return false;
        }
        if (getCamelAtPosition(pPosition) != null){
            System.out.println("Auf der Position befindet sich bereits ein Camel");
            return false;
        } 
        if (getCamelAtPosition(pPosition-1) != null || getCamelAtPosition(pPosition+1) != null){
            System.out.println("Du darfst keine Karte unmittelbar vor und nach einem Camel platzieren.");
            return false;
        } 
        if (getActionCardAtPosition(pPosition) != null){
            System.out.println("Auf der Position befindet sich bereits eine ActionCard");
            return false;
        }
        if (getActionCardAtPosition(pPosition-1) != null || getActionCardAtPosition(pPosition+1) != null){
            System.out.println("Du darfst keine Karte unmittelbar vor und nach einer ActionCard platzieren.");
            return false;
        }
        if (pOwner.getActionCardPlayed() == true){
            System.out.println("Du hast bereits deine ActionCard benutzt"); 
            return false; 
        }
        pCard.setCamelTrack(this);
        pCard.setPositionOnTrack(pPosition);
        pCard.setOwner(pOwner);
        pCard.setPlayed(true);
        pOwner.setActionCardPlayed(true);
        getWorld().addObject(pCard, 0, 0);
        actionCardsOnTrack.add(pCard);
        updateBoard();
        return true; 
    }

    public ActionCard getActionCardAtPosition(int pPosition){
        for (ActionCard ac : actionCardsOnTrack) {
            if (ac.getPositionOnTrack() == pPosition) {
                return ac;
            }
        }
        return null; // Kein Kamel an dieser Position gefunden
    }

    public void addCamelsToBoard(){
        int ownWidth = getImage().getWidth();
        int ownHeight = getImage().getHeight();
        int ownX = getX();
        int ownY = getY();

        // Berechnung der Umrechnungsfaktoren
        float cellWidth = ownWidth / (float) BOARD_COLS; // Pixel pro Array-Einheit (Breite)
        float cellHeight = ownHeight / (float) BOARD_ROWS; // Pixel pro Array-Einheit (Höhe)

        for (int i = 0; i < Game.CAMEL_COLORS.length; i++){
            float x = ownX - ownWidth / 2 + cellWidth / 2;
            float y = ownY - ownHeight / 2 + i * cellHeight + cellHeight / 2;
            camels[i] = new Camel(Game.CAMEL_COLORS[i], 0);
            String filename = "images/Camel-"+Game.CAMEL_COLORS[i]+".png";
            camels[i].setImage(filename);
            getWorld().addObject(camels[i], (int)x, (int)y);
        }

    }

    public Camel getCamelByColor(String color){
        for(int i = 0; i < camels.length; i++){
            if (camels[i].getColor() == color){
                return camels[i];
            } 
        }
        return null;
    }

    public Camel getCamelAtPosition(int position){
        for (int i = 0; i < camels.length; i++) {
            if (camels[i].getPositionOnTrack() == position) {
                return camels[i];
            }
        }
        return null; // Kein Kamel an dieser Position gefunden
    }

    public Camel findLowestCamelAtPosition(int position) {
        for (int j = 0; j < camels.length; j++) {
            if (camels[j].getPositionOnTrack() == position) {
                return camels[j].getLowestCamel();
            }
        }
        return null; // Kein Kamel an dieser Position gefunden
    }
}