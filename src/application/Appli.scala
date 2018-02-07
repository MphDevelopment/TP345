package application
import java.io.FileWriter
import java.util.Scanner
import library.ExpressionParser

object Appli extends App {
  val recherche = ExpressionParser.readExp
  
  //recherche2url

  val resultats: List[(String, String)] = List() //AnalysePage.resultats
  val text: String = HtmlWriter.process(ProductionHtml.resultat2html(resultats))
  
  val file = new FileWriter("resultat.html")
  try {
    file.write(text)
  } finally file.close()
}