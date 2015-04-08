package com.kingdee.safe.core

import akka.actor.{ActorLogging, Actor}
import akka.event.Logging
import com.kingdee.safe.core.messages.{Requests, Response}

/**
 * @Author   : xiaogo
 * @Date     : 2015-04-08 10:03
 * @Copyright: Skyworth Co.,Ltd
 */
trait Spider extends Actor with ActorLogging{
	val name:String
	val regexMap:Map[String,(String)=>Any]
	val logger = Logging(context.system,this)
	override def preStart = println("Spider %s has been initialized.".format(self))
	override def receive() = {
		case Response(req,resp) => {

			//Analyze url, sent to scheduler

			//Call processing func.
				for(i<-regexMap.keys)
				{
					if(req.url.matches(i))
						regexMap(i)(resp.content)
				}
			println("Crawled (%d), %s".format(resp.status,req.url))
		}
	}

	def extractURL(x:String):List[Requests] = {
		//TODO extract current HTML to URLList[Requests]
		null
	}
}