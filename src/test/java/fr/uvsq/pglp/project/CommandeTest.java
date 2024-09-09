package fr.uvsq.pglp.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class CommandeTest {

    @Test
    public void commandeEstIvalide(){
        Commande cmd =new Commande();
         Exception exception = assertThrows(IllegalArgumentException.class, () -> cmd.getCommande("1234"));
        assertEquals("Commande invalide !!!", exception.getMessage());
    }
}
