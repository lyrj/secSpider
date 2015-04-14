package com.kingdee.safe.core

import akka.actor.{Deploy, Actor, Props}
import akka.routing.RoundRobinRouter
import com.kingdee.safe.core.messages.{Response, Requests}
import scala.collection.mutable
import scala.collection.JavaConversions._

/**
 * Author   : xiaogo
 * Date     : 2015-04-08 10:01
 * Copyright: Kingdee Co.,Ltd
 */

class Scheduler extends Actor with ReadingConfig{

	//******initialization of spiders and downloader    *******
	val urls:List[String] = conf.getStringList("secSpider.spider.start_urls").toList
	val spider_thread_cnt = conf.getInt("secSpider.spider.thread_count")
	val use_spider = conf.getString("secSpider.spider.use_spider")
	val downloader_thread_cnt = conf.getInt("secSpider.downloader.thread_count")
	val downloader = context.actorOf(Props[Downloader]
		.withRouter(RoundRobinRouter(nrOfInstances = downloader_thread_cnt)),"downloader")
	val spiderClazz = Class.forName("com.kingdee.safe.spiders."+use_spider)
	val spiders = context.actorOf(new Props(new Deploy,spiderClazz,Nil)
		.withRouter(RoundRobinRouter(nrOfInstances = spider_thread_cnt)) )

	println("Scheduler %s has been initialized.".format(self))

	for (i<- urls)
		//Send First Download Task.
		downloader ! Requests(i,"GET","")

	//******initialization of spiders and downloader    *******

	def receive = {
		//Dispatch the URL Download Requests.
		case msg:Requests =>
			if(filter(msg.url)) {
				downloader ! Requests(msg.url,msg.method,msg.params)
			}

		case msg:Response =>
			spiders ! Response(msg.requests,msg.responseBody)
	}


	//TODO : Use Bloom filter
	//URL Filter
	val url_set:mutable.HashSet[String] = mutable.HashSet()
	def filter(url:String):Boolean =  {
		if(url_set.contains(url))
			false
		else {
			url_set +=url
			true
		}
	}
}
