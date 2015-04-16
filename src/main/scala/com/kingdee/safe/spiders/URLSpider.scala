package com.kingdee.safe.spiders

import java.io.{PrintWriter, File}
import akka.actor.{ActorLogging, Actor}
import akka.event.Logging
import com.kingdee.safe.core.Spider
import com.kingdee.safe.core.messages.{Item, Response}

/**
 * Author   : xiaogo
 * Date     : 2015-04-08 15:11
 * Copyright: Kingdee Co.,Ltd
 */

class URLSpider extends Spider{
	val regexMap = Map[String,(Response)=>Any](".*"->parse_AnyPage)
	val name = "URLSpider"

	def parse_AnyPage(x:Response) = {
		val (ripped_url:String,kvMap:Map[Any,Any])
			= UrlExtractor.extractParam(x.requests.url)
		val result = kvMap + ("ripped_url"->ripped_url) + ("origin_url" -> x.requests.url)
		Item(result)
	}
}
