package fr.uvsq.pglp.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DossierTest {

    @Test
    public void leNomDuDossierEstNonNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new Dossier(null, "", 0, ""));
        assertEquals("Le nom ne doit pas etre null", exception.getMessage());
    }

    @Test
    public void leNomDuRepertoireEstNonVide() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Dossier("", "", 0, ""));
        assertEquals("Le nom ne doit pas vide", exception.getMessage());
    }

    @Test
    public void leCheminDuDossierEstNonNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new Dossier("Dossier", "", 0, null));
        assertEquals("Le chemin ne doit pas etre null", exception.getMessage());
    }

    @Test
    public void leCheminDuRepertoireEstNonVide() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Dossier("Dossier", "", 0, ""));
        assertEquals("Le chemin ne doit pas vide", exception.getMessage());
    }
}
