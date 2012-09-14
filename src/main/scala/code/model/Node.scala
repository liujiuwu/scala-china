package code.model

import net.liftweb.mapper._

object Node extends Node with LongKeyedMetaMapper[Node] {
  override def dbTableName = "nodes"
}

class Node extends LongKeyedMapper[Node] with IdPK {
  def getSingleton = Node

  object name extends MappedString(this, 120)  with ValidateLength {
    override def validations = valMinLen(3, "讨论节点名不能为空") _ ::
      super.validations
  }

  object summary extends MappedString(this, 255)
  object nodeSort extends MappedInt(this) {
    override def dbColumnName = "node_sort"
  }
  object section extends MappedLongForeignKey(this, Section) {
    override def dbColumnName = "section_id"
    override def dbIndexed_? = true
  }
}