package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Database
{
   public Connection connect() throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        try {
            connection = DriverManager. getConnection(MyDBInfo.PostgreSQL_DATABASE_SERVER,
                    MyDBInfo.PostgreSQL_USERNAME, MyDBInfo.PostgreSQL_PASSWORD);
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

    public List<Tren> citesteTrenuri(Connection con) throws SQLException {
        String sql = "SELECT * FROM Tren";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Tren> lista = new ArrayList<>();
        while(rs.next()) {
            Tren t = new Tren();
            t.setIdTren(rs.getInt("idTren"));
            t.setTip(rs.getString("tip"));
            t.setNumar(rs.getInt("numar"));
            t.setClase(rs.getString("clase"));
            t.setNrVagoane(rs.getInt("nrVagoane"));

            // Setare Operator
            Operator op = new Operator();
            op.setNumeOperator(rs.getString("NumeOperator"));
            t.setOperator(op);

            // Setare Ruta
            Ruta r = new Ruta();
            r.setNumeRuta(rs.getString("NumeRuta"));
            t.setRuta(r);

            lista.add(t);
        }
        ps.close();
        return lista;
    }



    public boolean existaTren(Connection con, int idTren) throws SQLException {
        String sql = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;
        try {
            sql = "SELECT * FROM Tren WHERE idTren = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, idTren);
            resultSet = preparedStatement.executeQuery();
            exist = resultSet.next(); // returnează true dacă există cel puțin un rezultat
            System.out.println(exist);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if(preparedStatement != null) preparedStatement.close();
            if(resultSet != null) resultSet.close();
        }
        return exist;
    }



    // Cautare tren după idTren
    public Tren cautaTren(Connection con, int idTren) throws SQLException {
        String sql = "SELECT * FROM Tren WHERE idTren=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idTren);
        ResultSet rs = ps.executeQuery();
        Tren t = null;
        if(rs.next()) {
            t = new Tren();
            t.setIdTren(rs.getInt("idTren"));
            t.setTip(rs.getString("tip"));
            t.setNumar(rs.getInt("numar"));
            t.setClase(rs.getString("clase"));
            t.setNrVagoane(rs.getInt("nrVagoane"));

            Operator op = new Operator();
            op.setNumeOperator(rs.getString("NumeOperator"));
            t.setOperator(op);

            Ruta r = new Ruta();
            r.setNumeRuta(rs.getString("NumeRuta"));
            t.setRuta(r);
        }
        ps.close();
        return t;
    }

    // Inserare tren
    public void insereazaTren(Connection con, Tren tren) throws SQLException {
        String sql = "INSERT INTO Tren(idTren, tip, numar, clase, nrVagoane, NumeOperator, NumeRuta) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, tren.getIdTren());
        ps.setString(2, tren.getTip());
        ps.setInt(3, tren.getNumar());
        ps.setString(4, tren.getClase());
        ps.setInt(5, tren.getNrVagoane());

        if(tren.getOperator() != null)
            ps.setString(6, tren.getOperator().getNumeOperator());
        else
            ps.setNull(6, Types.VARCHAR);

        if(tren.getRuta() != null)
            ps.setString(7, tren.getRuta().getNumeRuta());
        else
            ps.setNull(7, Types.VARCHAR);

        ps.executeUpdate();
        ps.close();
    }


    public void afiseazaTrenInfo(Tren tren) {
        System.out.println("ID Tren: " + tren.getIdTren());
        System.out.println("Tip: " + tren.getTip());
        System.out.println("Numar: " + tren.getNumar());
        System.out.println("Clase: " + tren.getClase());
        System.out.println("Numar Vagoane: " + tren.getNrVagoane());

        if(tren.getOperator() != null) {
            System.out.println("Operator: " + tren.getOperator().getNumeOperator());
        }

        if(tren.getRuta() != null) {
            System.out.println("Ruta: " + tren.getRuta().getNumeRuta());
        }
    }

    // Citire operatori din baza de date
    public List<Operator> citesteOperatori(Connection con) throws SQLException {
        String sql = "SELECT * FROM Operator";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Operator> lista = new ArrayList<>();
        while(rs.next()) {
            Operator op = new Operator();
            op.setNumeOperator(rs.getString("NumeOperator"));
            // poți adăuga aici alte câmpuri dacă Operator are mai multe
            lista.add(op);
        }
        rs.close();
        ps.close();
        return lista;
    }

    // Citire rute din baza de date
    public List<Ruta> citesteRute(Connection con) throws SQLException {
        String sql = "SELECT * FROM Ruta";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Ruta> lista = new ArrayList<>();
        while(rs.next()) {
            Ruta r = new Ruta();
            r.setNumeRuta(rs.getString("NumeRuta"));
            // poți adăuga aici alte câmpuri dacă Ruta are mai multe
            lista.add(r);
        }
        rs.close();
        ps.close();
        return lista;
    }

    public void stergeTren(Connection con, int idTren) throws SQLException {
        String sql = "DELETE FROM Tren WHERE idTren = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTren);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Bilet> citesteBilete(Connection con) throws SQLException {
        String sql = "SELECT * FROM Bilet";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Bilet> lista = new ArrayList<>();
        while(rs.next()) {
            Bilet b = new Bilet();
            b.setIdBilet(rs.getInt("idBilet"));
            b.setDetalii(rs.getString("detalii"));
            b.setPret(rs.getDouble("pret"));
            b.setTipReducere(rs.getString("tipReducere"));
            b.setIdTren(rs.getInt("idTren"));
            b.setStatus(rs.getString("status"));
            b.setDataCumpararii(rs.getDate("dataCumpararii"));
            b.setLocVagon(rs.getInt("locVagon"));
            b.setNrLegitimatie(rs.getInt("nrLegitimatie"));
            b.setNumeGara(rs.getString("NumeGara"));
            b.setNrVagon(rs.getInt("NrVagon"));
            lista.add(b);
        }
        ps.close();
        return lista;
    }

    public void insereazaBilet(Connection con, Bilet bilet) throws SQLException {
        String sql = "INSERT INTO Bilet(idBilet, detalii, pret, tipReducere, idTren, status, dataCumpararii, locVagon, nrLegitimatie, NumeGara, NrVagon) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bilet.getIdBilet());
        ps.setString(2, bilet.getDetalii());
        ps.setDouble(3, bilet.getPret());
        ps.setString(4, bilet.getTipReducere());
        ps.setInt(5, bilet.getIdTren());
        ps.setString(6, bilet.getStatus());
        ps.setDate(7, new java.sql.Date(bilet.getDataCumpararii().getTime()));
        ps.setInt(8, bilet.getLocVagon());
        ps.setInt(9, bilet.getNrLegitimatie());
        ps.setString(10, bilet.getNumeGara());
        ps.setInt(11, bilet.getNrVagon());
        ps.executeUpdate();
        ps.close();
    }

    public void stergeBilet(Connection con, int idBilet) throws SQLException {
        String sql = "DELETE FROM Bilet WHERE idBilet=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idBilet);
        ps.executeUpdate();
        ps.close();
    }

    public Bilet cautaBilet(Connection con, int idBilet) throws SQLException {
        String sql = "SELECT * FROM Bilet WHERE idBilet=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idBilet);
        ResultSet rs = ps.executeQuery();
        Bilet b = null;
        if(rs.next()) {
            b = new Bilet();
            b.setIdBilet(rs.getInt("idBilet"));
            b.setDetalii(rs.getString("detalii"));
            b.setPret(rs.getDouble("pret"));
            b.setTipReducere(rs.getString("tipReducere"));
            b.setIdTren(rs.getInt("idTren"));
            b.setStatus(rs.getString("status"));
            b.setDataCumpararii(rs.getDate("dataCumpararii"));
            b.setLocVagon(rs.getInt("locVagon"));
            b.setNrLegitimatie(rs.getInt("nrLegitimatie"));
            b.setNumeGara(rs.getString("NumeGara"));
            b.setNrVagon(rs.getInt("NrVagon"));
        }
        ps.close();
        return b;
    }


    public List<Tren> cautaTrenDupaRuta(Connection con, String numeRuta) throws SQLException {
        List<Tren> lista = new ArrayList<>();

        String sql = """
        SELECT t.idTren, t.tip, t.numar, t.clase, t.nrVagoane,
               o.NumeOperator, r.NumeRuta
        FROM Tren t
        JOIN Operator o ON t.NumeOperator = o.NumeOperator
        JOIN Ruta r ON t.NumeRuta = r.NumeRuta
        WHERE r.NumeRuta = ?
    """;

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, numeRuta);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Operator op = new Operator(rs.getString("NumeOperator"));
            Ruta ruta = new Ruta(rs.getString("NumeRuta"));

            Tren t = new Tren(
                    rs.getInt("idTren"),
                    rs.getString("tip"),
                    rs.getInt("numar"),
                    rs.getString("clase"),
                    rs.getInt("nrVagoane"),
                    op,
                    ruta
            );
            lista.add(t);
        }

        return lista;
    }



    public void updateTren(Connection con, Tren tren) throws SQLException {
        String sql = "UPDATE Tren SET tip=?, numar=?, clase=?, nrVagoane=?, NumeOperator=?, NumeRuta=? WHERE idTren=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, tren.getTip());
        ps.setInt(2, tren.getNumar());
        ps.setString(3, tren.getClase());
        ps.setInt(4, tren.getNrVagoane());

        if (tren.getOperator() != null && tren.getOperator().getNumeOperator() != null)
            ps.setString(5, tren.getOperator().getNumeOperator());
        else
            ps.setNull(5, Types.VARCHAR);

        if (tren.getRuta() != null && tren.getRuta().getNumeRuta() != null)
            ps.setString(6, tren.getRuta().getNumeRuta());
        else
            ps.setNull(6, Types.VARCHAR);

        ps.setInt(7, tren.getIdTren());

        ps.executeUpdate();
        ps.close();
    }

    public void updateBilet(Connection con, Bilet b) throws SQLException {

        String sql = """
        UPDATE Bilet
        SET detalii = ?,
            pret = ?,
            tipReducere = ?,
            locVagon = ?,
            nrLegitimatie = ?,
            numeGara = ?,
            nrVagon = ?
        WHERE idBilet = ?
    """;

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, b.getDetalii());
        ps.setDouble(2, b.getPret());
        ps.setString(3, b.getTipReducere());
        ps.setInt(4, b.getLocVagon());
        ps.setInt(5, b.getNrLegitimatie());
        ps.setString(6, b.getNumeGara());
        ps.setInt(7, b.getNrVagon());
        ps.setInt(8, b.getIdBilet());

        int rows = ps.executeUpdate();
        System.out.println("Bilete modificate: " + rows);

        ps.close();
    }


    public List<Bilet> citesteBileteDupaTren(Connection con, int idTren) throws SQLException {
        List<Bilet> bilete = new ArrayList<>();
        String sql = "SELECT idbilet, detalii, pret, tipreducere, idtren, status, datacumpararii, locvagon, nrlegitimatie, numegara, nrvagon " +
                "FROM bilet WHERE idtren = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, idTren);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Bilet b = new Bilet(
                    rs.getInt("idbilet"),
                    rs.getString("detalii"),
                    rs.getDouble("pret"),
                    rs.getString("tipreducere"),
                    rs.getInt("idtren"),
                    rs.getString("status"),
                    rs.getDate("datacumpararii"),
                    rs.getInt("locvagon"),
                    rs.getInt("nrlegitimatie"),
                    rs.getString("numegara"),
                    rs.getInt("nrvagon")
            );
            bilete.add(b);
        }

        rs.close();
        pst.close();
        return bilete;
    }


    //pentru calatori
    // Citire toti calatorii
    public List<Calator> citesteCalatori(Connection con) throws SQLException {
        List<Calator> lista = new ArrayList<>();
        String sql = "SELECT * FROM Calator";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Calator c = new Calator();
            c.setNrLegitimatie(rs.getInt("nrLegitimatie"));
            c.setNume(rs.getString("nume"));
            c.setPrenume(rs.getString("prenume"));
            c.setTip(rs.getString("tip"));
            c.setContact(rs.getString("contact"));
            lista.add(c);
        }
        rs.close();
        ps.close();
        return lista;
    }

    // Inserare calator
    public void insereazaCalator(Connection con, Calator c) throws SQLException {
        String sql = "INSERT INTO Calator(nrLegitimatie, nume, prenume, tip, contact) VALUES(?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, c.getNrLegitimatie());
        ps.setString(2, c.getNume());
        ps.setString(3, c.getPrenume());
        ps.setString(4, c.getTip());
        ps.setString(5, c.getContact());
        ps.executeUpdate();
        ps.close();
    }

    // Stergere calator
    public void stergeCalator(Connection con, int nrLegitimatie) throws SQLException {
        String sql = "DELETE FROM Calator WHERE nrLegitimatie=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, nrLegitimatie);
        ps.executeUpdate();
        ps.close();
    }

    // Update calator
    public void updateCalator(Connection con, Calator c) throws SQLException {
        String sql = "UPDATE Calator SET nume=?, prenume=?, tip=?, contact=? WHERE nrLegitimatie=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, c.getNume());
        ps.setString(2, c.getPrenume());
        ps.setString(3, c.getTip());
        ps.setString(4, c.getContact());
        ps.setInt(5, c.getNrLegitimatie());
        ps.executeUpdate();
        ps.close();
    }

    // Cautare dupa nrLegitimatie
    public Calator cautaCalatorDupaNr(Connection con, int nrLegitimatie) throws SQLException {
        String sql = "SELECT * FROM Calator WHERE nrLegitimatie=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, nrLegitimatie);
        ResultSet rs = ps.executeQuery();
        Calator c = null;
        if(rs.next()) {
            c = new Calator();
            c.setNrLegitimatie(rs.getInt("nrLegitimatie"));
            c.setNume(rs.getString("nume"));
            c.setPrenume(rs.getString("prenume"));
            c.setTip(rs.getString("tip"));
            c.setContact(rs.getString("contact"));
        }
        rs.close();
        ps.close();
        return c;
    }

    // Cautare dupa contact
    public List<Calator> cautaCalatorDupaContact(Connection con, String contact) throws SQLException {
        List<Calator> lista = new ArrayList<>();
        String sql = "SELECT * FROM Calator WHERE contact LIKE ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, "%" + contact + "%");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Calator c = new Calator();
            c.setNrLegitimatie(rs.getInt("nrLegitimatie"));
            c.setNume(rs.getString("nume"));
            c.setPrenume(rs.getString("prenume"));
            c.setTip(rs.getString("tip"));
            c.setContact(rs.getString("contact"));
            lista.add(c);
        }
        rs.close();
        ps.close();
        return lista;
    }


}