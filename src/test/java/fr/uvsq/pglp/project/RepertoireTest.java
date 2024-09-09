package fr.uvsq.pglp.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Modifier;

public class RepertoireTest {

    private Dossier d1;
    private Fichier f1;

    public RepertoireTest() {
    }

    @Test
    void repertoireEstClassAbstract() {
        Class<Repertoire> repClass = Repertoire.class;
        assertTrue(Modifier.isAbstract(repClass.getModifiers()));
    }

}
