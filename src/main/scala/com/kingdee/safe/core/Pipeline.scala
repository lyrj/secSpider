package com.kingdee.safe.core

import akka.actor.{Deploy, Props, ActorLogging, Actor}
import com.kingdee.safe.core.messages.Item

/**
 * Author   : xiaogo
 * Date     : 2015-04-08 10:12
 * Copyright: Kingdee Co.,Ltd
 */
import scala.collection.JavaConversions._
class Pipeline extends Actor with ActorLogging with ReadingConfig{
	val lineconf = conf.getConfig("secSpider.pipeline.output_line")
	val pipelines =
		for (i<-lineconf.entrySet())
		yield i.getKey ->
				context.actorOf(new Props(new Deploy,Class.forName(lineconf.getString(i.getKey)),Nil))
	log.info("pipeline has been initialized.")
	def receive = {
		case i:Item =>
			for((name,output) <- pipelines if i.name == name)
				output ! i
	}
}
