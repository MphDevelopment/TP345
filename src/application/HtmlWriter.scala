package application

import library.Html2String
import library.Html
import library.Tag
import library.Text

object HtmlWriter extends Html2String {
/** Produit la chaîne de caractère correspondant à un document Html

    @param h le document Html
    @return la chaîne de caractère représentant h
 */
  def process(h:Html) : String = processRec(h, "")
  private def processRec(h:Html, prefix:String):String = {
    h match {
      case Tag(name, attributes, children) => prefix+"<"+name+processAttr(attributes)+">" +
        children.map(processRec(_, prefix+"    ")).mkString("\n","\n","\n") +
        prefix+"</"+name+">"
      case Text(string) => prefix+string

    }
  }
  private def processAttr(attributes : List[(String,String)]) : String = {
    attributes.map(p => " "+p._1 + "="+"\"" + p._2+"\"").mkString
  }

}