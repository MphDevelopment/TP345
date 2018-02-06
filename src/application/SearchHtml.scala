package application
import library.FiltrageHtml
import library.And
import library.Expression
import library.FiltrageHtml
import library.Html
import library.Or
import library.Tag
import library.Text
import library.Word

object SearchHtml extends FiltrageHtml {
  
  /** vérifie qu'une page contient une expression
   *  
   * @param h La page html à tester
   * @param e L'expression à rechercher dans cette page
   * @return true si l'expression est trouvable dans la page
   */
  def filtreHtml(h:Html,e:Expression):Boolean= {
    e match {
      case And(e1, e2) => (filtreHtml(h, e1) && filtreHtml(h, e2))
      case Or(e1, e2) => (filtreHtml(h, e1) || filtreHtml(h, e2))
      case Word(w) => findHtml(h, w)
    }
  }

  /** vérifie qu'une page contient bien un mot
   * 
   * @param h La page html à tester
   * @param w Le mot à trouver
   * @return true si la page contient bien le mot
   */
  private def findHtml(h:Html, w:String):Boolean= {
    h match {
      case Tag(_, _, children) => {
        var res = false
        for(c <- children) {
          res = res || findHtml(c, w)
        }
        res
      }
      // On cherche le mot sans vérifier qu'il est isolé (pas utile pour l'instant)
      case Text(t) => t contains w
    }
  }
}