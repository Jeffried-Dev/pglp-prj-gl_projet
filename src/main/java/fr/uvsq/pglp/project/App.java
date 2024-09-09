package fr.uvsq.pglp.project;

import com.github.lalyos.jfiglet.FigletFont;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 * Cette classe représente l'application principale du projet.
 * Elle contient le point d'entrée principal pour exécuter le programme.
 */
public class App {

  /**
   * Méthode principale pour exécuter le programme.
   *
   * @param args Les arguments de la ligne de commande.
   */

  public static void main(String[] args) {
    Repertoire cutFile = null;
    Repertoire copyFile = null;
    List<String> histories = new ArrayList<String>();
    String userCmd = "";
    Boolean exit = false;
    Commande cmd = new Commande();
    String currentPath = "";
    File fichierIndex = null;

    int lastIndex = 0;
    String espace = String.format("%" + 30 + "s", "");
    // Affichage du titre
    try {
      // boolean valide = false;
      System.out.println(FigletFont.convertOneLine("PARAMETRES"));
      // Recuperation du chemin vers le repertoire courant
      Scanner sc = new Scanner(System.in);
      String path;

      do {
        System.out.print("Repertoire courant : ");
        path = sc.nextLine();
        // Utilisation de l'espace de remplissage pour aligner la saisie
        Path rep = Paths.get(path);
        // creation du fichier note
        fichierIndex = new File(path + File.separator + "notes.txt");
        if (!fichierIndex.exists()) {
          try {
            fichierIndex.createNewFile();
          } catch (IOException e) {
            System.err.println("Une erreur est survenu");
          }
        }

        if ((Files.exists(rep) && Files.isDirectory(rep))) {
          currentPath = path;
          break;

        } else {
          System.out.println("Aucun fichier/repertoire trouver ou le chemin entrer n'est pas un dossier ...\n");
        }
      } while (path.isEmpty() || path == null);

      System.out.println(FigletFont.convertOneLine("GEST & FICHIER"));
      // Creation de la racine du gestionnaire du fichier
      Dossier currentDirectory = new Dossier("Racine", "", lastIndex, path);
      lastIndex = currentDirectory.init(path, fichierIndex);
      System.out.println(currentPath);
      printCurrentDirectory(currentDirectory.getRepertoires(), espace);
      Terminal terminal = TerminalBuilder.builder().system(true).dumb(true).build();
      LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();

      do {
        do {
          String prompt = "Entrez une commande : #>";
          userCmd = lineReader.readLine(" ".repeat((terminal.getWidth()) / 4) + prompt);
        } while (userCmd == null || userCmd.isEmpty());
        cmd = cmd.getCommande(userCmd);
        histories.add(userCmd);
        switch (cmd.getCode()) {
          case 0:
            exit = true;
            System.out.println("Vous avez ferme le programme");
            break;
          case 101:
            copyFile = currentDirectory.findDirectory(cmd.getNer());
            System.err.println("Vous avez copier : \t " + copyFile);

            break;
          case 102:
            cutFile = currentDirectory.findDirectory(cmd.getNer());
            System.out.println("Vous avez copier : \t " + cutFile);
            break;
          case 103:
            Repertoire tempVisu = currentDirectory.findDirectory(cmd.getNer());

            if (tempVisu instanceof Fichier) {
              System.out.print(((Fichier) tempVisu).visu());

            } else {
              System.out.print("Cette commande est reserve pour les repertoires de type <<Fichier>>\n \n");
            }
            break;
          case 104:
            lastIndex = 0;
            Repertoire temp = currentDirectory.findDirectory(cmd.getNer());
            if (temp instanceof Dossier) {
              currentDirectory = (Dossier) temp;
              currentPath = currentDirectory.getPath() + "/" + currentDirectory.getName();
              lastIndex = currentDirectory.init(currentPath, fichierIndex);
            } else {
              throw new IllegalArgumentException("Impossible de se deplacer dans un fichier");
            }
            break;
          case 202:
            currentPath = currentPath.replaceAll("/" + currentDirectory.getName(), "");
            lastIndex = 0;
            String[] tabs = currentPath.split("/");
            currentDirectory = new Dossier(tabs[tabs.length - 1], "", lastIndex, currentPath);
            lastIndex = currentDirectory.init(currentPath, fichierIndex);
            break;
          case 203:
            Dossier doc = new Dossier(cmd.getParams(), "", lastIndex + 1, currentPath);
            lastIndex = currentDirectory.creerRepertoire(doc);
            System.out.println(espace + "Votre dossier a ete cree !!!\n \n");
            break;
          case 204:
            if (copyFile != null) {
              currentDirectory.past(copyFile, false);
              System.out.println(espace + "Votre repertoire à coller avec success  !!!");
            } else if (cutFile != null) {
              currentDirectory.past(cutFile, true);
              System.out.println(espace + "Votre repertoire à coller avec success  !!!");
            } else {
              System.out.println(espace + "Copier ou Couper un fichier au préalable !!!");
            }
            break;
          case 205:
            String absolutePath = currentDirectory.findFile(cmd.getParams());
            if (absolutePath != null) {
              System.out.println("\t Chemin Absolue du fichier : \u001B[32m" + absolutePath + "\u001B[0m");
            } else {
              System.out.println("le fichier \u001B[31m  " + cmd.getParams() + " \u001B[0m non trouve ");
            }
            break;
          case 206:
            System.out.println(FigletFont.convertOneLine("HIST COMMANDE"));
            for (String h : histories) {
              System.out.println(espace + "#>" + h + "\n");
            }

            break;
          case 404:
            System.out.println("Commande incorrect !!! ");
            break;
          default:
            System.out.println("Instruction non reconnue !");
          
            break;
        }
        System.out.println(currentPath);
        printCurrentDirectory(currentDirectory.getRepertoires(), espace);
      } while (!exit);

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * Cette méthode permet d'afficher le contenu d'un répertoire.
   *
   */
  public static void printCurrentDirectory(List<Repertoire> currentDirectory, String espace) {
    for (Repertoire r : currentDirectory) {
      System.err.println(espace + "|_" + r);
    }
  }
}
