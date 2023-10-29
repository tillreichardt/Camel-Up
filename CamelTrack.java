import java.util.List;
import java.util.ArrayList;
import greenfoot.*;

public class CamelTrack extends Actor{
    private static final int BOARD_COLS = 18; // oder eine andere Größe entsprechend den Spielregeln
    private static final int BOARD_ROWS = 5; // oder eine andere Größe entsprechend den Spielregeln
    private static final String[] CAMEL_COLORS = {"white", "green", "blue", "yellow", "orange"};
    private Camel[] camels = new Camel[CAMEL_COLORS.length]; 

    public CamelTrack() {
        // Hier könnte man die Kamele auf die Startposition inistalisieren
    }

    public void addCamelsToBoard(){
        int ownWidth = getImage().getWidth();
        int ownHeight = getImage().getHeight();
        int ownX = getX();
        int ownY = getY();
        
        
        // Berechnung der Umrechnungsfaktoren
        float cellWidth = ownWidth / (float) BOARD_COLS; // Pixel pro Array-Einheit (Breite)
        float cellHeight = ownHeight / (float) BOARD_ROWS; // Pixel pro Array-Einheit (Höhe)

        // int wert = 875;
        // System.out.println("Ko: " + getX() + ", " + getY());
        for (int i = 0; i < CAMEL_COLORS.length; i++){
            float x = ownX - ownWidth / 2 + cellWidth / 2;
            float y = ownY - ownHeight / 2 + i * cellHeight + cellHeight / 2;
            camels[i] = new Camel(CAMEL_COLORS[i], 0);
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
    public void moveCamel(String camelColor, int steps) {
        Camel camelToMove = getCamelByColor(camelColor);
        camelToMove.move(steps); 
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
            float x = ownX - ownWidth / 2 + camels[i].getPositionAufStrecke() * cellWidth + cellWidth/ 2;
            float y = ownY + ownHeight / 2 - camels[i].camelsBelow() * cellHeight - cellHeight / 2;
            //        900 - 125 + 0 + 25
            camels[i].setLocation((int)x, (int)y);
        }
        printCamels();
    }
}