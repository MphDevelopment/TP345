package test

import application.Analyseur
import library.ExpressionParser
import application.HtmlWriter
import application.ProductionHtml

object AnalyseurTest extends App {
  val resultats = Analyseur.resultats("http://search.vivastreet.com/", ExpressionParser.readExp)
  for ((a,b) <- resultats)
    println(a + "  " + b)
  println(HtmlWriter.process(ProductionHtml.resultat2html(resultats)))
}