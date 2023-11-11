import greenfoot.*;
import java.util.*;
public class Game extends World {
    private static final int GRID_SIZE = 50;
    private String[] camelColors = {"white", "green", "blue", "yellow", "orange"};
    
    private boolean gameEnded;  
    private boolean etappeEnded; 

    private CamelTrack rennBahn = new CamelTrack();;

    private int amoutOfPyramidCards = 5;
    private Player[] players;
    private PyramidCards pyramidCards = new PyramidCards();
    private DiceSet dicersSet = new DiceSet(camelColors);
    private BetCards betCards = new BetCards();
    private List<Bet> endGameBets = new ArrayList<>();
    private Map<String, int[]> camelBetCounts = new HashMap<>();
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
    // 4 Möglichkeiten
    // case 1 - 4; 
    // aktive player --> Player player Parameter = aktiv player;
    // case 1: etappen Wettlätchen 
    // case 2: desertCard nach etappen ende wieder bei spieler
    // case 3: würfeln und usePCard nach etappen ende wieder voll 
    // case 4: Olle Tolle Camel, when gameEnded = true
    public void act(){
        Scanner scan = new Scanner(System.in);
        if (!isStageEnded()) {
            for (int i = 0; i < players.length; i++){

                Player activePlayer = players[i];
                int response = (int) getUserInput(scan, "\n" + activePlayer + " gib eine Option (1 - 4) ein: ");
                System.out.println(response);

                switch(response){
                    case 1:
                        String pColor = (String) getUserInput(scan, "Auf welches Kamel wettest du? Farbe: ");
                        activePlayer.addBetCard(betCards.drawBetCard(pColor));
                        System.out.println(activePlayer + " hat folgende BetCards: " + activePlayer.getAllBetCards());
                        break;

                    case 2:
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

                        break;

                    case 3:
                        activePlayer.addPyramidCard(pyramidCards.getPyramidCard());
                        
                        Dice dice = dicersSet.rollRandomDice(); // sonst wird die Methode rollRandomDice() zweimal ausgeführt
                        
                        rennBahn.moveCamel(dice.getColor(), dice.getValue());
                        
                        amoutOfPyramidCards--;
                        
                        System.out.println("Es gibt noch "+ amoutOfPyramidCards  + " Pyramiden Karten");
                        
                        Greenfoot.delay(1);
                        
                        break;
                        
                    case 4:
                        
                        String pppColor = (String) getUserInput(scan, "choose a camel color to bet on: ");

                        makeEndGameBet(activePlayer, pppColor);

                        break;

                    case 5: 
                        rennBahn.removeActionCardsOnTrack();
                        break; 
                    default:
                        System.out.println("Ungültige Auswahl. Bitte wähle 1 oder 5.");
                        break;
                }
                if (isStageEnded()) {
                    System.out.println("Die Etappe ist vorbei");
                    break; // Exit the for-loop immediately | --> return would exit the entire methode | although I know that Mr. Kreili does not like that lol 
                }
            } 
        }
    }
    
    public void makeEndGameBet(Player player, String camelColor) {
        int[] betCounts = camelBetCounts.getOrDefault(camelColor, new int[2]);

        // Determine the position for the bet
        String position = betCounts[0] <= betCounts[1] ? "first" : "last";
        betCounts[position.equals("first") ? 0 : 1]++;
        camelBetCounts.put(camelColor, betCounts);
                                
        // Check if player already placed a bet on this camel
        for (Bet bet : endGameBets) {
            if (bet.getPlayer().equals(player) && bet.getCamelColor().equals(camelColor)) {
                System.out.println("You have already placed a bet on " + camelColor);
                return;
            }
        }

        Bet newBet = new Bet(player, camelColor, position, endGameBets.size() + 1);
        endGameBets.add(newBet);
        System.out.println(player.getName() + " has placed a bet on " + camelColor + " for " + position + " place.");
    }
   
    public void evaluateEndGameBets() {
        List<Camel> sortedCamels = rennBahn.getCamelSorted();
        Camel firstPlaceCamel = sortedCamels.get(0);
        Camel lastPlaceCamel = sortedCamels.get(sortedCamels.size() - 1);
        int[] rewards = {8, 5, 3, 2, 1};
        int rewardIndex = 0;
        for (Bet bet : endGameBets) {
            boolean isCorrectBet = (bet.getPosition().equals("first") && bet.getCamelColor().equals(firstPlaceCamel.getColor())) ||
                (bet.getPosition().equals("last") && bet.getCamelColor().equals(lastPlaceCamel.getColor()));

            if (isCorrectBet) {
                int reward = rewardIndex < rewards.length ? rewards[rewardIndex++] : 1;
                bet.getPlayer().updateCoins(reward);
            } else {
                bet.getPlayer().updateCoins(-1);
            }
        }
    }
    
    public void resetAmoutOfPyramidCards(){
        amoutOfPyramidCards = 5;
    }

    public void moveOrange(){
        rennBahn.moveCamel("orange", 1);
    }

    public void moveBlue(){
        rennBahn.moveCamel("blue", 1);
    }

    public void moveYellow(){
        rennBahn.moveCamel("yellow", 1);
    }

    public void moveGreen(){
        rennBahn.moveCamel("green", 1);
    }

    public void moveWhite(){
        rennBahn.moveCamel("white", 1);
    }

    public boolean isStageEnded(){
        if (amoutOfPyramidCards == 0){
            return true; 
        } else{
            return false;
        }
    }

    public void startGame(){
        Dice[] würfel = dicersSet.rollAllAvailableDices();

        for(int i = 0; i < camelColors.length; i++){
            if (würfel[i] != null && rennBahn != null) {
                rennBahn.moveCamel(würfel[i].getColor(), würfel[i].getValue());
            } else {
                System.out.println("Ein Würfel ist null oder die RennBahn wurde nicht initialisiert.");
            }
        }
        dicersSet.reset();
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
}