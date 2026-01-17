package Datenmodell;


public class Wuerfel {

  private  int _seiten;

  public  Wuerfel(int s){
      _seiten = s;
    }

   public int wuerfeln(){

      int randomNumber = (int) (Math.random()*_seiten)+1;

      return randomNumber;
    }

    public static void main (String [] args) {

      Wuerfel wSechseitig = new Wuerfel(6);
      Wuerfel wZwanzigseitig = new Wuerfel(20);

     System.out.println("Du hast eine "+wSechseitig.wuerfeln() +" gewuerfelt") ;
     System.out.println("Du hast eine "+wZwanzigseitig.wuerfeln()+" gewuerfelt") ;


    }
}