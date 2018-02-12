package test

import application.FURLs
import library.ExempleHtml
import library.UrlProcessor

object FURLsTest extends App {

 println(FURLs.filtreAnnonce(UrlProcessor.fetch("http://search.vivastreet.com/annonces/fr?lb=new&search=1&start_field=1&select-this=00&searchGeoId=0&offer_type=offer&end_field=")))
}
