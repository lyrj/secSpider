package com.kingdee.safe.core

import akka.actor.{ActorLogging, Actor}
import com.kingdee.safe.core.messages.{ResponseBody, Requests, Response}
import scalaj.http.Http
import scalaj.http._
/**
 * Author   : xiaogo
 * Date     : 2015-04-08 10:02
 * Copyright: Kingdee Co.,Ltd
 */
class Downloader extends Actor with ActorLogging{
	def receive = {
		case msg:Requests =>
			//TODO : Download url and send message to spider.
			try {
				val in = Http(msg.url).timeout(connTimeoutMs = 500, readTimeoutMs = 10000).asString
				if (in.isNotError) {
					if(in.is2xx)
						sender() ! Response(Requests(msg.url, msg.method, msg.params),
							new ResponseBody(in.code, in.toString))
				}
				else if (in.isError) {
					log.error(in.statusLine + msg.url)
//					throw new Exception("Downloader Failed. " )
				}
			}catch
				{
					case e:Exception => log.error(e.toString)
				}
	}
}