package content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    private Connection conn;

    public AuthService(Connection conn) {
        this.conn = conn;
    }

    public boolean realizarLogin(String user, String pass) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retorna true se encontrar o par usuário/senha
        } catch (SQLException e) {
            System.err.println("Erro na autenticação: " + e.getMessage());
            return false;
        }
    }
}