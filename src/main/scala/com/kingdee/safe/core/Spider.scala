package com.kingdee.safe.core

import akka.actor.{ActorLogging, Actor}
import akka.event.Logging
import com.kingdee.safe.core.messages.{Requests, Response}
import org.jsoup._
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


/**
 * Author   : xiaogo
 * Date     : 2015-04-08 10:03
 * Copyright: Kingdee Co.,Ltd
 */
trait Spider extends Actor with ActorLogging{
	val name:String
	val allowed_domain:String
	val regexMap:Map[String,(Response)=>Any]
//	val logger = Logging(context.system,this)
	override def preStart() = println("Spider %s has been initialized.".format(self))
	import scala.collection.JavaConversions._

	override final def receive = {
		case msg:Response =>
			//Analyze url, sent to scheduler
			for(x <- extractURL(msg.responseBody.content))
				sender() ! x
			//Call processing func.
			for(i <- regexMap.keys)
			{
				if(msg.requests.url.matches(i))
					regexMap(i)(Response(msg.requests,msg.responseBody))
			}
			log.info("Crawled (%d), %s".format(msg.responseBody.status,msg.requests.url))
	}

	def extractURL(x:String):Iterator[Requests] = {
		//TODO extract current HTML to URLList[Requests]
		val doc = Jsoup.parse(x)
		val links = doc.getElementsByTag("a")
		for(i:Element <- links.iterator
		    if i.attr("href").contains(allowed_domain))
			yield
				Requests(i.attr("href"))
	}
}