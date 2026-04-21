package content;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import tools.Utils;
import tools.ConexaoDB;

public class Menu {

    public static void exibirMenu(ConexaoDB conn) {
        Scanner sc = new Scanner(System.in);
        ICountriesDAO dao = new CountriesDAO(conn.getConnection());
        CountryService service = new CountryService(dao);

        while (true) {
            System.out.println("\nCountries Library");
            System.out.println("1- Add Country info.");
            System.out.println("2- Edit Country info.");
            System.out.println("3- Remove Country info.");
            System.out.println("4- Show country info.");
            System.out.println("5- Search by name.");
            System.out.println("6- Search by continent.");
            System.out.println("7- Leave.");
            System.out.print("Choose an option: ");

            int resp = lerInteiro(sc);
            sc.nextLine();

            if (resp == 7) {
                Utils.limparTela();
                System.out.println("Leaving...");
                break;
            }

            if (resp == 1) {
                Utils.limparTela();
                System.out.print("Insert a name: ");
                String nome = sc.nextLine().trim();

                System.out.print("Insert a continent: ");
                String continente = sc.nextLine().trim();

                System.out.print("Insert the traffic hand (Left/Right): ");
                String direcao = sc.nextLine().trim();

                service.adicionarPais(nome, continente, direcao);
            }

            if (resp == 4) {
                Utils.limparTela();
                System.out.println("--- List of Registered Countries ---");
                List<Countries> paises = service.listarTodos();
                exibirLista(paises);
                System.out.println("\nPress Enter to return to menu...");
                sc.nextLine();
            }

            if (resp == 5) {
                Utils.limparTela();
                System.out.print("Search by name: ");
                String termo = sc.nextLine();
                List<Countries> resultado = service.buscarPorNome(termo);
                exibirLista(resultado);
                System.out.println("\nPress Enter to return to menu...");
                sc.nextLine();
            }

            if (resp == 6) {
                Utils.limparTela();
                System.out.print("Search by continent: ");
                String continente = sc.nextLine();
                List<Countries> resultado = service.buscarPorContinente(continente);
                exibirLista(resultado);
                System.out.println("\nPress Enter to return to menu...");
                sc.nextLine();
            }

            if (resp == 2) {
                Utils.limparTela();
                System.out.println("--- Updating country ---");
                List<Countries> lista = service.listarTodos();
                exibirLista(lista);

                System.out.print("\nType the updating country ID: ");
                int id = lerInteiro(sc);
                sc.nextLine();

                System.out.print("New name: ");
                String nome = sc.nextLine().trim();
                System.out.print("New continent: ");
                String continente = sc.nextLine().trim();
                System.out.print("New traffic hand: ");
                String direcao = sc.nextLine().trim();

                service.atualizarPais(id, nome, continente, direcao);

                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
            }

            if (resp == 3) {
                Utils.limparTela();
                System.out.println("--- Removing Country ---");
                List<Countries> lista = service.listarTodos();
                exibirLista(lista);

                System.out.print("\nType the ID of the country you want to remove: ");
                int id = lerInteiro(sc);
                sc.nextLine();

                // Confirmação antes de excluir
                System.out.print("Are you sure you want to remove ID " + id + "? (s/n): ");
                String confirmacao = sc.nextLine().trim().toLowerCase();
                if (confirmacao.equals("s") || confirmacao.equals("sim")) {
                    service.deletarPais(id);
                } else {
                    System.out.println("Operação cancelada.");
                }

                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
            }
        }
        sc.close();
    }

    // Lê um inteiro com proteção contra InputMismatchException
    private static int lerInteiro(Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine(); // Descarta a entrada inválida
                System.out.print("[!] Invalid input. Please enter a number: ");
            }
        }
    }

    // Exibe a lista de países formatada
    private static void exibirLista(List<Countries> lista) {
        if (lista.isEmpty()) {
            System.out.println("No countries found.");
        } else {
            for (Countries c : lista) {
                System.out.printf("[%d] %-30s | %-20s | %s%n",
                    c.getId(), c.getNome(), c.getContinente(), c.getDirecaoDaMao());
            }
        }
    }
}
