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
  def filtreHtml(h:Html,e:Expression):Boolean= {
    e match {
      case And(e1, e2) => (filtreHtml(h, e1) && filtreHtml(h, e2))
      case Or(e1, e2) => (filtreHtml(h, e1) || filtreHtml(h, e2))
      case Word(w) => findHtml(h, w)
    }
  }

  private def findHtml(h:Html, w:String):Boolean= {
    h match {
      case Tag(_, _, children) => {
        var res = false
        for(c <- children) {
          res = res || findHtml(c, w)
        }
        res
      }
      case Text(t) => t contains w
    }
  }
}