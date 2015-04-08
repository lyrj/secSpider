package com.kingdee.safe.spiders

import akka.actor.{ActorLogging, Actor}
import akka.event.Logging
import com.kingdee.safe.core.Spider

/**
 * @Author   : xiaogo
 * @Date     : 2015-04-08 15:11
 * @Copyright: Skyworth Co.,Ltd
 */
class URLSpider extends Spider{
	val regexMap = Map[String,(String)=>Any](".*"->parse_anypage)

	val name = "URLSpider"
	def parse_anypage(x:String) = {
		logger.debug("parse_anypage hit.")

	}
}
