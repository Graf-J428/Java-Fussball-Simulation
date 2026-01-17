package Datenmodell;

public class Verein  {

    protected String _name;
    protected int _gemachteSpiele;
    protected int _punkte;
    protected int _eigeneTore;
    protected int _gegentore;
    protected int _tordifferenz;

    private Mannschaft _mann;


    public Verein(String name,int gemachteSpiele,int punkte, int eigeneTore,int gegenTore){

        //Kennt man die Übergabewerte des Konstruktors schon ausser den namen ?-> Eventuell überarbeiten

        _name=name;
        _gemachteSpiele=gemachteSpiele;
        _punkte=punkte;
        _eigeneTore=eigeneTore;
        _gegentore=gegenTore;
        _tordifferenz=_eigeneTore-_gegentore;
    }
    public Mannschaft getMannschaft(){
        return _mann;
    }
    public Mannschaft setMannschaft (){
       return new Mannschaft(_name);
    }
    public String getName(){
        return _name;
    }
    public int getGemachteSpiele(){
        return _gemachteSpiele;
    }
    public int getPunkte(){
        return _punkte;
    }
    public int getEigeneTore(){
        return _eigeneTore;
    }
    public int getGegenTore(){
        return _gegentore;
    }
    public int getTorDifferenz(){
        return _tordifferenz;
    }

    public void setGemachteSpiele(int gemachteSpiele){
        _gemachteSpiele = gemachteSpiele;
    }
    public void setEigeneTore(int eigeneTore){
        _eigeneTore=eigeneTore;
    }
    public void setGegentore(int gegentore){
        _gegentore=gegentore;
    }
    public void setPunkte(int punkte){_punkte=punkte;}
    public void setTordifferenz(int tordifferenz){_tordifferenz=tordifferenz;}
    public void updateTorDifferenz() {
        _tordifferenz = _eigeneTore - _gegentore;
    }


    /**
     * Vergleicht diesen Verein mit einem anderen Verein.
     * Die Priorität der Sortierung ist:
     * 1. Punkte (höher ist besser)
     * 2. Tordifferenz (höher ist besser)
     * 3. Geschossene Tore (höher ist besser)
     *
     * @param ver Der zu vergleichende Verein.
     * @return 1 wenn dieser Verein besser ist, -1 wenn schlechter, 0 bei absolutem Gleichstand.
     */
    int compareTo(Verein ver){
        if (this.getPunkte()<ver.getPunkte() ){
            return -1;
        }
        else if (this.getPunkte()> ver.getPunkte()){
            return 1;
        }
        else if (this.getPunkte()== ver.getPunkte()){
            if (this.getTorDifferenz()<ver.getTorDifferenz()){
                return -1;
            }
            else if (this.getTorDifferenz()>ver.getTorDifferenz()){
                return 1;
            }
            else if (this.getTorDifferenz()==ver.getTorDifferenz()){
                if (this.getEigeneTore()<ver.getEigeneTore()){
                    return -1;
                }
                else if (this.getEigeneTore()> ver.getEigeneTore()){
                    return 1;
                }
            }
        }
        return 0;
    }
    @Override
    public boolean equals(Object o){
        if (o==null){
            return false;
        }
        if(this.getClass()!= o.getClass()){
            return false;
        }
        if (this == o){
            return true;
        }
        Verein tmpVer = (Verein)o;
        return this._name.equals(tmpVer._name);
    }

    /**
     * Erzeugt einen String im CSV-Format zum Speichern in Dateien.
     * Format: Name;Spiele;EigeneTore;Gegentore;Differenz;Punkte
     * @return Der CSV-String.
     */
    public String toCSVString() {
        return _name + ";" + _gemachteSpiele + ";" + _eigeneTore + ";" + _gegentore+";"+_tordifferenz+";"+_punkte;
    }

    @Override
    public String toString() {
        return "Vereinsname: "+_name+ " Punkte: " +_punkte+ " Tordifferenz: "+_tordifferenz +"\n";
    }

}
