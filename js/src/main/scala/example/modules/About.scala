package example.modules
import slinky.core.annotations.react
import slinky.core.FunctionalComponent
import slinky.web.html._

@react object About {
  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    div(className := "card",
      div(className := "card-header", "About"),
      div(className := "card-body",
        table(className := "table table-striped",
          tbody(
            tr(
              td("author"),
              td("oen")
            ),
            tr(
              td("github"),
              td(a(target := "_blank", href := "https://github.com/oen9/scalajs-react-slinky-akka-starter", "https://github.com/oen9/scalajs-react-slinky-akka-starter"))
            ),
            tr(
              td("heroku"),
              td(a(target := "_blank", href := "https://scalajs-react-slinky-akka-starter.herokuapp.com", "https://scalajs-react-slinky-akka-starter.herokuapp.com"))
            ),
            tr(
              td("use"),
              td("do whatever you want!")
            )
          )
        )
      )
    )
  }
}
