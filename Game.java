import greenfoot.*;
import java.util.*;
interface GameLoopInterface{
    void setGameEnded(boolean value);
}
public class Game extends World implements GameLoopInterface{
    // Konstanten
    private static final int GRID_SIZE = 50;
    private static final int BET_CARD = 1;
    private static final int ACTION_CARD = 2;
    private static final int PYRAMID_CARD = 3;
    private static final int END_GAME_BET = 4;
    public static final String[] CAMEL_COLORS = {"white", "green", "blue", "yellow", "orange"}; // public und ohne getter, weil eh static final bzw. Konstante also kann man eh nicht ändern -> Datenkapselung. Soll globale Variable sein (ergibt auch Sinn, sonst immer als Parameter übergeben) 

    private boolean gameEnded;

    private CamelTrack rennBahn = new CamelTrack(this);

    private int startPlayer = 0;
    private Player[] players;
    private PyramidCards pyramidCards = new PyramidCards();
    private DiceSet dicersSet = new DiceSet(CAMEL_COLORS);
    private BetCards betCards = new BetCards();
    private EndBetCards endBetCards = new EndBetCards();
    private boolean stageEvaluatedPostGame;
    public Game(/*int numberOfPlayers*/){ 
        super (18*GRID_SIZE, 18*GRID_SIZE, 1);
        addObject(rennBahn, 450, 775);
        setup(/*numberOfPlayers*/);
    }

    private void setup(/*int numberOfPlayers*/){
        int numberOfPlayers = 6;
        players = new Player[numberOfPlayers];
        String[] mustHaveNames = {"NullPointerNinja", "ExceptionExplorer", "ClassClown", "DebugDragon", "PixelPirate", "BugHunter"};

        if (numberOfPlayers <= 6){
            System.out.println("\n\n");
            for (int i = 0; i < numberOfPlayers; i++) {
                players[i] = new Player(mustHaveNames[i]);
                System.out.println(mustHaveNames[i] + " ist dem Spiel beigetreten");
            }
            System.out.println("\n\n");
        }
        else {
            System.out.println("Du kannst maximal nur 8 Spieler haben");
        }

        rennBahn.addCamelsToBoard();

        startGame();

    }

    public void act(){
        // Check if the game has already ended
        if (gameEnded) {
            // Perform one last stage evaluation if this is the first call after game end
            if (!stageEvaluatedPostGame) {
                stageEvaluation();
                stageEvaluatedPostGame = true; // Ensure this block doesn't run again
                System.out.println("\n"+ getWinner() + " hat das Spiel gewonnen!");
                System.out.println("\nDas Spiel ist vorbei");
            }
            return; // Do nothing more if the game has ended
        }

        // Check if only the stage has ended
        if (isStageEnded()) {

        }
        System.out.println(startPlayer);
        Scanner scan = new Scanner(System.in);
        for (int i = 0; !gameEnded && !isStageEnded() && i < players.length; i++){ // Beispiel Etappe vorbei. Spieler 3 letzen Zug, Spieler 4 als nächstes in neuer Etappe 
            int currentIndex = (startPlayer + i) % players.length;
            Player activePlayer = players[currentIndex];
            int response = (int) getUserInput(scan, "\n" + activePlayer + " gib eine Option (1 - 4) ein: ");
            System.out.println(response);

            // advanced switch case (nur ab Java 9) 
            switch(response){
                case BET_CARD -> {
                        String pColor = (String) getUserInput(scan, "Auf welches Kamel wettest du? Farbe: ");
                        activePlayer.addBetCard(betCards.drawBetCard(pColor));
                        System.out.println(activePlayer + " hat folgende BetCards: " + activePlayer.getAllBetCards());

                    }

                case ACTION_CARD -> {
                        String actionCard = (String) getUserInput(scan, "Soll eine DesertCard (dc) oder OasisCard (oc) gespielt werden?: ");
                        int pPosition = (int) getUserInput(scan, "Auf welche Position soll diese ActionCard?: ");

                        // Prüfe auf ungültige Eingabe
                        if (!actionCard.equals("dc") && !actionCard.equals("oc")) {
                            System.out.println("Ungültige Eingabe für die ActionCard. Bitte wähle 'dc' oder 'oc'.");
                            break;
                        }

                        // Prüfe, ob der Spieler bereits eine ActionCard gespielt hat
                        if (activePlayer.getActionCardPlayed()) {
                            System.out.println("Du hast bereits eine ActionCard gespielt, wähle eine andere Option");
                            break;
                        }

                        // Führe die Aktion für die entsprechende Karte aus
                        if (actionCard.equals("dc")) {
                            rennBahn.addActionCard(activePlayer.getDesertCard(), pPosition, activePlayer);
                        } else { // actionCard.equals("oc")
                            rennBahn.addActionCard(activePlayer.getOasisCard(), pPosition, activePlayer);
                        }
                        Greenfoot.delay(1);

                    }

                case PYRAMID_CARD -> {
                        activePlayer.addPyramidCard(pyramidCards.getPyramidCard());
                        Dice dice = dicersSet.rollRandomDice(); // sonst wird die Methode rollRandomDice() zweimal ausgeführt
                        rennBahn.moveCamel(dice.getColor(), dice.getValue());
                        System.out.println("Es gibt noch "+ pyramidCards.getSize()  + " Pyramiden Karten");
                        Greenfoot.delay(1);

                        if (isStageEnded()) {
                            System.out.println("Die Etappe ist vorbei");
                            startPlayer = currentIndex + 1; // Token den Spieler links von uns weiter geben (+1) 
                            stageEvaluation();
                            resetStage();
                        }

                    }
                case END_GAME_BET -> {
                        String pppColor = (String) getUserInput(scan, "choose a camel color to bet on: ");
                        String pPosition = (String) getUserInput(scan, "Welche Position denkst du hast das Kamel am Ende des Spiels? (first/last): ");
                        makeEndGameBet(activePlayer, pppColor, pPosition);

                    }

                case 5 ->  betCards.printBetCardsInBetCardsClass();

                default -> System.out.println("\nUngültige Auswahl. Bitte wähle 1 oder 5.");
            } 
        }
    }

