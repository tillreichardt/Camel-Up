import greenfoot.*;
import java.util.*;
// interface, damit im camelTrack in der moveCamel Methode das GameEnded Attribut auf false gesetzt werden kann. Ohne interface gibt es Fehler (non static reference from a static point oder so) 
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
    public static final String[] CAMEL_COLORS = {"white", "green", "blue", "yellow", "orange"}; // public und ohne getter, weil sowieso static final bzw. Konstante also kann man eh nicht ändern -> Datenkapselung. Soll globale Variable sein (ergibt auch Sinn, sonst immer als Parameter übergeben) 
    private static final String[] mustHaveNames = { "RegexRanger", "NullPointerNinja","ClassClown", "DebugDragon", "ExceptionExplorer", "BugHunter"};
    private boolean gameEnded; // automatisch false 

    private CamelTrack rennBahn;

    private int startPlayer = 0; // Spieler, welcher am Anfang der Etappe startet
    private Player[] players;
    private PyramidCards pyramidCards;
    private DiceSet dicersSet;
    private BetCards betCards;
    private EndBetCards endBetCards;
    private boolean stageEvaluatedPostGame; // automatisch false
    private Scanner scan = new Scanner(System.in);
    public Game(){ 
        super (18*GRID_SIZE, 5*GRID_SIZE, 1);
        rennBahn = new CamelTrack(this);
        pyramidCards = new PyramidCards();
        dicersSet = new DiceSet(CAMEL_COLORS);
        betCards = new BetCards();
        endBetCards = new EndBetCards();
        addObject(rennBahn, 450, 125);
        
        int numberOfPlayers = Integer.parseInt(getInputWithValidation(scan, "Mit wie vielen Spielern möchtest du spielen? (max. 8): ", "[1-8]"));
        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerName = getInputWithValidation(scan, "Wie soll der " + (i+1) + ". Spieler heißen?: ", "\\S+"); // + bedeutet mindestens ein Character lang 
            boolean nameAlreadyUsed = false; 
            for (Player player : players){
                 if (player != null && player.getName().equals(playerName)){
                     System.out.println("Der Name ist schon vergeben");
                     nameAlreadyUsed = true; 
                     break; 
                 }
            }
            if (nameAlreadyUsed){
                i--;
                continue;
            }
            players[i] = new Player(playerName);
            System.out.println(players[i] + " ist dem Spiel beigetreten\n");
        }
        System.out.println("\n\n");
        rennBahn.addCamelsToBoard();
        startGame();
        Greenfoot.start(); // fürhe die Run methode automatisch aus
    }
    
    public void act(){
        
        for (int i = 0; !gameEnded && !isStageEnded() && i < players.length; i++){ 
            int currentIndex = (startPlayer + i) % players.length; // Beispiel: Etappe vorbei. Spieler 3 letzen Zug, Spieler 4 als nächstes in neuer Etappe 
            Player activePlayer = players[currentIndex];
            int response = Integer.parseInt(getInputWithValidation(scan, "\n" + activePlayer + " gib eine Option (1 - 4) ein: ", "[1-4]"));
            
            // advanced switch case (nur ab Java 9) 
            switch(response){
                case BET_CARD -> 
                    {
                        // Eine BetCard einer beliebigen Farbe ziehen
                        
                        String pColor = getInputWithValidation(scan, "Welche Farbe wird das Kamel haben, das am Ende der Etappe erster Platz sein wird?: ", generateColorRegex(CAMEL_COLORS));
                        
                        BetCard tempCard = betCards.drawBetCard(pColor);
                        if (tempCard == null){
                            i--;
                            System.out.println("Operation war nicht erfolgreich. Bitte versuche es erneut!");
                            break;
                        }
                        activePlayer.addBetCard(tempCard);
                        System.out.println(activePlayer + " hat folgende BetCards: " + activePlayer.getAllBetCards());
                    }

                case ACTION_CARD -> 
                    {
                        // Entweder eine DesertCard oder OasisCard legen
                        
                        if (activePlayer.getActionCardPlayed()) {
                            System.out.println(activePlayer+" hat bereits eine ActionCard gespielt, wähle eine andere Option");
                            i--; // der gleiche Spieler ist dann nochmal dran, sonst gibt man nach break an den nächsten Spieler weiter;
                            break;
                        }
                        String actionCard = getInputWithValidation(scan, "Soll eine DesertCard (dc) oder OasisCard (oc) gespielt werden?: ", "(dc|oc)");       
                        int pPosition = Integer.parseInt(getInputWithValidation(scan, "\n" + activePlayer + " auf welche Position (2-16) soll diese ActionCard?: ", "(1[0-6]|[2-9])"));                        
                        boolean successful = false; 
                        
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

                case PYRAMID_CARD -> 
                    {
                        // Pyramiden Karte ziehen und würfeln -> Kamele bewegen
                        
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
                            // getWinner() Liste in einen String klatschen
                            System.out.println("\n\n"+ String.join(", ", getWinner().stream().map(Object::toString).toArray(String[]::new)) + s + " das Spiel gewonnen!");
                            System.out.println("\n\n\n---------------------  Das Spiel ist vorbei ---------------------\n\n\n");
                        }   
                    }
                case END_GAME_BET -> 
                    { 
                        // Auf's Olle oder Tolle Kamel wetten 
                        
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
    
    // Für den Userinput, um zu gucken, welche Farben eingegeben werden dürfen
    private String generateColorRegex(String[] pColors){
        String joinedColors = String.join("|",pColors);
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

    public void makeEndGameBet(Player pPlayer, String pCamelColor, String pPosition) {
        if (pPosition.equals("first")){
            endBetCards.addBetForFirst(pPlayer.getEndBetsCard(pCamelColor));
            System.out.println(pPlayer.getName() + " hat eine Wette auf das Kamel mit der Farbe " + pCamelColor + " für den " + pPosition + " place gesetzt.");
        } else if (pPosition.equals("last")) {
            endBetCards.addBetForLast(pPlayer.getEndBetsCard(pCamelColor));
            System.out.println(pPlayer.getName() + " hat eine Wette auf das Kamel mit der Farbe " + pCamelColor + " für den " + pPosition + " place gesetzt.");
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

        HashMap<Integer, Integer> rewardMap = new HashMap<>(); // Coins für die Platzierungen
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
            } else { // Wette war falsch --> -1 Coin 
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
            } else { // Wette war falsch --> -1 Coin 
                bet.getPlayer().addCoins(-1);
                System.out.println(bet.getPlayer() + " hat 1 Coin verloren, dafür dass er eine Wette auf das falsche, letzte Kamel gesetzt hat.");
            }
        }
    }

    public void stageEvaluation(){
        System.out.println("\n\n\n---------------------  Die Etappe ist vorbei ---------------------\n\n\n");
        System.out.println("Etappenwertung:");
        List<Camel> sortedCamels = rennBahn.getCamelSorted();
        Camel firstPlaceCamel = sortedCamels.get(0);
        Camel lastPlaceCamel = sortedCamels.get(sortedCamels.size() - 1);
        
        // durch alle Spieler iterieren
        for (int i = 0; i < players.length; i++){ 
            List<BetCard> playerBetCards = players[i].getAllBetCards();
             // durch alle seine Wettkarten iterieren 
            for (BetCard betcard : playerBetCards){
                // Platzierungen der Kamele auswerten
                for (int j = 0; j < sortedCamels.size(); j++){
                    if (sortedCamels.get(j).getColor().equals(betcard.getColor())){
                        players[i].addCoins(betcard.getCoinsForPlacement(j+1));
                        System.out.println(players[i] + " hat " + betcard.getCoinsForPlacement(j+1) + " Coins für die Wette auf das Kamel mit der Farbe " + betcard.getColor() + " erhalten.");
                    }
                }
            }
            List<PyramidCard> playerPyramidCards = players[i].getAllPyramidCards();
            
            // durch alle seine Pyramidenkarten iterieren
            for (PyramidCard pyramidcard : playerPyramidCards){
                players[i].addCoins(pyramidcard.getValue()); 
                System.out.println(players[i] + " hat " + pyramidcard.getValue() + " Coin erhalten, dafür dass er eine Pyramiden Karte gezogen hat.");
            }
            
            // Wenn der Spieler seine ActionCard nicht verwendet hat, erhält er einen Coin 
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
        
        // alle Würfel rollen lassen und den Kamelen somit eine Startposition geben 
        for(int i = 0; i < CAMEL_COLORS.length; i++){
            if (würfel[i] != null && rennBahn != null) {
                rennBahn.moveCamel(würfel[i].getColor(), würfel[i].getValue());
            } else {
                System.out.println("Ein Würfel ist null oder die RennBahn wurde nicht initialisiert.");
            }
        }
        dicersSet.resetDiceSet();
    }

    private String getInputWithValidation(Scanner scanner, String question, String regex) {
        
        // Userinput erhalten, solange bis er etwas richtiges eingibt, das dem regex entspricht
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
        // Prüfen, welche Spieler die meisten Coins haben, wenn es mehrere mit gleich vielen Coins gibt, gibt es mehrere Gewinner
        for (Player player : sortedPlayers) {
            if (player.getCoins() == winnerCoins) winner.add(player);
        }

        return winner; // Return players with the most coins
    }

    @Override
    public void setGameEnded(boolean value){
        gameEnded = value;
    }
}