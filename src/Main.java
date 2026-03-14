import tools.ConexaoDB;
import tools.CriarTabela;
import tools.Utils;
import java.util.Scanner;

import content.AuthService;
import content.Menu;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ConexaoDB conexao = new ConexaoDB();
        
        CriarTabela criador = new CriarTabela(conexao.getConnection());
        criador.criarTabela();
        
        AuthService auth = new AuthService(conexao.getConnection());
        boolean logado = false;

        while (!logado) {
            Utils.limparTela();
            System.out.println("====== LOGIN ======");
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();
            
            if (auth.realizarLogin(username, password)) {
                System.out.println("Login successful! Redirecting...");
                logado = true;
                Thread.sleep(1000); 
                
                Utils.limparTela();
                Menu.exibirMenu();
            } else {
                System.out.println("\n[!] Invalid credentials. Try again.");
                System.out.println("Press Enter to continue...");
                sc.nextLine();
            }
        }
        
        conexao.closeConnection();
        sc.close();
    }
}