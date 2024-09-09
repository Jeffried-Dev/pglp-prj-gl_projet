package fr.uvsq.pglp.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente un répertoire abstrait.
 * Elle est utilisée comme classe de base pour différents types de répertoires.
 */
public class Dossier extends Repertoire {

  private List<Repertoire> repertoires = new ArrayList<Repertoire>();

  public Dossier(String name, String annotation, int ner, String path) {
    super(name, annotation, ner, path);
    this.repertoires = new ArrayList<Repertoire>();
  }

  /**
   * Cette methode permet initialiser le repertoire lors de la creation.
   *
   * @return vrai si le dossier est initialise sinon faux
   */
  public int init(String repertoireCourant, File fichierIndex) {
    File repertoire = new File(repertoireCourant);
    String[] contenu = repertoire.list();
    List<String> lignesIndex = lireFichierIndex(fichierIndex);
    try {
      for (int j = 0; j < contenu.length; j++) {
        File element = new File(repertoireCourant + "/" + contenu[j]);
        if (element.isFile()) {
          this.repertoires.add(new Fichier(contenu[j], "", j + 1, repertoireCourant));
        } else if (element.isDirectory()) {
          this.repertoires.add(new Dossier(contenu[j], "", j + 1, repertoireCourant));
        }
        lignesIndex.set(j+1,element.getAbsolutePath()+ "," + " ");
        ecrireFichierIndex(lignesIndex, fichierIndex);
      }
      return this.repertoires.size();
    } catch (Exception e) {
      throw new IllegalArgumentException("Initialisation du repertoire echoue");
    }

  }

  private static List<String> lireFichierIndex(File fichierIndex) {
    List<String> lignes = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(fichierIndex))) {
      String ligne;
      while ((ligne = reader.readLine()) != null) {
        lignes.add(ligne);
      }
    } catch (IOException e) {
      System.err.println("Une erreur est survenu");
    }
    return lignes;
  }

  private static void ecrireFichierIndex(List<String> lignes, File fichierIndex) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierIndex))) {
      for (String ligne : lignes) {
        writer.write(ligne);
        writer.newLine();
      }
    } catch (IOException e) {
      System.err.println("Une erreur est survenu");
    }
  }

  /**
   * Cette methode permet creer le repertoire.
   *
   */
  public int creerRepertoire(Repertoire repertoire) {
    this.repertoires.add(repertoire);
    Path chemin = Paths.get(repertoire.path, repertoire.name);
    if (Files.exists(chemin)) {
      System.out.println("Il exite deja un element de meme nom");
    } else {
      try {
        if (repertoire instanceof Dossier) {
          Files.createDirectories(chemin);
        } else {
          Files.createFile(chemin);
        }

      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return this.repertoires.size();
  }

  /**
   * Cette methode permet rechercher dans le repertoire courant.
   *
   */
  public Repertoire findDirectory(int ner) {
    for (Repertoire repertoire : repertoires) {
      if (repertoire.ner == ner) {
        return repertoire;
      }
    }
    return null;

  }

  public void paste() {

  }

  /**
   *  recuperer les repertoires.
   */
  public List<Repertoire> getRepertoire() {
    return this.repertoires;
  }

  public String findFile(String nameFile) {
    return find(new File(this.getPath()), nameFile, 0);

  }

  /**
   * Cette methode permet de chercher un fichier dans le repertoire .
   *
   * @return path
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

  public List<Repertoire> getRepertoires() {
    return repertoires;
  }

  /**
   * Cette methode poster un repertoire.
   */
  public void past(Repertoire repertoire, boolean delete) throws IOException {
    File source = new File(repertoire.getPath() + "\\" + repertoire.getName());
    File destination = new File(this.getPath() + "\\" + this.getName());
    if (repertoire instanceof Dossier) {
      if (destination.exists()) {
        destination = new File(this.getPath(), repertoire.getName() + " -copy");
      }
      copyDirectory(source, destination);
    } else if (repertoire instanceof Fichier) {
      copyFile(source, destination);
    } else {
      throw new IllegalArgumentException("Le chemin spécifié ne pointe ni vers un fichier ni vers un dossier valide.");
    }
    if (delete) {
      deleteFileOrDirectory(source);
      this.getRepertoires().remove(repertoire);
    }
    this.repertoires.add(repertoire);
  }

  private void copyDirectory(File source, File destination) throws IOException {
    if (!destination.exists()) {
      destination.mkdirs();
    }

    File[] files = source.listFiles();
    if (files != null) {
      for (File file : files) {
        File newFile = new File(destination, file.getName());
        if (file.isDirectory()) {
          copyDirectory(file, newFile);
        } else {
          copyFile(file, newFile);
        }
      }
    }
  }

  private void copyFile(File source, File destination) throws IOException {
    String fileName = source.getName();
    String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
    String extension = fileName.substring(fileName.lastIndexOf('.'));

    File file = new File(this.getPath(), fileName);
    if (file.exists()) {
      File newFile = new File(this.getPath(), baseName + " -copy" + extension);
      Files.copy(source.toPath(), newFile.toPath());
    } else {
      Files.copy(source.toPath(), destination.toPath());
    }
  }

  private void deleteFileOrDirectory(File fileOrDirectory) {
    if (fileOrDirectory.isDirectory()) {
      File[] files = fileOrDirectory.listFiles();
      if (files != null) {
        for (File file : files) {
          deleteFileOrDirectory(file);
        }
      }
    }
    fileOrDirectory.delete();
  }

  @Override
  public Repertoire copy() {

    // a implementer
    return null;
  }

  @Override
  public String toString() {
    return name + "\t \u001B[34m [ " + ner + " ] \u001B[0m ";

  }

  @Override
  public Repertoire cut() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cut'");
  }

}