    public void moveWhite(){
        rennBahn.moveCamel("white", 1);
    }

    public void resetStage(){
        betCards.resetBetCardList();
        pyramidCards.resetPyramidCards();
        dicersSet.resetDiceSet();
        for(Player player : players){
            player.clearBetCards();
            player.clearPyramidCards();
            player.setActionCardPlayed(false);
        }
        rennBahn.removeActionCardsOnTrack();
    }

    public void makeEndGameBet(Player player, String camelColor, String position) {
        // Überprüfen, ob der Spieler bereits eine Wette auf die Farbe gesetzt hat;
        if (position.equals("first")){
            endBetCards.addBetForFirst(player.getEndBetsCard(camelColor));
            System.out.println(player.getName() + " hat eine Wette auf das Kamel mit der Farbe " + camelColor + " für den " + position + " place gesetzt.");
        } else if (position.equals("last")) {
            endBetCards.addBetForLast(player.getEndBetsCard(camelColor));
            System.out.println(player.getName() + " hat eine Wette auf das Kamel mit der Farbe " + camelColor + " für den " + position + " place gesetzt.");
        } else {
            System.out.println("Ungültige Auswahl. Bitte wähle first oder last");
        }
    }

    public void evaluateEndGameBets() {
        if (!gameEnded) return;  // sicherstellen, dass die methode nur nach dem spiel ende ausgeführt wird
        List<Camel> sortedCamels = rennBahn.getCamelSorted();
        Camel firstPlaceCamel = sortedCamels.get(0);
        Camel lastPlaceCamel = sortedCamels.get(sortedCamels.size() - 1);

        HashMap<Integer, Integer> rewardMap = new HashMap<>(); 
        int[] rewards = {8, 5, 3, 2};
        for (int i = 0; i < rewards.length; i++){
            rewardMap.put(i+1, rewards[i]);   
        } 

        // first camel 
        int matches = 0; 
        for (EndBetCard bet : endBetCards.getBetsForFirstCamel()) {
            String camelColor = bet.getColor();
            if (camelColor.equals(firstPlaceCamel.getColor())) { // Wette war richtig --> Farbe der Wette stimmt mit der Farbe des ersten Kamels überein
                int rewardsPoints = rewardMap.containsKey(++matches) ? rewardMap.get(matches) : 1;
                bet.getPlayer().addCoins(rewardsPoints);
                System.out.println(bet.getPlayer() + " hat " + rewardsPoints +" Coins erhalten, dafür dass er eine Wette auf das richtige, erste Kamel gesetzt hat.");
            } else {
                bet.getPlayer().addCoins(-1);
                System.out.println(bet.getPlayer() + " hat 1 Coin verloren, dafür dass er eine Wette auf das falsche, erste Kamel gesetzt hat.");
            }
        }

        // last camel
        matches = 0; 
        for (EndBetCard bet : endBetCards.getBetsForLastCamel()) {
            String camelColor = bet.getColor();
            if (camelColor.equals(lastPlaceCamel.getColor())) { // Wette war richtig --> Farbe der Wette stimmt mit der Farbe des ersten Kamels überein
                int rewardsPoints = rewardMap.containsKey(++matches) ? rewardMap.get(matches) : 1;
                bet.getPlayer().addCoins(rewardsPoints);
                System.out.println(bet.getPlayer() + " hat " + rewardsPoints +" Coins erhalten, dafür dass er eine Wette auf das richtige, letzte Kamel gesetzt hat.");
            } else {
                bet.getPlayer().addCoins(-1);
                System.out.println(bet.getPlayer() + " hat 1 Coin verloren, dafür dass er eine Wette auf das falsche, letzte Kamel gesetzt hat.");
            }
        }
    }

