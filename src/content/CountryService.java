package content;

import java.util.List;

// Camada de serviço — separa regras de negócio do Menu
// O Menu só chama métodos desta classe; a lógica fica aqui.
public class CountryService {

    private final ICountriesDAO dao;

    public CountryService(ICountriesDAO dao) {
        this.dao = dao;
    }

    public void adicionarPais(String nome, String continente, String direcaoDaMao) {
        if (nome == null || nome.isBlank()) {
            System.out.println("[!] O nome do país não pode ser vazio.");
            return;
        }
        if (continente == null || continente.isBlank()) {
            System.out.println("[!] O continente não pode ser vazio.");
            return;
        }
        if (direcaoDaMao == null || direcaoDaMao.isBlank()) {
            System.out.println("[!] A direção da mão não pode ser vazia.");
            return;
        }
        Countries c = new Countries();
        c.setNome(nome.trim());
        c.setContinente(continente.trim());
        c.setDirecaoDaMao(direcaoDaMao.trim());
        dao.inserirPais(c);
        System.out.println("País inserido com sucesso!");
    }

    public List<Countries> listarTodos() {
        return dao.listarPaises();
    }

    public List<Countries> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            System.out.println("[!] Digite um termo para busca.");
            return List.of();
        }
        return dao.buscarPorNome(nome.trim());
    }

    public List<Countries> buscarPorContinente(String continente) {
        if (continente == null || continente.isBlank()) {
            System.out.println("[!] Digite um continente para busca.");
            return List.of();
        }
        return dao.buscarPorContinente(continente.trim());
    }

    public void atualizarPais(int id, String novoNome, String novoContinente, String novaDirecao) {
        if (novoNome == null || novoNome.isBlank()) {
            System.out.println("[!] O nome não pode ser vazio.");
            return;
        }
        Countries c = new Countries();
        c.setId(id);
        c.setNome(novoNome.trim());
        c.setContinente(novoContinente.trim());
        c.setDirecaoDaMao(novaDirecao.trim());
        dao.atualizarPais(c);
    }

    public void deletarPais(int id) {
        dao.deletarPais(id);
    }
}
