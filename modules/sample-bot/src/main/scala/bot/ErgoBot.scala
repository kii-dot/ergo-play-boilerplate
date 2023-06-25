package bot

import boxes.BoxWrapper
import commons.StackTrace
import commons.node.Client
import errors.{ConnectionException, MempoolBoxRetrievalException, ParseException}
import explorer.Explorer
import org.ergoplatform.appkit.{BlockchainContext, ErgoClientException, ErgoId}
import play.api.libs.json.JsResultException
import txs.{GenericTxProcessor, SingletonDependentTxProcessor}

import javax.inject.Inject
import scala.util.{Failure, Success, Try}

class ErgoBot @Inject() (client: Client, explorer: Explorer) extends Runnable {

  override def run(): Unit = {
    println("ErgoBot: running Ergo bot babeh \n")

    // Run requests with 5 times retry
    try {} catch {
      case e: ParseException =>
        println(e)
      case e: JsResultException =>
        println(e)
      case e: ErgoClientException =>
        println(e)
      case e: ConnectionException =>
        println(e)
      case e: NullPointerException =>
        println(e)
      case e: MempoolBoxRetrievalException =>
        println(e)
      case e: java.lang.IllegalArgumentException =>
        println(e)
      case e: Throwable =>
        println(e)
    }

    println("Clearing Cache")
    System.gc()
    println("Cache cleared")
    println("ErgoBot: Sleepy timeeee")
  }

  def catchAndGo(
    gtp: GenericTxProcessor
  )(implicit ctx: BlockchainContext): Unit =
    try {
      gtp.process()
    } catch {
      case e: ParseException =>
        println(e)
      case e: JsResultException =>
        println(e)
      case e: ErgoClientException =>
        println(e)
      case e: ConnectionException =>
        println(e)
      case e: NullPointerException =>
        println(e)
      case e: MempoolBoxRetrievalException =>
        println(e)
      case e: java.lang.IllegalArgumentException =>
        println(e)
      case e: Throwable =>
        println(e)
    }

  @annotation.tailrec
  final def retry[T](n: Int)(fn: => T): T =
    Try {
      fn
    } match {
      case Success(x) => x
      case _ if n > 1 => retry(n - 1)(fn)
      case Failure(e) => throw e
    }
}
