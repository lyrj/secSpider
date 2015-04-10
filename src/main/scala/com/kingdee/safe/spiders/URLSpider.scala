package com.kingdee.safe.spiders

import akka.actor.{ActorLogging, Actor}
import akka.event.Logging
import com.kingdee.safe.core.Spider
import com.kingdee.safe.core.messages.Response

/**
 * Author   : xiaogo
 * Date     : 2015-04-08 15:11
 * Copyright: Kingdee Co.,Ltd
 */

class URLSpider extends Spider{
	val regexMap = Map[String,(Response)=>Any](".*"->parse_AnyPage)
	val name = "URLSpider"
	val allowed_domain = "feidee.com"
	def parse_AnyPage(x:Response) = {

	}

}

