package code.snippet
import net.liftweb.http.S
import scala.xml.MetaData
import scala.xml.NodeSeq.seqToNodeSeq
import scala.xml.Null
import scala.xml.Text
import scala.xml.UnprefixedAttribute

class AttrLoc {
  val Split = "(.+?):(.+?)".r
  def r(attr: MetaData): MetaData = {
    attr.value.text match {
      case Split(attrName, locId) => new UnprefixedAttribute(attrName,
        S.loc(locId, Text(locId)), Null)
      case _ => Null
    }
  }
} 