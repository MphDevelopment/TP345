package test

import application.HtmlWriter
import library.ExempleHtml

object HtmlWriterTest extends App {
  println(HtmlWriter.process(ExempleHtml.exemple))
}