package fr.uvsq.pglp.project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cette classe représente l'application principale du projet.
 * Elle contient le point d'entrée principal pour exécuter le programme.
 */
public class Commande {
  private int code;
  private int ner;
  private String commande;
  private String params = "";

  /**
   * Cette classe représente un répertoire abstrait.
   * Elle est utilisée comme classe de base pour différents types de répertoires.
   */
  public Commande() {

  }

  /**
   * Méthode pour retourner une valeur.
   *
   */
  public Commande(int code, int ner, String commande, String params) {
    this.code = code;
    this.ner = ner;
    this.commande = commande;
    this.params = params;
  }

  /**
   * Méthode pour retourner une valeur.
   *
   * @return La valeur retournée par la méthode.
   */
  public Commande getCommande(String cmd) {

    cmd = cmd.trim();
    Commande command = new Commande();
    String pattern1 = "\\d+\\s\\w+|\\d+|\\d+\\s\\-|\\d+\\s\\.|(\\d+)\\s\\+\\s\"([^\"]*)\"";
    String pattern2 = "^find\\s\\w+\\.\\w+|^mkdir\\s\\w+|^past|^hist||^quitter";
    // Création du pattern avec la regex
    Pattern regex = Pattern.compile(pattern1);
    Pattern regex2 = Pattern.compile(pattern2);
    // Création d'un matcher pour la chaîne donnée
    Matcher matcher1 = regex.matcher(cmd);
    Matcher matcher2 = regex2.matcher(cmd);
    String[] cmdLine = cmd.split(" ");

    // Vérification de la chaîne
    if (matcher1.matches()) {

      command.setNer(Integer.parseInt(cmdLine[0]));
      switch (cmdLine[1]) {
        case "copy":
          command.setCode(101);
          command.setCommande("COPY");
          break;
        case "cut":
          command.setCode(102);
          command.setCommande("CUT");
          break;
        case "visu":
          command.setCode(103);
          command.setCommande("VISU");
          break;
        case ".":
          command.setCode(104);
          command.setCommande("CHNG_DIRECTORY");
          break;
        case "+":
          command.setCode(105);
          command.setCommande("ADD_ANN");
          command.setParams(cmd.split("\"")[1]);
          break;
        case "-":
          command.setCode(106);
          command.setCommande("DEL_ANN");
          break;
        default:
          break;
      }
    } else if (matcher2.matches()) {
      switch (cmdLine[0]) {
        case "mkdir":
          command.setCode(203);
          command.setParams(cmdLine[1]);
          command.setCommande("MKDIR");
          break;
        case "past":
          command.setCode(204);
          command.setCommande("PAST");
          break;
        case "find":
          command.setCode(205);
          command.setCommande("FIND");
          command.setParams(cmdLine[1]);
          break;
        case "quitter":
          command.setCode(0);
          command.setCommande("EXIT");
          break;
        case "hist":
          command.setCode(206);
          command.setCommande("EXIT");
          break;
        default:
          System.out.println("commande non reconnue !");
          break;
      }

    } else if (cmd.equals("?")) {

      command.setCode(201);
      command.setCommande("AIDE");
    } else if (cmd.equals("..")) {
      command.setCode(202);
      command.setCommande("UP");
    } else {
      command.setCode(404);
    }
    return command;

  }

  public String toString() {
    return "Code : " + code + "\n NER : " + ner + "\n cmd :" + commande + "\n params :" + params;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public void setNer(int ner) {
    this.ner = ner;
  }

  public void setCommande(String commande) {
    this.commande = commande;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public int getCode() {
    return code;
  }

  public int getNer() {
    return ner;
  }

  public String getParams() {
    return params;
  }
}
