package example.bridges.reactrouter

import slinky.core.ExternalComponent
import slinky.core.annotations.react

import scala.scalajs.js
import scala.scalajs.js.{|, UndefOr}
import _root_.com.lambdaminute.slinkywrappers.reactrouter.To

@react object NavLink extends ExternalComponent {
  case class Props(
    to: String | To,
    exact: UndefOr[Boolean] = js.undefined,
    activeClassName: UndefOr[String] = js.undefined,
    activeStyle: UndefOr[js.Dynamic] = js.undefined
  )
  override val component = ReactRouterDOM.NavLink
}
