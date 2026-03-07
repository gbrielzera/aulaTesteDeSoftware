package tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConexaoDB {

    private static final String DB_FILE = "countries.db";
    private Connection connection;

    public ConexaoDB() { // Construtor
        try {
            String url = "jdbc:sqlite:" + DB_FILE;

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
