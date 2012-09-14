package code.snippet

import scala.xml.Group
import scala.xml.NodeSeq
import scala.xml.NodeSeq.seqToNodeSeq
import scala.xml.Text

import code.model.Section
import net.liftweb.common.Box
import net.liftweb.common.Box.box2Option
import net.liftweb.common.Full
import net.liftweb.http.RequestVar
import net.liftweb.http.S
import net.liftweb.http.S.error
import net.liftweb.http.S.redirectTo
import net.liftweb.http.SHtml
import net.liftweb.http.SHtml.link
import net.liftweb.http.SHtml.submit
import net.liftweb.mapper.Descending
import net.liftweb.mapper.MappedField.mapToType
import net.liftweb.mapper.OrderBy
import net.liftweb.util.Helpers.bind
import net.liftweb.util.Helpers.strToCssBindPromoter
import net.liftweb.util.Helpers.strToSuperArrowAssoc

class SectionSnp {

  object sectionVar extends RequestVar[Box[Section]](Full(Section.create))

  def list: NodeSeq = {
    val sections = Section.findAll(OrderBy(Section.sectionSort, Descending))
    sections.flatMap(section => {
      <tr>
        <td>
          { section.name }
        </td><td>
               { section.sectionSort }
             </td>
        <td>
          { link("/admin/sections/edit", () => sectionVar(Full(section)), Text("修改")) }{ link("/admin/sections/delete", () => sectionVar(Full(section)), Text("删除")) }
        </td>
      </tr>
    })
  }

  def confirmDelete(xhtml: Group): NodeSeq = {
    (for (section <- sectionVar.is) // find the user
    yield {
      def deleteSection() {
        section.delete_!
        redirectTo("/admin/sections.html")
      }

      bind("xmp", xhtml, "name" -> (section.name.is),
        "delete" -> submit("Delete", deleteSection _))

    }) openOr {
      error("User not found"); redirectTo("/admin/sections.html")
    }
  }

  def edit(xhtml: NodeSeq): NodeSeq = {
    val section = Section.findByKey(1L)
    bind("acct", xhtml,
      "name" -> section.get.name)
  }

  private def validateName(name: String) = {
    if (name == "") {
      S.error("error_section_name", "分类名不能为空")
      val cssSel = "#group_section_name [class+]" #> "error"
    } else {
      sectionVar.is.get.name(name)
    }
  }

  def add = {
    "#section_name" #> SHtml.text(sectionVar.is.get.name, validateName(_)) & 
      "#section_submit" #> SHtml.onSubmitUnit(processSubmit)
  }

  def processSubmit() = {
    val section = sectionVar.is
    if (section.get.name == "") {
      //S.error("error_section_name","分类名不能为空")
    } else {
      sectionVar.is.get.validate match {
        case Nil => {
          sectionVar.is.get.save
          S.notice("Event Saved")
          S.seeOther("/admin/sections/")
        }
        case errors => S.error("")
      }
    }
  }

  def delete = {
    if (!sectionVar.set_?)
      S.redirectTo("/admin/sections/")

    var e = sectionVar.is.get
    "#section_name" #> sectionVar.is.get.name &
      "#yes" #> SHtml.link("/admin/sections/", () => {
        e.delete_!
      }, Text("确定")) &
      "#no" #> SHtml.link("/admin/sections/", () => {}, Text("取消"))
  }

  def edit = {
    if (!sectionVar.set_?)
      S.redirectTo("/admin/sections/")

    val section = sectionVar.is.get
    "#hidden" #> SHtml.hidden(() => sectionVar(Full(section))) &
      "#section_name" #> SHtml.text(sectionVar.is.get.name, name => sectionVar.is.get.name(name)) &
      "#section_submit" #> SHtml.onSubmitUnit(processSubmit)
  }

}