package code.snippet

import code.model.Section
import net.liftweb._
import net.liftweb.util._
import net.liftweb.mapper.Descending
import net.liftweb.mapper.OrderBy
import net.liftweb.util.CssSel

class IndexSnippet {
  def sections: CssSel = {
    val sections = Section.findAll(OrderBy(Section.sectionSort, Descending))

    "*" #> sections.map {
      section =>
        "label *" #> section.name &
          "a " #>  section.nodes.map {
            node =>
              "a [href]" #> ("/topics/node" + node.id.get) &
                "a *" #> node.name.get
          }
    }
  }
}