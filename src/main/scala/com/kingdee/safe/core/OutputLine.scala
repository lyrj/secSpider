package com.kingdee.safe.core

import akka.actor.{ActorLogging, Actor}
import com.kingdee.safe.core.messages.Item

/**
 * Author   : xiaogo
 * Date     : 2015-04-15 10:27
 * Copyright: Kingdee Co.,Ltd
 */
trait OutputLine extends Actor with ActorLogging with ReadingConfig{
	def write(data:Map[Any,Any])
	override def receive = {
		case i:Item =>
			this.write(i.item)
		case _ =>
			log.error("Fatal : OutputLine disMatched.")
	}
}
