package application

import library._

object Analyseur extends AnalysePage {
  /** Retourne une liste de recherches à partir d'une expression
    * Exemple:
    *  developpeur and (rennes or nantes) and (python or java)
    *  => List(developpeur+rennes+python,
    *          developpeur+rennes+java,
    *          developpeur+nantes+python,
    *          developpeur+nantes+java)
    *
    * @param exp l'expression
    * @return la liste
    */
  def compileReq(exp:Expression) : List[String] = {
    exp match {
      // On remplace les espaces par des +
      case Word(w) => List(w.replace(' ', '+'))
      case Or(e1,e2) => compileReq(e1)++compileReq(e2)
      case And(e1,e2) => {
        var l = List[String]()
        for (s1 <- compileReq(e1)) {
          l = l ++ compileReq(e2).map(s1 + "+" + _)
        }
        l
      }
    }
  }
/** A partir d'une URL de requête sur le site de référence et d'une expression exp,
    retourne de pages issues de la requête et satisfaisant l'expression.

    @param url l'URL de la requête sur le site de référence
    @param exp l'expression à vérifier sur les pages trouvées
    @return la liste des couples (titre,ref) où ref est l'URL d'une page
            satisfaisant l'expression et titre est son titre. */
  def resultats(url:String,exp:Expression):List[(String,String)] = {
    val urlTools : UrlTools = UrlProcessor
    val filtrageURLs : FiltrageURLs = FURLs
    val filtrageHtml : FiltrageHtml = SearchHtml

    val localUrl = "/annonces/fr?lb=new&search=1&start_field=1&keywords="
    val localUrl2Part1 = "/annonces/fr/t+"
    val localUrl2Part2 = "?lb=new&search=1&start_field=1&keywords="
    val pageMax = 3

    var lURLs : List[String] = List()

    var fullUrl = ""
    for (r <- compileReq(exp)) {
      for(i <- (1 to pageMax)) {
        if (i == 1) {
          fullUrl = urlTools.combineUrls(url, localUrl + r)
          print("Recherche \"" + r + "\". Page de résutat : 1")
        } else {
          // NOTE actuellement, si il n'y a moins de page de résultat que pageMax, on cherche
          // plusieur fois la même page (mais ce n'est pas très grave)
          fullUrl = urlTools.combineUrls(url, localUrl2Part1 + i.toString + localUrl2Part2 + r)
          print(", " + i.toString)
        }

        val html = urlTools.fetch(fullUrl)

        lURLs = lURLs ++ filtrageURLs.filtreAnnonce(html)
      }
      println()
    }
    val lHtml = lURLs.distinct.par                     // suppression des doublons
      .map(x => (x, {print("."); urlTools.fetch(x)}))  // liste d'URL -> liste de couples (URL, HTML)
      .filter(x => filtrageHtml.filtreHtml(x._2, exp)) // on ne garde que les documents satisfaisant
    lHtml.map(x => (getTitle(x._2), x._1)).toList
  }

  private def getTitle(e:Html) : String = {
    val l = getTitleRec(e)
    if (l.size < 1) "(no title)" else l(0)
  }
  private def getTitleRec (e:Html) : List[String] ={
    e match {
      case Tag("title", _, List(Text(t))) => List(t)
      case Tag(_, _, children) => children.map(getTitleRec).fold(List()){((a,b)=>a++b)}
      case _ => List()
    }
  }

}