package code.snippet

import net.liftweb.http.{S, SHtml, RequestVar}
import net.liftweb.common.{Full, Box, Empty}
import code.model.Node
import code.model.Section
import xml.{Text, NodeSeq}
import net.liftweb.mapper.{Descending, OrderBy}
import net.liftweb.http.SHtml._
import net.liftweb.common.Full
import xml.Text
import net.liftweb.util.Helpers
import Helpers._


class NodeSnp {

  object nodeVar extends RequestVar[Box[Node]](Full(Node.create))

  def list: NodeSeq = {
    val nodes = Node.findAll(OrderBy(Node.nodeSort, Descending))
    nodes.flatMap(node => {
      <tr>
        <td>
          {node.name}
        </td>
        <td>
          {Section.findByKey(node.section.is).get.name}
        </td>
        <td>
          {node.nodeSort}
        </td>
        <td>
          {link("/admin/nodes/edit", () => nodeVar(Full(node)), Text("修改"))}{link("/admin/nodes/delete", () => nodeVar(Full(node)), Text("删除"))}
        </td>
      </tr>
    })
  }


  def add = {
    val node = nodeVar.is
    val sectionsMap = Section.findAll(OrderBy(Section.sectionSort, Descending)).map(s => (s.id.toString, s.name.toString()))

    "#node_section_id" #> SHtml.select(sectionsMap, Empty,x => nodeVar.is.get.section(x.toLong)) &
      "#node_name" #> SHtml.text(nodeVar.is.get.name, nodeVar.is.get.name(_)) &
      "#node_summary" #> SHtml.text(nodeVar.is.get.summary, nodeVar.is.get.summary(_)) &
      "#node_sort" #> SHtml.text(nodeVar.is.get.nodeSort.toString, x=> nodeVar.is.get.nodeSort(x.toInt)) &
      "#node_submit" #> SHtml.onSubmitUnit(processSubmit)
  }


  def processSubmit() = {
    nodeVar.is.get.validate match {
      case Nil => {
        nodeVar.is.get.save
        S.notice("Event Saved")
        S.seeOther("/admin/nodes/")
      }
      case errors => S.error(errors)
    }
  }
}
