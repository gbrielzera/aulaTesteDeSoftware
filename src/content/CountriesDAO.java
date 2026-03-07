package content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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













}
