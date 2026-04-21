package test.java;

import static org.junit.Assert.*;
import org.junit.Test;
import content.AuthService;

public class AuthTest {
    @Test
    public void testeQueFalha() {
        // Este teste falhará propositalmente para o exercício
        assertEquals("Falha proposital", 1, 2);
    }
}