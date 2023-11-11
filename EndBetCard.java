public class EndBetCard {
    private Player player;
    private String camelColor;

    public EndBetCard(Player player, String camelColor) {
        this.player = player;
        this.camelColor = camelColor;
    }
    
    public String getColor(){
        return camelColor;
    }
    
    public Player getPlayer(){
        return player;
    }
    
    @Override
    public String toString(){
        return camelColor;
    }
}
