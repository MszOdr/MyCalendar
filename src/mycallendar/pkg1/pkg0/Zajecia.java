
package mycallendar.pkg1.pkg0;


public class Zajecia {
   public int id, dzien, miesiac, rok, godzina, minuta, czasTrwania;
   public String nazwa, miejsce, informacjeDodatkowe;
    
    Zajecia(int d, int m ,int y ,int godz, int min, String nazwa, String miejsce, String InfD)
    {
    this.dzien=d;
    this.miesiac=m;
    this.rok=y;
    this.godzina = godz;
    this.minuta = min;
    this.nazwa=nazwa;
    this.miejsce=miejsce;
    this.informacjeDodatkowe=InfD;    
    }
    Zajecia(){}
    
    
       
    
}
