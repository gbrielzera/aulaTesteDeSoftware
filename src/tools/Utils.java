package tools;

public class Utils {

    public static void limparTela() {
        try {
            String sistema = System.getProperty("os.name");

            if (sistema.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }

        } catch (Exception e) {
            System.out.println("Não foi possível limpar o console.");
        }
    }
}
