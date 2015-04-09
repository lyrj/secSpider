import akka.actor._
import com.typesafe.config.ConfigFactory

import scala.util.control.NonFatal

/**
 * Author   : xiaogo
 * Date     : 2015-04-07 18:08
 * Copyright: Kingdee Co.,Ltd
 */
  object Main {
  def main(args: Array[String]): Unit = {
	  val system = ActorSystem("Main")
	  try {
		  //ExtendedActorSystem].dynamicAccess.getClassFor[Actor](args(0)).get
		  val app = system.actorOf(Props(classOf[com.kingdee.safe.core.Scheduler],
			  "http://www.feidee.com"::Nil,"kingdee.com"::Nil,"URLSpider"), "app")
		  val terminator = system.actorOf(Props(classOf[Terminator], app), "app-terminator")
	  } catch {
		  case NonFatal(e) => system.shutdown(); throw e

	  }
  }
	class Terminator(app: ActorRef) extends Actor with ActorLogging {
		context watch app
		def receive = {
			case Terminated(_) =>
				log.info("application supervisor has terminated, shutting down")
				context.system.shutdown()
		}
	}
}