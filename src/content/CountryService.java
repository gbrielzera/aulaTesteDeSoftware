package content;

import java.util.List;

public class CountryService {

    private final ICountriesDAO dao;

    public CountryService(ICountriesDAO dao) {
        this.dao = dao;
    }

    public void adicionarPais(String nome, String continente, String direcaoDaMao) {
        // Validação da HU01: Campos obrigatórios não podem ser vazios [cite: 266]
        if (nome == null || nome.isBlank() || continente == null || continente.isBlank() || direcaoDaMao == null || direcaoDaMao.isBlank()) {
            System.out.println("[!] Todos os campos são obrigatórios.");
            return;
        }

        // Validação da RAP001: Nome duplicado [cite: 292]
        if (!dao.buscarPorNome(nome.trim()).isEmpty()) {
            System.out.println("[!] Erro: Este país já está cadastrado.");
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

    // MÉTODO QUE ESTAVA FALTANDO:
    public List<Countries> buscarPorContinente(String continente) {
        if (continente == null || continente.isBlank()) {
            System.out.println("[!] Digite um continente para busca.");
            return List.of();
        }
        return dao.buscarPorContinente(continente.trim());
    }

    public void atualizarPais(int id, String novoNome, String novoContinente, String novaDirecao) {
        // Validação da HU03: Campos não podem ser vazios na edição [cite: 339]
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