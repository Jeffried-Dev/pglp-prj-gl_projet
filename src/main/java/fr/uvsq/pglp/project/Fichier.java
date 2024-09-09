package fr.uvsq.pglp.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Cette classe représente l'application principale du projet.
 * Elle contient le point d'entrée principal pour exécuter le programme.
 */
public class Fichier extends Repertoire {

  public String type;
  public String fichiercouper;
  public InputStream contenuFichierCouper;

  public Fichier(String name, String annotation, int ner, String path) {
    super(name, annotation, ner, path);
    type = "";
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String rechercheDeFichier() {
    return find(new File(this.getPath()), this.getName(), 0);
  }

  /**
   * Cette methode permet de chercher un fichier dans le repertoire.
   */
  private String find(File repertoire, String nomFichier, int profondeur) {
    if (repertoire.isDirectory()) {
      String[] contenu = repertoire.list();

      if (contenu != null) {
        for (String nom : contenu) {
          File elementEr = new File(repertoire, nom);

          if (elementEr.isDirectory()) {

            String resultat = find(elementEr, nomFichier, profondeur + 1);
            if (resultat != null) {
              return resultat;
            }
          } else if (nom.equals(nomFichier)) {
            return elementEr.getAbsolutePath();
          }
        }
      }
    }

    return null;
  }

  /**
   * La fonction de visualisation.
   */
  public Object visu() {

    Path path = Paths.get(this.getPath() + "/" + this.getName());
    String typeMime = null;
    try {
      typeMime = Files.probeContentType(path);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Vérifie si le type MIME indique que c'est un fichier texte
    if (typeMime != null && typeMime.contains("text")) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(this.getPath() + "/" + this.getName()));
        String contenu = "\n ======================= | FICHIER : " + this.getName() + " |=================\n\n";
        String ligne;

        // Lire et afficher chaque ligne du fichier
        while ((ligne = br.readLine()) != null) {
          contenu += ligne + "\n";
        }
        br.close();
        return contenu + "\n ========================================================\n";
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        long size = Files.size(path);
        double sizeMo = (double) size / (1024 * 1024); // Convertit la taille en Mo
        return "Taille du fichier " + this + " est : " + Math.floor(sizeMo * 1000.0) / 1000.0 + " MO \n\n";
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    return null;
  }

  @Override
  public String toString() {

    return name + " \t \u001B[31m [ " + ner + " ]\u001B[0m";

  }

  @Override
  public Repertoire copy() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'copy'");
  }

  @Override
  public Repertoire cut() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cut'");
  }

}
