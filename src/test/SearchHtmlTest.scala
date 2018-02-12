package test
import library._
import application.SearchHtml

object SearchHtmlTest extends App {
  println(SearchHtml.filtreHtml(ExempleHtml.exemple2, ExpressionParser.readExp))
}