import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

object Main {
  def main(args: Array[String]): Unit = {
    val prop = new GatlingPropertiesBuilder
    prop.simulationClass("ExampleSimulation")

    Gatling.fromMap(prop.build)
  }
}
