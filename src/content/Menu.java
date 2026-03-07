package content;

import java.util.Scanner;
import tools.Utils;
import tools.ConexaoDB;

public class Menu {
    public static void exibirMenu() {
        Scanner sc = new Scanner(System.in);
        ConexaoDB conn = new ConexaoDB(); // Criar fora do loop
        CountriesDAO dao = new CountriesDAO(conn.getConnection());
        
        while(true) {
            System.out.println("\nCountries Library");
            System.out.println("1- Add Country info.");
            System.out.println("2- Edit Country info.");
            System.out.println("3- Remove Country info.");
            System.out.println("4- Show country info.");
            System.out.println("5- Leave.");
            System.out.print("Choose an option: ");
            
            int resp = sc.nextInt();
            sc.nextLine(); // Limpa o buffer após ler o número

            if (resp == 5) {
                Utils.limparTela();
                System.out.println("Leaving...");
                break;
            }
            
            if (resp == 1) {
                Countries country = new Countries(); // Criar um novo objeto para cada inserção
                
                Utils.limparTela();
                System.out.print("Insert a name: ");
                country.setNome(sc.nextLine());
                
                Utils.limparTela();
                System.out.print("Insert a continent: ");
                country.setContinente(sc.nextLine()); // Usar o setter correto
                
                Utils.limparTela();
                System.out.print("Insert the traffic hand: ");
                country.setDirecaoDaMao(sc.nextLine()); // Usar o setter correto
                
                dao.inserirPais(country);
                System.out.println("País inserido com sucesso!");
            }
        }
        conn.closeConnection(); // Fechar a conexão ao sair
        sc.close();
    }
}