package example.bridges.reactrouter

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("react-router-dom", JSImport.Default)
@js.native
object ReactRouterDOM extends js.Object {
  val HashRouter: js.Object = js.native
  val Switch: js.Object = js.native
  val NavLink: js.Object = js.native
  def useParams(): js.Dictionary[String] = js.native
}
