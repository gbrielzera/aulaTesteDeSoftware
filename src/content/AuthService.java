package content;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    private Connection conn;

    public AuthService(Connection conn) {
        this.conn = conn;
    }

    // Gera hash SHA-256 da senha antes de comparar com o banco
    public static String hashSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo SHA-256 não encontrado", e);
        }
    }

    public boolean realizarLogin(String user, String pass) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            stmt.setString(2, hashSenha(pass)); // Compara o hash, não a senha pura
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erro na autenticação: " + e.getMessage());
            return false;
        }
    }
}