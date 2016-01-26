
package mycallendar.pkg1.pkg0;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import static mycallendar.pkg1.pkg0.Logowanie.conn;


public class Kalendarz extends JFrame{
    JTable table;
    public Zajecia  zajecie[];
    public int id;
    
    
    private void TableMouseClicked(MouseEvent evt) throws SQLException {
    System.out.println(table.getSelectedRow());
   
    int result = JOptionPane.showConfirmDialog(null, 
   "Czy napewno chcesz usunąć zajęcie?",null, JOptionPane.YES_NO_OPTION);
if(result == JOptionPane.YES_OPTION) {
       int Wiersz =table.getSelectedRow();
       Object id =table.getValueAt(Wiersz, 0);
       String usunRelacje="DELETE FROM `uzytkownicy_zajecia` WHERE `id_zajecia` = "+id+"";
       String usun="DELETE FROM `zajecia` WHERE `id_zajecia` = "+id+"";
       System.out.println(usunRelacje);
       System.out.println(usun);
       Statement stmt = conn.createStatement();
        stmt.executeUpdate(usunRelacje);
        stmt.executeUpdate(usun);
       
        }
    }
    public Kalendarz(int id) throws SQLException{
        
        
        
         String wyswietl = "SELECT zajecia.id_zajecia, zajecia.nazwa, zajecia.godzina, zajecia.data, zajecia.miejsce, zajecia.czasTrwania, zajecia.informacjeDodatkowe"
                    + " FROM zajecia, uzytkownicy_zajecia, uzytkownicy "
                    + "WHERE zajecia.id_zajecia = uzytkownicy_zajecia.id_zajecia "
                    + "AND uzytkownicy_zajecia.id_uzytkownika = uzytkownicy.id_uzytkownika "
                    + "AND uzytkownicy.id_uzytkownika = "+id+" ORDER BY `zajecia`.`data`, `zajecia`.`godzina` DESC";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(wyswietl);
            int rowcount = 0;
            
            while (rs.next()) {
                ++rowcount;
                // Get data from the current row and use it
                
            }
            
            if (rowcount == 0) {
                System.out.println("No records found");
            }
            rs.beforeFirst();
            
            zajecie = new Zajecia[rowcount];
            int i=0;
            while(rs.next()){
               
                zajecie[i] = new Zajecia();
                zajecie[i].id = rs.getInt("id_zajecia");
                zajecie[i].nazwa  = rs.getString("nazwa");
                
                Timestamp timestamp = rs.getTimestamp("data");
                zajecie[i].dzien=timestamp.getDate();
                zajecie[i].miesiac=timestamp.getMonth()+1;
                zajecie[i].rok = timestamp.getYear()+1900;
                
                timestamp = rs.getTimestamp("godzina");
                zajecie[i].godzina  = timestamp.getHours();
                zajecie[i].minuta = timestamp.getMinutes();
                zajecie[i].miejsce = rs.getString("miejsce");
                zajecie[i].czasTrwania =rs.getInt("czasTrwania");
                zajecie[i].informacjeDodatkowe = rs.getString("informacjeDodatkowe");
                i++;
            }
           
           
            
            Object[] ColumnNames = {"ID","Nazwa", "Godzina", "Data", "Miejsce", "Czas trwania","Informacje dodatkowe"};
            Object[][] data = new Object[rowcount][7];
            
            for(int j=0; j<rowcount; j++)
            {
                data[j][0]=zajecie[j].id;
                data[j][1]=zajecie[j].nazwa;
                data[j][2]= Integer.toString(zajecie[j].godzina)+":"+Integer.toString(zajecie[j].minuta);
                data[j][3]= Integer.toString(zajecie[j].dzien)+"/"+Integer.toString(zajecie[j].miesiac)+"/"+Integer.toString(zajecie[j].rok);
                data[j][4]= zajecie[j].miejsce;
                data[j][5]= Integer.toString(zajecie[j].czasTrwania);
                data[j][6]= zajecie[j].informacjeDodatkowe;
            }
     
    
    table = new JTable(data,ColumnNames);
    table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    TableMouseClicked(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(Kalendarz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    table.setPreferredScrollableViewportSize(new Dimension(500,50));
    table.setFillsViewportHeight(true);
    
        JScrollPane scroll = new JScrollPane(table);
        add(scroll);

}
}
