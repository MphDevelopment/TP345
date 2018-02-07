package application

import library.FiltrageURLs
import library.Html
import library.Tag

object FURLs extends FiltrageURLs {

  /**
    * Renvoie la liste de tout les URLs d'annonces de VivaStreet contenu dans le document html h.
    *
    * @param h le document Html
    * @return la liste des URLs d'annonces contenues dans h
    */

  def filtreAnnonce(h:Html):List[String] = {
    var l: List[String] = List()
    h match {
      case Tag(name, attributes, children) =>
        if(name == "a"){
          for( a <- attributes) {
            if(a._2.contains("classified-link")){
              for( a <- attributes) {
                if (a._1 == "href") {
                  if(a._2.startsWith("http://www.vivastreet.com")){
                    l = l ::: List(a._2)
                  }
                }
              }
            }
          }
        }
        for( x <- children){
          if(x.getClass == h.getClass){
            l = l ::: filtreAnnonce(x)
          }
        }
      case _ =>
    }
    l
  }
}
