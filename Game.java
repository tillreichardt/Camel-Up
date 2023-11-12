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
    public Game(){ 
        super (18*GRID_SIZE, 18*GRID_SIZE, 1);
        addObject(rennBahn, 450, 775);
        setup();
    }

    private void setup(){
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
        Scanner scan = new Scanner(System.in);
        for (int i = 0; !gameEnded && !isStageEnded() && i < players.length; i++){ 
            int currentIndex = (startPlayer + i) % players.length; // Beispiel: Etappe vorbei. Spieler 3 letzen Zug, Spieler 4 als nächstes in neuer Etappe 
            Player activePlayer = players[currentIndex];
            int response = Integer.parseInt(getInputWithValidation(scan, "\n" + activePlayer + " gib eine Option (1 - 4) ein: ", "[1-4]"));
            
            // advanced switch case (nur ab Java 9) 
            switch(response){
                case BET_CARD -> {
                        String pColor = getInputWithValidation(scan, "Welche Farbe wird das Kamel haben, das am Ende der Etappe erster Platz sein wird?: ", generateColorRegex(CAMEL_COLORS));
                        activePlayer.addBetCard(betCards.drawBetCard(pColor));
                        System.out.println(activePlayer + " hat folgende BetCards: " + activePlayer.getAllBetCards());

                    }

                case ACTION_CARD -> {
                        if (activePlayer.getActionCardPlayed()) {
                            System.out.println(activePlayer+" hat bereits eine ActionCard gespielt, wähle eine andere Option");
                            i--; // der gleiche Spieler ist dann nochmal dran, sonst gibt man nach break an den nächsten Spieler weiter;
                            break;
                        }
                        String actionCard = getInputWithValidation(scan, "Soll eine DesertCard (dc) oder OasisCard (oc) gespielt werden?: ", "(dc|oc)");       
                        int pPosition = Integer.parseInt(getInputWithValidation(scan, "\n" + activePlayer + " auf welche Position (2-16) soll diese ActionCard?: ", "(1[0-6]|[2-9])"));
                        // Prüfe, ob der Spieler bereits eine ActionCard gespielt hat
                        
                        boolean successful = false; 
                        // Führe die Aktion für die entsprechende Karte aus
                        if (actionCard.equals("dc")) {
                            successful = rennBahn.addActionCard(activePlayer.getDesertCard(), pPosition, activePlayer);
                        } else { // actionCard.equals("oc")
                            successful = rennBahn.addActionCard(activePlayer.getOasisCard(), pPosition, activePlayer);
                        }
                        
                        if(!successful){
                            i--;
                            System.out.println("Operation war nicht erfolgreich. Bitte versuche es erneut!");
                            break; // Fehlermeldung in addActionCard() Methode;  
                        }
                        Greenfoot.delay(1); // Delay, damit board geupdated wird 

                    }

                case PYRAMID_CARD -> {
                        activePlayer.addPyramidCard(pyramidCards.getPyramidCard());
                        Dice dice = dicersSet.rollRandomDice(); // sonst wird die Methode rollRandomDice() zweimal ausgeführt
                        System.out.println("Es gibt noch "+ pyramidCards.getSize()  + " Pyramiden Karten");
                        rennBahn.moveCamel(dice.getColor(), dice.getValue());
                        Greenfoot.delay(1); // Delay, damit board geupdated wird 

                        if (isStageEnded() && !gameEnded) {
                            startPlayer = currentIndex + 1; // Token den Spieler links von uns weiter geben (+1) 
                            stageEvaluation();
                            resetStage();
                        }
                        if (gameEnded) {
                            stageEvaluation();
                            String s = getWinner().size() > 1 ? " haben " : " hat";
                            System.out.println("\n\n"+ String.join(", ", getWinner().stream().map(Object::toString).toArray(String[]::new)) + s + " das Spiel gewonnen!");
                            System.out.println("\n\n\n---------------------  Das Spiel ist vorbei ---------------------");
                        }   
                    }
                case END_GAME_BET -> { 
                        // Überprüfung, ob Spieler überhaupt legen kann, ob erst mit makeEndGameBet, muss früher kommen 
                        System.out.println();
                        List<EndBetCard> availableEndBetCards = activePlayer.getAvailableEndBetCards();
                        String[] availableColors = new String[availableEndBetCards.size()];
                        for (int j = 0; j < availableColors.length; j++){
                            availableColors[j] = availableEndBetCards.get(j).getColor();
                        }
                        String pColor = getInputWithValidation(scan, "Die Farben "+ activePlayer.getAvailableEndBetCards()+ " stehen "+activePlayer+" noch zur Verfügung. \nWelche dieser Farben soll das Kamel haben, auf das du wettest?: ", generateColorRegex(availableColors)); 
                        String pPosition = getInputWithValidation(scan, "Welche Position denkst du hat das Kamel am Ende des Spiels? (first/last): ", "(first|last)");
                        makeEndGameBet(activePlayer, pColor, pPosition);

                    }

                case 5 ->  betCards.printBetCardsInBetCardsClass();

                default -> System.out.println("\nUngültige Auswahl. Bitte wähle 1 oder 5.");
            } 
        }
    }
    
    private String generateColorRegex(String[] colors){
        String joinedColors = String.join("|",colors);
        return "\\b(" + joinedColors + ")\\b"; 
    }
    
    private void resetStage(){
        betCards.resetBetCardList();
        pyramidCards.resetPyramidCards();
        dicersSet.resetDiceSet();
        for(Player player : players){
            player.clearBetCards();
            player.clearPyramidCards();
            player.setActionCardPlayed(false);
        }
        if(rennBahn.getSizeActionCardsOnTrack() == 0) return;
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
        System.out.println("\nEndGameBetCards Auswertung:");
        if (endBetCards.getBetsForFirstCamel().size() == 0 && endBetCards.getBetsForLastCamel().size() == 0) System.out.println("Entfällt, da keine Wetten platziert wurden.");
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
        System.out.println("\n\n\n---------------------  Die Etappe ist vorbei ---------------------\n\n");
        System.out.println("\nEtappenwertung:");
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

    private void startGame(){
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

    private String getInputWithValidation(Scanner scanner, String question, String regex) {
        String input;
        do {
            System.out.print(question);
            input = scanner.nextLine().trim();
            if (!input.matches(regex)) {
                System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
            }
        } while (!input.matches(regex));

        return input;
    }
    
    public void printCoinsValuesOfPlayers(){
        System.out.println("\nPunktestand der Spieler: ");
        List<Player> sortedPlayers = getSortedPlayersByCoins();
        for(Player player : sortedPlayers){
            System.out.println(player + " hat im Moment " + player.getCoins()+ " Coins.");
        }
    }
    
    public List<Player> getSortedPlayersByCoins(){
        List<Player> sortedPlayers = new ArrayList<>();
        for (int i = 0; i < players.length; i++){
            sortedPlayers.add(players[i]);
        }
        Comparator<Player> playerComparator = (p1, p2) -> p2.getCoins() - p1.getCoins();

        Collections.sort(sortedPlayers, playerComparator);
        return sortedPlayers;
    }
    
    public List<Player> getWinner() {
        List<Player> winner = new ArrayList<>();
        List<Player> sortedPlayers = getSortedPlayersByCoins();
        int winnerCoins = sortedPlayers.get(0).getCoins();
        for (Player player : sortedPlayers) {
            if (player.getCoins() == winnerCoins) winner.add(player);
        }

        return winner; // Return the player with the most coins
    }

    @Override
    public void setGameEnded(boolean value){
        gameEnded = value;
    }
}