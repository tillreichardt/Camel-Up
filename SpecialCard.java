import greenfoot.*; 
import java.util.*;

public class SpecialCard extends Actor {
    // Konstanten zur Definition der Kartentypen
    public static final int OASIS = 1;
    public static final int DESERT = -1;
    private SpecialCard location[] = new SpecialCard[15];
    
    private int type;  // Dies könnte 1 (Oase) oder -1 (Wüste) sein, basierend auf unseren Konstanten.

    public SpecialCard(int type) {
        this.type = type; // Setzen des Kartentyps beim Erstellen der Karte
    }
    public SpecialCard[] getLocation(){
        return this.location;
    }
    public int getType() {
        return type; // Getter-Methode, um den Kartentyp abzurufen
    }
    
    public void setLocation(int pos, SpecialCard card){
        location[pos] = card; 
    }
    // Diese Methode wird aufgerufen, wenn Kamele auf das Feld mit dieser Karte kommen.
    // Sie ändert die Stapel entsprechend, abhängig vom Typ der Spezialkarte.
    public void applyEffect(List<Camel> arrivingCamels, List<Camel> residentCamels) {
        if (type == OASIS) {
            // Bei einer Oase wird der ankommende Stapel über den vorhandenen gelegt.
            residentCamels.addAll(arrivingCamels);
        } else if (type == DESERT) {
            // In der Wüste wird der ankommende Stapel unter den vorhandenen gelegt.
            residentCamels.addAll(0, arrivingCamels); // Hinzufügen am Anfang der Liste bedeutet, dass sie "unten" im Stapel sind.
        }
        // Die Positionen der Kamele müssen möglicherweise aktualisiert werden, je nachdem, wie Ihre Logik für die Bewegung der Kamele funktioniert.
    }

    @Override
    public String toString() {
        switch (type) {
            case OASIS:
                return "Oasis Card";
            case DESERT:
                return "Desert Card";
            default:
                return "Unknown Card";
        }
    }

}
