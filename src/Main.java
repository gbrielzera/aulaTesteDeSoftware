import tools.ConexaoDB;
import tools.CriarTabela;
import tools.Utils;
import content.Menu;

public class Main {
    public static void main(String[] args) throws Exception {
        ConexaoDB conexao = new ConexaoDB();
        
        CriarTabela criador = new CriarTabela(conexao.getConnection());
        criador.criarTabela();
        
        Utils.limparTela();
        Menu.exibirMenu();
        
        conexao.closeConnection();
    }
}