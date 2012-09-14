package code.model

import net.liftweb.mapper.LongKeyedMapper
import net.liftweb.mapper.LongKeyedMetaMapper
import net.liftweb.mapper.MappedLongIndex
import net.liftweb.mapper.MappedText
import net.liftweb.mapper.MappedString
import net.liftweb.mapper.MappedInt
import net.liftweb.mapper.OneToMany
import net.liftweb.mapper.OrderBy
import net.liftweb.mapper.Ascending
import net.liftweb.mapper.IdPK
import net.liftweb.mapper.CRUDify

object Section extends Section with LongKeyedMetaMapper[Section]{
  override def dbTableName = "sections"
}

class Section extends LongKeyedMapper[Section] with IdPK with OneToMany[Long, Section] {
  def getSingleton = Section

  object name extends MappedString(this, 120)
  object sectionSort extends MappedInt(this) {
    override def dbColumnName = "section_sort"
  }
  object nodes extends MappedOneToMany(Node, Node.section, OrderBy(Node.id, Ascending))
}