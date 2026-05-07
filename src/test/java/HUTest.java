package test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import content.*;
import tools.ConexaoDB;
import java.sql.Connection;
import java.util.List;

public class HUTest {
    private AuthService authService;
    private CountryService countryService;
    private Connection conn;

    @BeforeEach
    void setUp() throws Exception {
        ConexaoDB conexao = new ConexaoDB();
        this.conn = conexao.getConnection();
        this.authService = new AuthService(conn);
        this.countryService = new CountryService(new CountriesDAO(conn));
        
        // Limpa a tabela para garantir o isolamento dos testes (Regressão)
        conn.createStatement().execute("DELETE FROM countries");
    }

    @AfterEach
    void tearDown() {
        System.out.println("--------------------------------------------------");
    }

    // --- TESTES DE UNIDADE (HU01: Login) ---

    @Test
    @DisplayName("Teste de Unidade - Login")
    void testeLogin() {
        System.out.println("\n>>> NÍVEL: TESTE DE UNIDADE");
        System.out.println("HU01 - LOGIN: Validando RAP002 (Bloqueio de senha)");
        
        boolean loginIncorreto = authService.realizarLogin("admin", "senha_errada");
        assertFalse(loginIncorreto, "O login não deveria ser permitido com senha incorreta.");
        System.out.println("[RESULTADO]: Bloqueio de senha incorreta verificado.");

        System.out.println("HU01 - LOGIN: Validando CA4 (Acesso com credenciais corretas)");
        boolean loginSucesso = authService.realizarLogin("admin", "1234");
        assertTrue(loginSucesso, "O login deveria funcionar com as credenciais padrão.");
        System.out.println("[RESULTADO]: Login de sucesso verificado.");
    }

    // --- TESTES DE SISTEMA (HU02: Criar País) ---

    @Test
    @DisplayName("Teste de Sistema - Criar País")
    void testeSistemaCriar() {
        System.out.println("\n>>> NÍVEL: TESTE DE SISTEMA (END-TO-END)");
        System.out.println("HU02 - CRIAR: Validando CA3 e RAP001 (Persistência e Duplicidade)");
        
        // Inserção Válida
        countryService.adicionarPais("Brasil", "América do Sul", "Direita");
        List<Countries> lista = countryService.buscarPorNome("Brasil");
        assertEquals(1, lista.size(), "O país deve estar no banco.");
        System.out.println("[RESULTADO]: Inserção e persistência no SQLite confirmadas.");

        // Tentativa de Duplicado
        System.out.println("HU02 - CRIAR: Testando tentativa de nome duplicado...");
        countryService.adicionarPais("Brasil", "América do Sul", "Direita");
        assertEquals(1, countryService.buscarPorNome("Brasil").size(), "Não deve duplicar.");
        System.out.println("[RESULTADO]: Regra de nome único validada com sucesso.");
    }

    // --- TESTES DE REGRESSÃO (HU03: Editar País) ---

    @Test
    @DisplayName("Teste de Regressão - Editar País")
    void testeRegressaoEditar() {
        System.out.println("\n>>> NÍVEL: TESTE DE REGRESSÃO / INTEGRAÇÃO");
        System.out.println("HU03 - EDITAR: Validando RAP004 (Restrição de Direção)");

        // Prepara um cenário base
        countryService.adicionarPais("Japão", "Ásia", "Esquerda");
        int id = countryService.buscarPorNome("Japão").get(0).getId();

        // Tenta editar com valor inválido
        System.out.println("HU03 - EDITAR: Tentando atualizar direção para 'Cima'...");
        countryService.atualizarPais(id, "Japão", "Ásia", "Cima");
        
        // Garante que a regra de negócio continua protegendo o banco
        Countries c = countryService.listarTodos().get(0);
        assertNotEquals("Cima", c.getDirecaoDaMao(), "O sistema não deve aceitar direções inválidas.");
        System.out.println("[RESULTADO]: A validação de negócio impediu a regressão de dados inválidos.");
    }
}