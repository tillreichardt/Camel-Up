import greenfoot.*;  

public class Camel extends Actor
{
    private int location;
    private String color;
    public Camel(int pLocation, String pColor){
        location = pLocation;
        color = pColor; 
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int newLocation) {
        this.location = newLocation;
    }

    @Override
    public String toString() {
        return color + "Camel";  // Dies gibt z.B. "greenCamel" für ein grünes Kamel aus
    }

}