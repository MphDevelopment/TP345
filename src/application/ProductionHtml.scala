package application
import library.ProductionResultat
import library.Html
import library.Tag
import library.Text
class ProductionHtml extends ProductionResultat {
  def resultat2html(l: List[(String, String)]): Html = {
    var l2 = List()
    for (e <- l)
    {
      l2.::(Tag("a", List(("href", e._2)), List(Text(e._1))))
    }
    
    val page = Tag("html", List(),
      List(
        Tag("head", List(),
          List(
            Tag("meta", List(("content", "text/html"), ("charset", "UTF-8")), List()),
            Tag("title", List(), List(Text("Recerche"))))),
        Tag("body", List(), List(
          Text("&nbsp"),
          Tag("center", List(), l2)))))
    return page

  }
}