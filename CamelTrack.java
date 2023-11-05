import java.util.*;
import greenfoot.*;
public class CamelTrack extends Actor{
    private static final int BOARD_COLS = 18;
    private static final int BOARD_ROWS = 5;

    private Dice[] dicers;
    private static final String[] CAMEL_COLORS = {"white", "green", "blue", "yellow", "orange"};
    private Camel[] camels = new Camel[CAMEL_COLORS.length]; 
    private List<ActionCard> actionCardsOnTrack = new ArrayList<>(); // Liste, weil man weißt nicht, wie lange es wird.
    public CamelTrack() {

    }

    public void moveCamel(String camelColor, int steps) {
        Camel camelToMove = getCamelByColor(camelColor);
        camelToMove.dropSelf();

        int targetPosition = camelToMove.getPositionOnTrack() + steps;
        Camel camelAtTarget = getCamelAtPosition(targetPosition);

        if (camelAtTarget != null){ // Falls targetPosition leer ist, ist highestCamel auch null und dann bekommt man nullPointerExeption
            camelAtTarget.getHighestCamel().carry(camelToMove);
        }
        camelToMove.move(steps); 
        ActionCard cardAtTarget = getActionCardAtPosition(targetPosition);
        // gucken, ob an targetPosition eine SpecialCard liegt
        if (cardAtTarget != null){
            cardAtTarget.activate(camelToMove); 
        }
        updateBoard();
    }

    public void updateBoard(){
        // Wir müssen durch alle durch loopen
        // Und die Position snacken
        // und gucken ob sie auf jemanden sitzen 
        // wenn sie auf einem sitzen, nach oben schieben in der reihenfolge 

        //1. Durch loopen
        int ownWidth = getImage().getWidth();
        int ownHeight = getImage().getHeight();

        int ownX = getX();
        int ownY = getY();

        float cellWidth = ownWidth / (float) BOARD_COLS; // Pixel pro Array-Einheit (Breite)
        float cellHeight = ownHeight / (float) BOARD_ROWS; // Pixel pro Array-Einheit (Höhe)

        for(int i = 0; i < camels.length; i++){
            float x = ownX - ownWidth / 2 + camels[i].getPositionOnTrack() * cellWidth + cellWidth/ 2;
            float y = ownY + ownHeight / 2 - camels[i].camelsBelow() * cellHeight - cellHeight / 2;
            //        900 - 125 + 0 + 25
            camels[i].setLocation((int)x, (int)y);
        }
        for (int i = 0; i < actionCardsOnTrack.size(); i++){
            float x = ownX - ownWidth / 2 + actionCardsOnTrack.get(i).getPositionOnTrack() * cellWidth + cellWidth/ 2;
            float y = 875;

            actionCardsOnTrack.get(i).setLocation((int)x, (int)y);
        }
        printCamels();
    }

    public List<Camel> getCamelSorted(){
        List<Camel> sortedCamels = new ArrayList<>();
        for (int i = 0; i < 5; i++){
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

    public void addActionCard(ActionCard card, int position, Player Owner) {
        if (position < 1 && position > BOARD_COLS-2) {
            System.out.println("Position befindet sich außerhalb des Definitionsbereiches. (2-16)");
            return;
        }
        if (getCamelAtPosition(position) != null){
            System.out.println("Auf der Position befindet sich bereits ein Camel");
            return;
        } 
        if (getCamelAtPosition(position-1) != null && getCamelAtPosition(position+1) != null){
            System.out.println("Du darfst keine Karte unmittelbar vor und nach einem Camel platzieren.");
            return;
        } 
        if (getActionCardAtPosition(position) != null){
            System.out.println("Auf der Position befindet sich bereits eine ActionCard");
            return;
        }
        if (getActionCardAtPosition(position-1) != null && getActionCardAtPosition(position+1) != null){
            System.out.println("Du darfst keine Karte unmittelbar vor und nach einer ActionCard platzieren.");
            return;
        }
        if (Owner.getActionCardPlayed() == true){
            System.out.println("Du hast bereits deine ActionCard benutzt"); 
            return; 
        }
        card.setCamelTrack(this);
        card.setPositionOnTrack(position);
        card.setOwner(Owner);
        card.setPlayed(true);
        Owner.setActionCardPlayed(true);
        actionCardsOnTrack.add(card);
    }

    public ActionCard getActionCardAtPosition(int position){
        for (ActionCard ac : actionCardsOnTrack) {
            if (ac.getPositionOnTrack() == position) {
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

        for (int i = 0; i < CAMEL_COLORS.length; i++){
            float x = ownX - ownWidth / 2 + cellWidth / 2;
            float y = ownY - ownHeight / 2 + i * cellHeight + cellHeight / 2;
            camels[i] = new Camel(CAMEL_COLORS[i], 0);
            String filename = "images/"+CAMEL_COLORS[i]+".png";
            camels[i].setImage(filename);
            getWorld().addObject(camels[i], (int)x, (int)y);
        }

    }

    public void printCamels(){
        System.out.println("\n");
        for(int i = 0; i < camels.length; i++){
            System.out.println(camels[i]);
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