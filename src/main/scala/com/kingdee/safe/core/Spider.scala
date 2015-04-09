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
	val logger = Logging(context.system,this)
	override def preStart() = println("Spider %s has been initialized.".format(self))
	import scala.collection.JavaConversions._

	override final def receive = {
		case Response(req,resp) =>
			//Analyze url, sent to scheduler
			for(x <- extractURL(resp.content))
				sender() ! x
			//Call processing func.
			for(i <- regexMap.keys)
			{
				if(req.url.matches(i))
					regexMap(i)(Response(req,resp))
			}
			logger.error("Crawled (%d), %s".format(resp.status,req.url))
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