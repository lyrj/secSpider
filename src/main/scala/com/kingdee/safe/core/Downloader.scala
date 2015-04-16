package com.kingdee.safe.core

import java.net.{MalformedURLException, SocketTimeoutException, UnknownHostException}
import akka.actor.{Actor, ActorLogging}
import com.kingdee.safe.core.messages.{Requests, Response, ResponseBody}
import scalaj.http.Http
/**
 * Author   : xiaogo
 * Date     : 2015-04-08 10:02
 * Copyright: Kingdee Co.,Ltd
 */
class Downloader extends Actor with ActorLogging with ReadingConfig{
	//Read config from config file
	val connTimeoutMs = conf.getInt("secSpider.downloader.connectTimeOut")
	val readTimeoutMs = conf.getInt("secSpider.downloader.readTimeOut")
	val PryTimeOutMs = conf.getInt("secSpider.downloader.pry_timeout")
	def receive = {
		case msg:Requests =>
			try {
				//test if url is a file
				if(isFile (msg.url))
					throw new MalformedURLException("URL is File, interrupt download. + " + msg.url)
				//test if url is http
				if(!msg.url.startsWith("http"))
					throw new MalformedURLException("URL is not http protocol " + msg.url)
				//open http
				val in = Http(msg.url)
					.timeout(
				        connTimeoutMs = connTimeoutMs,
				        readTimeoutMs = readTimeoutMs)
							.asString
				if (in.isNotError) {
					if(in.is2xx)
						sender() ! Response(Requests(msg.url, msg.method, msg.params),
							new ResponseBody(in.code, in.toString))
				}
				else if (in.isError) {
					throw new Exception("Downloader Failed. " +in.code + msg.url)
				}
			}catch
				{
					case e:SocketTimeoutException => log.info("Connect Time Out / " + msg.url)
					case e:MalformedURLException => log.info("URL is malformed, interrupt download. / " +msg.url)
					case e:UnknownHostException => log.info("host unknown / " +msg.url)
					case e:Exception => e.printStackTrace(); println(msg.url)
				}
	}
	def isFile(url:String): Boolean =
		url.contains(".pdf") ||
		url.contains(".mkv") ||
		url.contains(".avi") ||
		url.contains(".jpg") ||
		url.contains(".jpeg")||
		url.contains(".mp3") ||
		url.contains(".ppt") ||
		url.contains(".doc") ||
		url.contains(".zip") ||
		url.contains(".rar") ||
		url.contains(".xml") ||
		url.contains(".js")  ||
		url.contains(".exe")
}