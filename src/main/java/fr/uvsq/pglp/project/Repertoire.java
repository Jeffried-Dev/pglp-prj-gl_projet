package fr.uvsq.pglp.project;

/**
 * Cette classe représente un répertoire abstrait.
 * Elle est utilisée comme classe de base pour différents types de répertoires.
 */

public abstract class Repertoire {
  protected int ner;
  protected String name;
  protected String path;
  protected String annotation;

  /**
   * Constructeur de la classe Repertoire.
   *
   * @param name       Le name du répertoire.
   * @param annotation Les informations sur le contenu du répertoire.
   * @param ner        Le numéro d'identification du répertoire.
   */
  public Repertoire(String name, String annotation, int ner, String path) {
    if (name == null) {
      throw new NullPointerException("Le nom ne doit pas etre null");
    }
    if (name == "") {
      throw new IllegalArgumentException("Le nom ne doit pas vide");
    }
    this.name = name;
    this.path = path;
    this.ner = ner;
  }

  public void setname(String name) {
    this.name = name;
  }

  public String getAnnotation() {
    return annotation;
  }

  public void setAnnotation(String annotation) {
    this.annotation = annotation;
  }

  public void removeAnnotation(String annotation) {
    this.annotation = "";
  }

  public int getNer() {
    return ner;
  }

  public void setNer(int ner) {
    this.ner = ner;
  }

  public String getName() {
    return name;
  }

  public abstract String toString();

  public abstract Repertoire copy();

  public abstract Repertoire cut();
  
  public String getPath() {
    return path;
  }

}
