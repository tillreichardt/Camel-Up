import greenfoot.*;  

public class Camel extends Actor
{
    private int Xposition;
    private int Yposition;
    private String color;
    private Camel WerSitztAufMir; 
    private Camel sitzendAuf; 
    private boolean sitztAufJemanden;
    private boolean JemandSitztAufMir;
    public Camel(int x, int y, String pColor){
        setLocation(x, y);
        Xposition = x;
        Yposition = y;
        color = pColor; 
        sitztAufJemanden = false; 
    }
    public void bewege(int schritte) {

        if (getJemandSitztAufMir()) {
            // Wenn das Kamel auf einem anderen Kamel sitzt, bewege beide gemeinsam
            this.Xposition += schritte;
            WerSitztAufMir.Xposition += schritte;
            // Aktualisiere die Position des Kamels auf dem Spielfeld
            int x = getX() + (schritte * 50);  // Annahme: 50 Pixel pro Schritt
            setCoordinates(x, getY());
            WerSitztAufMir.setCoordinates(x, WerSitztAufMir.getY());
        } else {
            // Andernfalls bewege nur dieses Kamel
            this.Xposition += schritte;
            // Aktualisiere die Position des Kamels auf dem Spielfeld
            int x = getX() + (schritte * 50);  // Annahme: 50 Pixel pro Schritt
            setCoordinates(x, getY());

        }
    }
    public void setCoordinates(int x, int y) {
        setLocation(x, y); // Diese Methode wird von Greenfoot bereitgestellt, um die Koordinaten zu setzen
    }     
    //z.b. greenCamel.WelchesCamelSitztAufMir(blueCamel) --> blue sitzt auf green
    public void WelchesCamelSitztAufMir(Camel camel) {
        WerSitztAufMir = camel;
        JemandSitztAufMir = true; 
    }

    public void setzeAuf(Camel camel) {
        sitzendAuf = camel;
    }

    @Override
    public String toString() {
        return color + "Camel";  // Dies gibt z.B. "greenCamel" für ein grünes Kamel aus
    }

    public int getXposition() {
        return this.Xposition;
    }

    public void setXposition(int Xposition) {
        this.Xposition = Xposition;
    }

    public int getYposition() {
        return this.Yposition;
    }

    public void setYposition(int Yposition) {
        this.Yposition = Yposition;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Camel getWerSitztAufMir() {
        return this.WerSitztAufMir;
    }

    public void setWerSitztAufMir(Camel WerSitztAufMir) {
        this.WerSitztAufMir = WerSitztAufMir;
    }

    public Camel getSitzendAuf() {
        return this.sitzendAuf;
    }

    public void setSitzendAuf(Camel sitzendAuf) {
        this.sitzendAuf = sitzendAuf;
    }

    public boolean getSitztAufJemanden() {
        return this.sitztAufJemanden;
    }

    public void setSitztAufJemanden(boolean sitztAufJemanden) {
        this.sitztAufJemanden = sitztAufJemanden;
    }

    public boolean isJemandSitztAufMir() {
        return this.JemandSitztAufMir;
    }

    public boolean getJemandSitztAufMir() {
        return this.JemandSitztAufMir;
    }

    public void setJemandSitztAufMir(boolean JemandSitztAufMir) {
        this.JemandSitztAufMir = JemandSitztAufMir;
    }
}