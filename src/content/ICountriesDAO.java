package content;

import java.util.List;

// Interface que define o contrato do DAO de países.
// Facilita trocar a implementação (ex: SQLite → PostgreSQL) sem alterar o restante do código.
public interface ICountriesDAO {

    void inserirPais(Countries countries);

    List<Countries> listarPaises();

    List<Countries> buscarPorNome(String nome);

    List<Countries> buscarPorContinente(String continente);

    void atualizarPais(Countries countries);

    void deletarPais(int id);
}
