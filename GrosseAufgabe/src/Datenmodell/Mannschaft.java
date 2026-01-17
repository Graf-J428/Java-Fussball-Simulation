package Datenmodell;

public class Mannschaft {

    private String _name;
    private int _sturm;
    private int _mi;
    private int _vert;
    private int _torw;

    //Stärkewerte :
    Wuerfel w6= new Wuerfel(6);
    //Erfolg für angriff, verteidigung und Tor
    Wuerfel w20 = new Wuerfel(20);

    public Mannschaft(String n){


        _name =n;
        _sturm= +2*w6.wuerfeln();
        _mi=4+2*w6.wuerfeln();
        _vert=30+2*w6.wuerfeln();
        _torw=10+2*w6.wuerfeln();
    }

    public String get_name() {
        return _name;
    }

    /**
     * Berechnet, ob ein Angriff erfolgreich initiiert wird.
     * Ein Angriff ist erfolgreich, wenn ein W20-Wurf sowohl kleiner/gleich dem Mittelfeld-Wert
     * als auch kleiner/gleich dem Sturm-Wert ist.
     * @return true bei erfolgreichem Angriffsaufbau, sonst false.
     */
    public boolean angriff(){

        if (w20.wuerfeln() <= _mi && w20.wuerfeln() <= _sturm) {
            //System.out.println(_name+" durchbricht die gegnerische Fornt und startet einen erfolgreichen Angriff!!!");
            return true;
        }
        else //System.out.println(_name+" konnte trotz ihrer Bemühung keinen erfolgreichen Angriff starten.");
        return false;
    }

    /**
     * Berechnet, ob die Mannschaft einen gegnerischen Angriff abwehren kann.
     * Verteidigung ist erfolgreich, wenn ein W20-Wurf kleiner/gleich Mittelfeld
     * UND kleiner/gleich Verteidigungswert ist.
     * @return true, wenn erfolgreich verteidigt wurde.
     */
    public boolean verteidigt(){

        if(w20.wuerfeln() <= _mi && w20.wuerfeln() <= _vert) {
            //System.out.println(_name+" hat den Angriff erfolgreich verteidigen können!!!");
            return true;
        }
        else // System.out.println(_name+" musste zusehen wie Ihre Verteidigung durchbrochen wurde.");
        return false;


    }

    public int schiesseTor(){

        int schussStaerke= w6.wuerfeln();
        return schussStaerke;
    }

    public boolean kassiereTor(int tsStaerke){

        if(tsStaerke>=5){
            //System.out.println(_name+" KASSIERT EIN TOR");
            return true;
        }
        //System.out.println("Der Torschuss konnte pariert werden");
        return w20.wuerfeln() <=_torw;

    }
    @Override
    public String toString() {
        return _name; // Gibt den Namen der Datenmodell.Mannschaft zurück
    }

   /* @Override
    public int compareTo(Object obj){
        Datenmodell.Mannschaft m = (Datenmodell.Mannschaft) obj;
        if(obj ==null){
            return 1;
        }
        else {
            return this.get_name().compareTo(m.get_name());
        }
    }


    public static void main(String[] args) {

    Datenmodell.Mannschaft eintrachtF = new Datenmodell.Mannschaft("Eintracht Frankfurt");

    eintrachtF.angriff();
    eintrachtF.verteidigt();
    eintrachtF.kassiereTor(eintrachtF.schiesseTor());


    }*/
}




