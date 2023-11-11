public class EndBet {
    private Player player;
    private String camelColor;
    private String position;
    private int order;

    public EndBet(Player player, String camelColor, String position, int order) {
        this.player = player;
        this.camelColor = camelColor;
        this.position = position;
        this.order = order;
    }
    
    public String getPosition(){
        return position; 
    }
    
    public String getCamelColor(){
        return camelColor;
    }
    
    public Player getPlayer(){
        return player;
    }
}
