package content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CountriesDAO {
    
    private Connection conn;

    // Construtor
    public CountriesDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public void inserirPais(Countries countries) {
        String sql = "INSERT INTO countries(nome, continente, direcaoDaMao) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, countries.getNome());
            stmt.setString(2, countries.getContinente());
            stmt.setString(3, countries.getDirecaoDaMao());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir país: " + e.getMessage());
        }
    }

    // READ
    public List<Countries> listarPaises() {
        List<Countries> lista = new ArrayList<>();
        String sql = "SELECT * FROM countries";

        try (Statement stmt = this.conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Countries c = new Countries();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setContinente(rs.getString("continente"));
                c.setDirecaoDaMao(rs.getString("direcaoDaMao"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar países: " + e.getMessage());
        }
        return lista;
    }











}
