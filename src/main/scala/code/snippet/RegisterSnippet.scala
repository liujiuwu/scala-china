package code.snippet

import net.liftweb._
import net.liftweb.common._
import net.liftweb.common.Box
import net.liftweb.http._
import net.liftweb.util.Helpers._
import code.model.User
import code.model.Section
import code.model.Node

class RegisterSnippet extends StatefulSnippet {
  private var userLongin = ""
  private var userName = ""
  private val whence = S.referer openOr "/"

  def dispatch = { case _ => render }

  def render =
    "name=userLongin" #> SHtml.text(userLongin, userLongin = _) &
      "name=userName" #> SHtml.text(userName, userName = _) &
      "type=submit" #> SHtml.onSubmitUnit(process)

  private def process() = {
    Full(userLongin) match {
      /* case Empty if userLongin == "" => {
        S.error("um", "登陆名不能为空!")
      }*/

      case Full(userLongin) => {

        val section: Section = Section.create
        section.name("分享")
        section.save
        
        val node:Node = Node.create
        node.section(section)
        node.name("scala")
        node.save

        S.notice("Name: " + userLongin)
        //S.redirectTo(whence)
      }

      // notice the parameter for error corresponds to
      // the id in the Msg span
      case _ => S.error("userLonginMsg", "Age doesn't parse as a number")
    }
  }
}