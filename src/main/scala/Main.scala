import io.gatling.app.Gatling
import io.gatling.charts.report.ReportGenerator
import io.gatling.core.config.GatlingPropertiesBuilder
import io.gatling.recorder.GatlingRecorder

object Main {
  def main(args: Array[String]): Unit = {
    val prop = new GatlingPropertiesBuilder
    prop.simulationClass("ExampleSimulation")

    Gatling.fromMap(prop.build)
  }
}
