package tools;

import content.AuthService;

import java.sql.Connection;
import java.sql.Statement;

public class CriarTabela {

    private Connection conn;

    public CriarTabela(Connection conn) {
        this.conn = conn;
    }

    public void criarTabela() {

        String sql = """
            CREATE TABLE IF NOT EXISTS countries (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                continente TEXT NOT NULL,
                direcaoDaMao TEXT NOT NULL
            );
        """;

        String sqlUsers = """
                    CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL
            );
        """;

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.execute(sqlUsers);

            // Senha armazenada como hash SHA-256 (não mais em texto puro)
            String senhaHash = AuthService.hashSenha("1234");
            String seedUser = "INSERT OR IGNORE INTO usuarios (username, password) VALUES ('admin', '" + senhaHash + "');";
            stmt.execute(seedUser);
            System.out.println("Tabela criada ou já existente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}