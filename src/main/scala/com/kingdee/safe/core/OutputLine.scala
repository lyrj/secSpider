package com.kingdee.safe.core

import akka.actor.Actor
import com.kingdee.safe.core.messages.Item

/**
 * Author   : xiaogo
 * Date     : 2015-04-15 10:27
 * Copyright: Kingdee Co.,Ltd
 */
trait OutputLine extends Actor{
	def write(data:Map[Any,Any])
	override def receive = {
		case i:Item =>
			this.write(i.item)
	}
}
