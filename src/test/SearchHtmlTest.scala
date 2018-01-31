package test
import library._

object SearchHtmlTest extends App {
  println(SearchHtml.filtreHtml(ExempleHtml.exemple2, ExpressionParser.readExp))
}