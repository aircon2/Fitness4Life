package model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPerson {
    private Person test;
    
    @BeforeEach
    void runBefore() {
        test = new Person("Angela", 100);
    }

    @Test
    void sampleTest() {
        assertTrue(true);
    }
}
