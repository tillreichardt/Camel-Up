import java.util.*;


public class EndBetCards{
    // damit man auch mal die Liste vom "sChUlmInEsTeRiUm" benutzt aka Pointerliste 
    private LostList<EndBetCard> betsForFirstCamel;
    private LostList<EndBetCard> betsForLastCamel;
    
    public EndBetCards(){
        betsForFirstCamel = new LostList<>();
        betsForLastCamel = new LostList<>();
    }
    
    public void addBetForFirst(EndBetCard endbetcard){
        betsForFirstCamel.append(endbetcard);
    }
    
    public void addBetForLast(EndBetCard endbetcard){
        betsForLastCamel.append(endbetcard);
    }
    
    public void resetAllLists(){ // nur für full ended game wenn man eine "nochmal spielen Funktion einbauen würde, das macht aber den coolen ---- Das Spiel ist vorbei ---- Screen kaputt, weil dann wieder danach etwas kommt lol   
        betsForFirstCamel.toFirst();
        while(!betsForFirstCamel.isEmpty()){
            betsForFirstCamel.remove();
        }
        betsForLastCamel.toFirst();
        while(!betsForLastCamel.isEmpty()){
            betsForLastCamel.remove();
        }
    }
    
    public List<EndBetCard> getBetsForFirstCamel(){
        betsForFirstCamel.toFirst();
        List<EndBetCard> resultList = new ArrayList<>(); // because superior
        while(betsForFirstCamel.hasAccess()){
            resultList.add(betsForFirstCamel.getContent());   
            betsForFirstCamel.next();
        }
        return resultList;
    }
    
    public List<EndBetCard> getBetsForLastCamel(){
        betsForLastCamel.toFirst();
        List<EndBetCard> resultList = new ArrayList<>(); // because superior
        while(betsForLastCamel.hasAccess()){
            resultList.add(betsForLastCamel.getContent()); 
            betsForLastCamel.next();
        }
        return resultList;
    }
}