package application
import library.ProductionResultat
import library.Html
import library.Tag
import library.Text
object ProductionHtml extends ProductionResultat {
  /**resultat2html : A partir d'une liste (nom, url) crée le code html
   * @param une liste de Set(String (nom), String (url))
   * @return le code Html avec les liens cliquables correspondants.
   */
  def resultat2html(l: List[(String, String)]): Html = {
    var l2 = List[Html]()
    for (e <- l)
    {
      l2 = l2 :+ Tag("a", List(("href", e._2)), List(Text(e._1)))
      l2 = l2 :+ Tag("br", List(), List())
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