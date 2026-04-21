package content;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class AuthService {
    private Connection conn;

    public AuthService(Connection conn) {
        this.conn = conn;
    }

    // Gera um Salt aleatório seguro
    public static String gerarSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    // Hash SHA-256 com Salt
    public static String hashSenhaComSalt(String senha, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Concatena salt + senha antes de fazer o hash
            String senhaComSalt = salt + senha;
            byte[] hash = digest.digest(senhaComSalt.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo SHA-256 não encontrado", e);
        }
    }

    // Mantido por compatibilidade (sem salt — não usar para novos cadastros)
    public static String hashSenha(String senha) {
        return hashSenhaComSalt(senha, "");
    }

    public boolean realizarLogin(String user, String pass) {
        // Busca o salt do usuário para recriar o hash corretamente
        String sql = "SELECT password, salt FROM usuarios WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String senhaHashBanco = rs.getString("password");
                String salt           = rs.getString("salt");
                String hashTentativa  = hashSenhaComSalt(pass, salt);
                return hashTentativa.equals(senhaHashBanco);
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Erro na autenticação: " + e.getMessage());
            return false;
        }
    }
}
