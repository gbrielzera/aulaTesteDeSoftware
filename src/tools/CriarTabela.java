package tools;

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

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Tabela criada ou já existente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}