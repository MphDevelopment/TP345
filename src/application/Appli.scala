package application
import java.io.FileWriter
import java.util.Scanner
import library.ExpressionParser
import Analyseur

object Appli extends App {
  println("Recherche :")
  val recherche = ExpressionParser.readExp
  
  //recherche2url dans l'analyseur.

  val resultats: List[(String, String)] = Analyseur.resultat(recherche)
  val text: String = HtmlWriter.process(ProductionHtml.resultat2html(resultats))
  
  val file = new FileWriter("resultat.html")
  try {
    file.write(text)
  } finally file.close()
}