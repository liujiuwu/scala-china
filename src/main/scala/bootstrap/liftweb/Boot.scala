package bootstrap.liftweb

import code.model.MyDBVendor
import code.model.Node
import code.model.Section
import net.liftweb._
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.mapper.DB
import net.liftweb.mapper.DefaultConnectionIdentifier
import net.liftweb.mapper.Schemifier
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.util._
import net.liftweb.util.Helpers._

class Boot extends Logger{

  def boot {
    //If using defaults FoBo init params can be omitted
    //FoBo.InitParam.JQuery = FoBo.JQuery172
    //FoBo.InitParam.ToolKit=FoBo.FoBo020
    //FoBo.InitParam.ToolKit = FoBo.Bootstrap210
    //FoBo.InitParam.ToolKit = FoBo.Foundation215
    //FoBo.InitParam.ToolKit = FoBo.PrettifyJun2011
    //FoBo.InitParam.ToolKit = FoBo.JQueryMobile110
    //FoBo.InitParam.ToolKit = FoBo.DataTables190
    //FoBo.InitParam.ToolKit = FoBo.Knockout210
    //FoBo.init()
    LiftRules.addToPackages("code")

    DB.defineConnectionManager(DefaultConnectionIdentifier, MyDBVendor)

    LiftRules.setSiteMapFunc(() => MenuInfo.sitemap)

    LiftRules.ajaxStart = Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    LiftRules.ajaxEnd = Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

    Schemifier.schemify(true, Schemifier.infoF _, Section, Node)
  }

}

object MenuInfo {
  import Loc._

  val adminMenus = Menu("管理首页") / "admin" / "index" >> Hidden submenus (
    Menu("分类管理") / "admin" / "sections" / "index" >> LocGroup("admin"),
    Menu("新建分类") / "admin" / "sections" / "add" >> LocGroup("admin"),
    Menu("修改分类") / "admin" / "sections" / "edit" >> Hidden,
    Menu("删除分类") / "admin" / "sections" / "delete" >> Hidden,

    Menu("讨论节点管理") / "admin" / "nodes" / "index" >> LocGroup("admin"),
    Menu("新建讨论节点") / "admin" / "nodes" / "add" >> LocGroup("admin"),
    Menu("修改讨论节点") / "admin" / "nodes" / "edit" >> Hidden,
    Menu("删除讨论节点") / "admin" / "nodes" / "delete" >> Hidden)

  def sitemap() = SiteMap(
    Menu("Home") / "index",
    adminMenus,
    Menu(Loc("help", ("help" :: Nil) -> true, "帮助")))
}

