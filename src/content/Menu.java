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
          
            if (resp == 4) {
                Utils.limparTela();
                System.out.println("--- List of Registered Countries ---");
                
                var paises = dao.listarPaises();
                
                if (paises.isEmpty()) {
                    System.out.println("No countries found.");
                } else {
                    for (Countries c : paises) {
                        System.out.println(c.toString());
                    }
                }
                
                System.out.println("\nPress Enter to return to menu...");
                sc.nextLine();
            }
            
            if (resp == 2) {
                Utils.limparTela();
                System.out.println("--- Updating country ---");
                
                // Lista os países para o usuário ver os IDs disponíveis
                var lista = dao.listarPaises();
                for (Countries c : lista) {
                    System.out.println(c.getId() + " - " + c.getNome());
                }

                System.out.print("\nType the updating country ID: ");
                int id = sc.nextInt();
                sc.nextLine(); // Limpa o buffer

                Countries countryParaAtualizar = new Countries();
                countryParaAtualizar.setId(id);

                System.out.print("New name: ");
                countryParaAtualizar.setNome(sc.nextLine());

                System.out.print("New continent: ");
                countryParaAtualizar.setContinente(sc.nextLine());

                System.out.print("New traffic hand: ");
                countryParaAtualizar.setDirecaoDaMao(sc.nextLine());

                dao.atualizarPais(countryParaAtualizar);
                
                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
            }
            
            if (resp == 3) {
                Utils.limparTela();
                System.out.println("--- Removing Country ---");
                
                var lista = dao.listarPaises();
                for (Countries c : lista) {
                    System.out.println(c.getId() + " - " + c.getNome());
                }

                System.out.print("\nType the ID of the country you want to remove: ");
                int id = sc.nextInt();
                sc.nextLine();

                dao.deletarPais(id);
                
                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
            }
            
            
            
            
            
            
            
            
            
            
            
            
        }
        conn.closeConnection(); // Fechar a conexão ao sair
        sc.close();
    }
}