    public void stageEvaluation(){
        System.out.println("\n");
        List<Camel> sortedCamels = rennBahn.getCamelSorted();
        Camel firstPlaceCamel = sortedCamels.get(0);
        Camel lastPlaceCamel = sortedCamels.get(sortedCamels.size() - 1);
        for (int i = 0; i < players.length; i++){ // durch alle Spieler; 
            List<BetCard> playerBetCards = players[i].getAllBetCards();
            for (BetCard betcard : playerBetCards){ // durch alle seine Karten;
                for (int j = 0; j < sortedCamels.size(); j++){
                    if (sortedCamels.get(j).getColor().equals(betcard.getColor())){
                        players[i].addCoins(betcard.getCoinsForPlacement(j+1));
                        System.out.println(players[i] + " hat " + betcard.getCoinsForPlacement(j+1) + " Coins für die Wette auf das Kamel mit der Farbe " + betcard.getColor() + " erhalten.");
                    }
                }
            }
            List<PyramidCard> playerPyramidCards = players[i].getAllPyramidCards();
            for (PyramidCard pyramidcard : playerPyramidCards){
                players[i].addCoins(pyramidcard.getValue()); 
                System.out.println(players[i] + " hat " + pyramidcard.getValue() + " Coin erhalten, dafür dass er eine Pyramiden Karte gezogen hat.");
            }
            if (!players[i].getActionCardPlayed()){
                players[i].addCoins(1);
                System.out.println(players[i] + " hat 1 Coin erhalten, dafür dass er seine ActionCard nicht ausgespielt hat.");
            }
        }
        evaluateEndGameBets();
        printCoinsValuesOfPlayers();
    }

    public boolean isStageEnded(){
        if (pyramidCards.getSize() == 0){
            return true; 
        } else{
            return false;
        }
    }

    public void startGame(){
        Dice[] würfel = dicersSet.rollAllAvailableDices();

        for(int i = 0; i < CAMEL_COLORS.length; i++){
            if (würfel[i] != null && rennBahn != null) {
                rennBahn.moveCamel(würfel[i].getColor(), würfel[i].getValue());
            } else {
                System.out.println("Ein Würfel ist null oder die RennBahn wurde nicht initialisiert.");
            }
        }
        dicersSet.resetDiceSet();
    }

    private Object getUserInput(Scanner scanner, String prompt) {
        System.out.print(prompt);

        if (scanner.hasNextInt()) {
            int intValue = scanner.nextInt();
            scanner.nextLine(); // Konsumiere den Zeilenumbruch
            return intValue;
        } else {
            return scanner.nextLine();
        }
    }

    public void printCoinsValuesOfPlayers(){
        System.out.println("\n");
        for(int i = 0; i < players.length; i++){
            System.out.println(players[i] + " hat im Moment " + players[i].getCoins()+ " Coins.");
        }
    }

    public Player getWinner() {
        if (players.length == 0) {
            return null; // No players in the game
        }

        Player winner = players[0]; // Start with the first player as a potential winner

        for (int i = 1; i < players.length; i++) {
            if (players[i].getCoins() > winner.getCoins()) {
                winner = players[i]; // Update the winner if another player has more coins
            }
        }

        return winner; // Return the player with the most coins
    }

    @Override
    public void setGameEnded(boolean value){
        gameEnded = value;
    }
}