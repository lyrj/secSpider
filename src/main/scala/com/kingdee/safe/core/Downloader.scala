package com.kingdee.safe.core

import akka.actor.Actor
import com.kingdee.safe.core.messages.{ResponseBody, Requests, Response}
import scalaj.http.Http
/**
 * Author   : xiaogo
 * Date     : 2015-04-08 10:02
 * Copyright: Skyworth Co.,Ltd
 */
class Downloader extends Actor{
	println("Downloader has been initialized.")
	def receive = {
		case Requests(url,method,params) => {
			//TODO : Download url and send message to spider.
			val in = Http(url)

			if(in.asString.is2xx) {
				sender() ! Response(Requests(url, method, params),
					ResponseBody(in.asString.code, in.asString.toString))
			}
			else if(in.asString.isRedirect) {
				sender() ! Requests(in.asString.headers("Location"),"GET","")
			}
			else if(in.asString.isError) {
				throw new Exception("Downloader Failed. " + in.asString)
			}

		}
	}
}