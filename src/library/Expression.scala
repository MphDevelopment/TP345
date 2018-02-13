package library

import scala.util.parsing.combinator.syntactical.StdTokenParsers
import scala.util.parsing.combinator.lexical.StdLexical
import scala.io.StdIn._
import org.apache.commons.lang3.StringUtils

/** La case classe des expression */
sealed trait Expression
case class Word(w:String) extends Expression
case class And(e1:Expression,e2:Expression) extends Expression
case class Or(e1:Expression,e2:Expression) extends Expression

/** Le parseur d'expressions */
object ExpressionParser{

  /** Remet les espaces à leurs place dans l'expression
    * @param exp l'expression
    * @return l'expression avec des espaces
    */
  def putSpaces(exp:Expression):Expression= {
    exp match {
      // On elève également les espaces inutiles
      case Word(w) => Word(StringUtils.strip(StringUtils.strip(StringUtils.stripAccents(w.replace('_',' ').toLowerCase))))
      case And(e1, e2) => And(putSpaces(e1), putSpaces(e2))
      case Or(e1, e2) => Or(putSpaces(e1), putSpaces(e2))
    }
  }

  /** La méthode principale du parseur: lit (au clavier) une chaîne de caractères de la forme "((toto AND titi) AND (tata OR tutu))" et produit une
      expression. La méthode itère le processus jusqu'à ce qu'une chaîne de caractère pouvant être transformée en une expression
      est tapée.
    * @return l'expression résultat du parsing.
    */
  def readExp={
    var rep=""
    var query:Expression= Word("")
    while (rep==""){
      println("Donnez votre requète sous forme de mots clés et combinés avec AND/OR\n(NOTE: pas de caratères spéciaux)\nPar exemple: developpeur AND (rennes OR nantes) AND (python OR java)")
      rep = readLine()

      /*
      Pour pouvoir chercher des phrases entiére, on doit renplacer les espaces par d'autres caractères
      (il existe surement un moyen plus "propre" de le faire, mais nous avons réussi à trouver malgré
      la doc de StdLexical et StdTokensParser)
      */
      rep = rep.replace(' ', '_').replaceAll("_OR_", " OR ")
        .replaceAll("_AND_", " AND ")


      val p= LocalParser.parse(rep)
      if (p.successful) query=p.get
      else {println("Malformed query!"); rep =""}
    }

    /*
    On remet les espaces
     */
    query = putSpaces(query)
    query
  }

  /** L'objet local qui implémente le parseur */
  object LocalParser extends StdTokenParsers{
    type Tokens = StdLexical

    import lexical.{ Keyword, NumericLit, StringLit, Identifier }

    val lexical = new StdLexical

    lexical.reserved += ("AND", "OR")
    lexical.delimiters ++= List("(", ")")

    /** Lit au clavier une chaîne de caractères de la forme "((toto AND titi) AND (tata OR tutu))" et produit une liste
      *  des identifiants recontrés dans la chaîne.
   	  @param s la chaîne de caractères à analyser
    @return un objet de type parseResult qui contient l'expression résultat du parsing, si le parsing a réussi.
      */

    def parse(s:String):ParseResult[Expression]={
      expr(new lexical.Scanner(s))
    }

    // the parser itself
    def expr: Parser[Expression] = andExp | orExp | unExp
    def factor: Parser[Expression] = "(" ~> expr <~ ")"

    def andExp: Parser[Expression] = (unExp ~ "AND" ~ expr ^^ { case x ~ _ ~ y => And(x,y) })

    def orExp: Parser[Expression] = (unExp ~ "OR" ~ expr ^^ { case x ~ _ ~ y => Or(x,y) })

    def unExp: Parser[Expression] = word | factor

    def word: Parser[Expression] = (
      ident ^^ { case s => Word(s) }
        | numericLit ^^ {case i => Word(i.toString)})
  }

}