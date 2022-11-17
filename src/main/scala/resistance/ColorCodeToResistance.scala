/*                                                                            */
/*      ***************************************************************       */
/*      ***       Resistor ColourCode to value  - by Her4sov -      ***       */
/*      see:  https://en.wikipedia.org/wiki/Electronic_color_code   ***       */
/*      ***               (c) Apache 2.0 License                    ***       */
/*      ***************************************************************       */
/*                                                                            */
package resistance

case class SomeOption(opt: String) 

case class ResistorCode(value: String, common: String) 

type Arg = SomeOption | ResistorCode

object Arg {
  val OptPattern = "-.*".r
  val CodePattern = "(value)(.+)\\.(common)(.+)".r
  
  def unapply(v: String) = v match {
    case OptPattern(_) => SomeOption(v)
    case CodePattern(value, common) => ResistorCode(value, common)
    case _ => ColorCodeToResistance.usage()
  }
}

object ColorCodeToResistance {
  val appName = "cc2res"
  val g = ColorCodeGrouper(colorCodeList)
  def usage() = System.err.print(
      s"Usage: ${ColorCodeToResistance.appName} a-b-c[-d][.to[-te]] ...\n" +
      s"Print decoded resistor(s) data, where a,b,c,d are value stripes,\n" +
      s"to and te - tolerance and temperature coefficient stripes.\n" +
      s"Stripes should be color names or abbreviatures:\n" +
      s"Colors names are: ${g.colors}\n"
  )
 
  def main(args: Array[String]) =
    usage()
}

/* (https://medium.com/wix-engineering/scala-pattern-matching-apply-the-unapply-7237f8c30b41) */
