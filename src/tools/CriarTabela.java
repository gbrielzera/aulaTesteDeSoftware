package tools;

import content.AuthService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

public class CriarTabela {

    private Connection conn;

    public CriarTabela(Connection conn) {
        this.conn = conn;
    }

    // Lê usuário e senha seed do db.properties
    private static String[] carregarSeedUsuario() {
        Properties props = new Properties();
        try (InputStream input = CriarTabela.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (IOException e) {
            System.err.println("Aviso: db.properties não encontrado, usando padrão.");
        }
        return new String[]{
            props.getProperty("seed.username", "admin"),
            props.getProperty("seed.password", "1234")
        };
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
                password TEXT NOT NULL,
                salt TEXT NOT NULL DEFAULT ''
            );
        """;

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.execute(sqlUsers);

            // Adiciona coluna salt se não existir
            try {
                stmt.execute("ALTER TABLE usuarios ADD COLUMN salt TEXT NOT NULL DEFAULT ''");
            } catch (Exception ignored) {
                // Coluna já existe — ignorar
            }

            // Seed do usuário admin com salt
            String[] seed = carregarSeedUsuario();
            String username = seed[0];
            String senha    = seed[1];
            String salt     = AuthService.gerarSalt();
            String senhaHash = AuthService.hashSenhaComSalt(senha, salt);

            String seedUser = "INSERT OR IGNORE INTO usuarios (username, password, salt) VALUES ('"
                    + username + "', '" + senhaHash + "', '" + salt + "');";
            stmt.execute(seedUser);

            System.out.println("Tabela criada ou já existente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
