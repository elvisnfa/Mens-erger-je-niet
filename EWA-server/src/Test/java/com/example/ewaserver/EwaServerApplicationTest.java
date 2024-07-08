package com.example.ewaserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EwaServerApplicationTest {
    @Autowired
    EwaServerApplication application = null;

    @Test
    void contextLoad() {
        assertNotNull(application);
        System.out.println("application auto-configuration has succeeded.");
    }

    @Test
    public void jUnit5HasBeenConfiguredForTesting() {
        assertThrows(RuntimeException.class, () -> { int a = 0; int b = 1 / a; });
    }

}