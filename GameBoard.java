import java.util.List;
import java.util.ArrayList;
import greenfoot.*;

public class GameBoard extends Actor{
    private static final int BOARD_WIDTH = 18; // oder eine andere Größe entsprechend den Spielregeln
    private static final int BOARD_HIGHT = 5; // oder eine andere Größe entsprechend den Spielregeln
    Camel[][] board = new Camel[BOARD_HIGHT][BOARD_WIDTH];
    private boolean gameEnded;

    public GameBoard() {
        gameEnded = false;
        // Hier könnten Sie Kamele auf ihren Startpositionen initialisieren
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void moveCamel(Camel camel, int steps) {
        // alte Koordinaten speichern
        int oldX = camel.getX();
        int oldY = camel.getY();

        // Bewege das Kamel. Dies ändert auch seine Koordinaten in der grafischen Oberfläche
        camel.bewege(steps);

        // neue Koordinaten holen
        int newX = camel.getX();
        int newY = camel.getY();

        // Logische Aktualisierung der Positionen in der Datenstruktur des Spielbretts
        updateBoardWithCamelMovement(camel, oldX, oldY, newX, newY);
    }

    /**
     * Aktualisiert das Spielbrett, um die Bewegung eines Kamels von einer alten Position zu einer neuen zu reflektieren.
     */
    private void updateBoardWithCamelMovement(Camel camel, int oldX, int oldY, int newX, int newY) {
        // Entfernen Sie das Kamel von seiner alten Position in Ihrer Datenstruktur
        removeCamelAtCoordinates(oldX, oldY, camel);

        // Fügen Sie das Kamel an seiner neuen Position in Ihrer Datenstruktur hinzu
        addCamelAtCoordinates(newX, newY, camel);
    }

    private void removeCamelAtCoordinates(int x, int y, Camel camel) {
        if (board[y][x] == camel) {
            board[y][x] = null; // Entferne das Kamel, indem Sie die Position auf 'null' setzen
        }
    }

    private void addCamelAtCoordinates(int x, int y, Camel camel) {
        if (board[4][x] == null){
            board[4][x] = camel;  
        } else {
            board[y][x] = camel;
        }
    }

    // Weitere Methoden wie das Abrufen eines Kamels an einer bestimmten Position könnten hier ebenfalls hinzugefügt werden
}
