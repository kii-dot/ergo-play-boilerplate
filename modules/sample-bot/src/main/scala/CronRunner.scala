import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import bot.ErgoBot
import commons.configs.Configs
import commons.node.Client
import play.api.Logger

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

class CronRunner @Inject() (
  client: Client,
  system: ActorSystem,
  ergoBot: ErgoBot
)(implicit ec: ExecutionContext) {
  println("App Started")
  client.setClient()

  val jobs: ActorRef = system.actorOf(
    Props(
      new ErgoJobs(ergoBot)
    ),
    "scheduler"
  )

  system.scheduler.scheduleAtFixedRate(
    initialDelay = 2.seconds,
    interval = Configs.refundThreadInterval.seconds,
    receiver = jobs,
    message = ""
  )
}

class ErgoJobs(
  ergoBot: ErgoBot
) extends Actor
    with ActorLogging {
  private val logger: Logger = Logger(this.getClass)

  override def receive: Receive = {
    case _ =>
      logger.info("ergoBot running full speed!")
      ergoBot.run()
  }
}
