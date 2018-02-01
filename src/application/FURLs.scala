package application

import library.FiltrageURLs
import library.Html
import library.Tag

object FURLs extends FiltrageURLs {
  def filtreAnnonce(h:Html):List[String] = {
    var l: List[String] = List()
    h match {
      case Tag(name, attributes, children) =>
        if(name == "a"){
          for( a <- attributes) {
            if (a._1 == "href") {
              if(a._2.startsWith("http://www.irisa.fr")){
                l = l ::: List(a._2)
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
