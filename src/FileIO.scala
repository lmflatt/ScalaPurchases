import java.io.{File, PrintWriter}

import scala.collection.mutable
import scala.io.Source

object FileIO {

  val purchases = mutable.MutableList[(String, String, String, String, String)]()

  def main(args: Array[String]) {

    Source
      .fromFile("be594d3c-purchases.csv")
      .getLines
      .drop(1)
      .foreach( line => {
        val Array(customer_id, date, credit_card, cvv, category) = line.split(",").map(_.trim)
        purchases.+=((customer_id, date, credit_card, cvv, category.toUpperCase))
      })

    def prompt(s: String) = {println(s); io.StdIn.readLine()}

    def menu = {
      val seq = Seq("alcohol", "furniture", "shoes", "toiletries", "food", "jewelry").mkString("\n")
      prompt(s"\nPlease select your choice:\n${seq}\n").toUpperCase
    }

    def sort(s: String) = {
      val pw = new PrintWriter(new File(s"filtered_${s}_purchases.prn" ))
      purchases.foreach(x =>
      if (x._5 == s) {
        val s = s"Customer: ${x._1}, Date: ${x._2.substring(0,10)}"
        println(s)
        pw.write(s + "\n")
      })
      pw.close
      ""}


    var resp = ""
    while (resp != "Q") {
      resp = menu match {
        case "ALCOHOL" => sort("ALCOHOL")
        case "FURNITURE" => sort("FURNITURE")
        case "SHOES" => sort("SHOES")
        case "TOILETRIES" => sort("TOILETRIES")
        case "FOOD" => sort("FOOD")
        case "JEWELRY" => sort("JEWELRY")
        case "Q" => "Q"
      }
    }
  }
}
