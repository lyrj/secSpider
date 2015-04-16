import akka.actor._
import com.kingdee.safe.core.ReadingConfig
import com.typesafe.config.ConfigFactory

import scala.util.control.NonFatal

/**
 * Author   : xiaogo
 * Date     : 2015-04-07 18:08
 * Copyright: Kingdee Co.,Ltd
 */
  object Main {
  def main(args: Array[String]): Unit = {
	  ReadingConfig.argList = args toList
	  val system = ActorSystem("Main")
	  try {
		  system.actorOf(Props(classOf[com.kingdee.safe.core.Scheduler]),"app")
	  } catch {
		  case NonFatal(e) => system.shutdown(); throw e
	  }
  }
}