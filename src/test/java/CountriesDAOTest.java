package test.java;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import content.Countries;
import content.CountriesDAO;
import content.ICountriesDAO;
import tools.ConexaoDB;
import tools.CriarTabela;

import java.sql.Connection;
import java.util.List;

public class CountriesDAOTest {

    private ConexaoDB conexao;
    private ICountriesDAO dao;

    @Before
    public void setUp() {
        conexao = new ConexaoDB();
        Connection conn = conexao.getConnection();
        dao = new CountriesDAO(conn);

        // Garante que as tabelas existam
        new CriarTabela(conn).criarTabela();

        // Limpa tabela de países antes de cada teste para garantir isolamento
        try {
            conn.createStatement().execute("DELETE FROM countries");
        } catch (Exception e) {
            fail("Falha ao limpar tabela: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        conexao.closeConnection();
    }

    private Countries criarPais(String nome, String continente, String direcao) {
        Countries c = new Countries();
        c.setNome(nome);
        c.setContinente(continente);
        c.setDirecaoDaMao(direcao);
        return c;
    }

    @Test
    public void testInserirEListar() {
        dao.inserirPais(criarPais("Brasil", "América do Sul", "Right"));
        List<Countries> lista = dao.listarPaises();
        assertEquals("Deve haver 1 país na lista", 1, lista.size());
        assertEquals("Nome deve ser Brasil", "Brasil", lista.get(0).getNome());
    }

    @Test
    public void testListarVazio() {
        List<Countries> lista = dao.listarPaises();
        assertTrue("Lista deve estar vazia inicialmente", lista.isEmpty());
    }

    @Test
    public void testBuscarPorNome() {
        dao.inserirPais(criarPais("Brasil", "América do Sul", "Right"));
        dao.inserirPais(criarPais("Portugal", "Europa", "Right"));

        List<Countries> resultado = dao.buscarPorNome("bras");
        assertEquals("Busca por 'bras' deve retornar 1 resultado", 1, resultado.size());
        assertEquals("Brasil", resultado.get(0).getNome());
    }

    @Test
    public void testBuscarPorContinente() {
        dao.inserirPais(criarPais("Brasil", "América do Sul", "Right"));
        dao.inserirPais(criarPais("Argentina", "América do Sul", "Right"));
        dao.inserirPais(criarPais("França", "Europa", "Right"));

        List<Countries> resultado = dao.buscarPorContinente("América");
        assertEquals("Busca por 'América' deve retornar 2 países", 2, resultado.size());
    }

    @Test
    public void testAtualizar() {
        dao.inserirPais(criarPais("Brasil", "América do Sul", "Right"));
        int id = dao.listarPaises().get(0).getId();

        Countries atualizado = criarPais("Brasil Atualizado", "América do Sul", "Right");
        atualizado.setId(id);
        dao.atualizarPais(atualizado);

        assertEquals("Brasil Atualizado", dao.listarPaises().get(0).getNome());
    }

    @Test
    public void testDeletar() {
        dao.inserirPais(criarPais("Brasil", "América do Sul", "Right"));
        int id = dao.listarPaises().get(0).getId();

        dao.deletarPais(id);
        assertTrue("Lista deve estar vazia após deletar", dao.listarPaises().isEmpty());
    }

    @Test
    public void testDeletarIdInexistente() {
        // Não deve lançar exceção ao tentar deletar ID que não existe
        dao.deletarPais(999);
        assertTrue("Lista deve continuar vazia", dao.listarPaises().isEmpty());
    }
}
