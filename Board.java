import greenfoot.*;  
import java.util.*;

public class Board extends Actor {
    private List<List<Camel>> fields;
    private SpecialCard card; 
    private int specialCardPos;
    public Board() {
        fields = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            fields.add(new ArrayList<>());
        }
    }

    public void createCard(int type, int pos){
        
        if (type == 1 || type == -1 && fields.get(pos) == null){
            specialCardPos = pos;
            card = new SpecialCard(type);
            card.setLocation(specialCardPos, card); 
        } else {
            System.out.println("Entweder kannst du keine 1 oder -1 schreiben oder ");
        }
    }

    public void addCamelToField(int index, Camel camel) {
        if (index >= 0 && index < fields.size()) {
            fields.get(index).add(camel);
            camel.setLocation(index);
        } else {
            System.out.println("index is out of bounds du sohn ");
        }
    }

    public void printBoard(){
        System.out.println(fields); 
    }
    
    public void printCards(){
        System.out.println(Arrays.toString(card.getLocation()));
    }

    public void moveCamelStack(int fromField, int toField, Camel specificCamel) {
        // Prüfen, ob das Zielfeld gültig ist.
        if (toField < 0 || toField >= fields.size()) {
            // Ungültiges Zielfeld, Aktion kann nicht durchgeführt werden.
            return;
        }

        List<Camel> toStack = fields.get(toField);
        List<Camel> camelsToMove = new ArrayList<>();

        if (fromField >= 0 && fromField < fields.size()) { // Wenn das Startfeld gültig ist, sind wir mitten im Spiel.
            List<Camel> fromStack = fields.get(fromField);

            boolean found = false;
            for (Camel camel : fromStack) {
                if (camel == specificCamel) {
                    found = true;
                }
                if (found) {
                    camelsToMove.add(camel);
                }
            }

            if (found) {
                // Entferne die Kamele vom Ausgangsfeld.
                fromStack.removeAll(camelsToMove);
            } else {
                // Das spezifische Kamel wurde nicht gefunden, es kann nicht bewegt werden.
                return;
            }
        } else { 
            // Wenn das Startfeld ungültig ist, fügen wir ein neues Kamel hinzu, da wir annehmen, dass das Spiel gerade erst beginnt.
            camelsToMove.add(specificCamel);
        }

        // Füge die zu bewegenden Kamele dem Zielfeld hinzu.
        for (Camel camel : camelsToMove) {
            toStack.add(camel);
            camel.setLocation(toField);
        }

        if (specialCardPos == toField) {
            card.applyEffect(camelsToMove, fields.get(specialCardPos));
        }
        // Konsolenausgabe für die Bewegung
        Camel[] movedCamels = new Camel[camelsToMove.size()];
        movedCamels = camelsToMove.toArray(movedCamels); // ArrayList in ein Array machen von den camelsToMove
        int echtesFeld = toField + 1;
        System.out.println("Es wurden folgende Kamele: "  + Arrays.toString(movedCamels) + " auf das " + echtesFeld + ". Feld gesetzt: ");
    }

    public Camel getTopCamelAtField(int fieldIndex) {
        // Überprüft, ob der Index außerhalb des gültigen Bereichs liegt oder das Feld keine Kamele hat.
        if (fieldIndex < 0 || fieldIndex >= fields.size() || fields.get(fieldIndex).isEmpty()) {
            return null; // Rückgabe von 'null' als Indikator für einen ungültigen Zustand.
        }

        List<Camel> stackAtField = fields.get(fieldIndex);
        return stackAtField.get(stackAtField.size() - 1);  // Gibt das oberste Kamel zurück
    }
}
