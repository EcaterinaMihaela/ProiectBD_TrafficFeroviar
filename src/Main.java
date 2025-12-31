import dao.Database;
import model.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        Database db = new Database();
        Connection con = null;
        List<Tren> listaTrenuri = new ArrayList<>();
        List<Operator> listaOperatori = new ArrayList<>();
        List<Ruta> listaRute = new ArrayList<>();

        try {
            con = db.connect();

            listaTrenuri = db.citesteTrenuri(con);
            listaOperatori = db.citesteOperatori(con);
            listaRute = db.citesteRute(con);

            for(Tren t : listaTrenuri){
                db.afiseazaTrenInfo(t);
            }

            // Deschidere GUI dacă ai implementat TrenViewer
            // TrenViewer trenViewer = new TrenViewer(listaTrenuri, listaOperatori, listaRute);
            // trenViewer.setVisible(true);

        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
