import java.util.List;
import java.util.ArrayList;
import greenfoot.*;

public class CamelTrack extends Actor{
    private static final int BOARD_WIDTH = 18; // oder eine andere Größe entsprechend den Spielregeln
    private static final int BOARD_HEIGHT = 5; // oder eine andere Größe entsprechend den Spielregeln
    Camel[][] board = new Camel[BOARD_HEIGHT][BOARD_WIDTH];
    private boolean gameEnded;

    private int totalPixelWidth = 750; // von 50 bis 800
    private int totalPixelHeight = 250; // von 900 bis 650

    private int arrayWidth = 16; // effektive Breite, Spalten 1 bis 16
    private int arrayHeight = 5;

    // Berechnung der Umrechnungsfaktoren
    private float conversionFactorX = totalPixelWidth / (float) arrayWidth; // Pixel pro Array-Einheit (Breite)
    private float conversionFactorY = totalPixelHeight / (float) arrayHeight; // Pixel pro Array-Einheit (Höhe)

    public void addCamelToBoard(int height, int width, Camel camel){
        board[height][width] =  camel;
    }

    public CamelTrack() {
        gameEnded = false;
        // Hier könnte man die Kamele auf die Startposition inistalisieren
    }

    public void printBoard() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == null) {
                    System.out.print("[ ]\t"); // leeres Feld darstellen
                } else {
                    System.out.print("[" + board[i][j].toString() + "]\t"); // das Kamel darstellen
                }
            }
            System.out.println(); // neue Zeile am Ende jeder Brettzeile
        }
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void moveCamel(Camel camel, int steps) {
        // Speichern der aktuellen Position des Kamels, bevor es sich bewegt
        int oldArrayIndexX = (camel.getX() - 50) / 50; // Umwandlung von Pixeln in Index
        int oldArrayIndexY = (900 - camel.getY()) / 50; // Umwandlung von Pixeln in Index und Umkehrung der Y-Koordinaten

        camel.bewege(steps); // Das Kamel bewegen. Dies sollte auch die Pixelkoordinaten des Kamels aktualisieren.

        // Neue Positionen nach der Bewegung berechnen
        int camelPixelX = camel.getX(); // X-Koordinate des Kamels in Pixeln
        int camelPixelY = camel.getY(); // Y-Koordinate des Kamels in Pixeln

        // Umrechnung der Pixelkoordinaten in Array-Indizes
        int arrayIndexX = (camelPixelX - 50) / 50 + 1; // Wir haben 50 Pixel als Größe für jedes Feld angenommen.
        int arrayIndexY = (900 - camelPixelY) / 50; // Umkehrung der Y-Koordinaten

        // Das Kamel an seiner alten Position entfernen
        if (board[oldArrayIndexY][oldArrayIndexX] == camel) {
            board[oldArrayIndexY][oldArrayIndexX] = null; // Stellen Sie sicher, dass wir das richtige Kamel entfernen
            System.out.println("wurde entfernt");
        }

        // Überprüfung und Platzierung des Kamels im Array an der neuen Position
        if (board[4][arrayIndexX] == null) {
            board[4][arrayIndexX] = camel;
            camel.setCoordinates(camel.getX(), 900); // Die Y-Koordinate auf den Boden setzen
        } else {
            // Finden Sie die erste freie Position über dem aktuellen Platz
            while (arrayIndexY > 0) {
                arrayIndexY--; // Gehe eine Position nach oben
                if (board[arrayIndexY][arrayIndexX] == null) {
                    // Freier Platz gefunden, platzieren Sie das Kamel hier
                    board[arrayIndexY][arrayIndexX] = camel;
                    camel.setCoordinates(camel.getX(), 900 - (arrayIndexY * 50)); // Korrekte Y-Koordinate setzen basierend auf der Höhe
                    break; // Schleife beenden, da das Kamel platziert wurde
                }
            }
        }
    }
    //Stack von einem Feld geben 
    
}
