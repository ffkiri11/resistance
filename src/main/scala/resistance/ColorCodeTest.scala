package resistance

object ColorCodeTest {
  def main(args: Array[String]) = {
    val g = ColorCodeGrouper(colorCodeList)
    assert(g.namesToInt("green") == 5)
    assert(g.namesToInt("wh") == 9)
    assert(g.namesToInt("pink") == -3)
    assert(g.colors(0) == "pink")
    assert(g.abbrs(12) == "wh")
    println(".....")
  }
}
