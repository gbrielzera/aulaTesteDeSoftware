package tools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoDB {

    private Connection connection;

    // Lê as configs do arquivo db.properties
    private static String carregarUrlBanco() {
        Properties props = new Properties();
        try (InputStream input = ConexaoDB.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (IOException e) {
            System.err.println("Aviso: db.properties não encontrado, usando padrão.");
        }
        String dbFile = props.getProperty("db.file", "countries.db");
        return "jdbc:sqlite:" + dbFile;
    }

    public ConexaoDB() {
        try {
            String url = carregarUrlBanco();
            connection = DriverManager.getConnection(url);
            System.out.println("Conexão com SQLite feita com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão encerrada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão " + e.getMessage());
            }
        }
    }
}
