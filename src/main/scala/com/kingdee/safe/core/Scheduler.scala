package com.kingdee.safe.core

import akka.actor.{Deploy, Actor, Props}
import akka.routing.RoundRobinRouter
import com.kingdee.safe.core.messages.{Response, Requests}
import scala.collection.mutable
import scala.collection.mutable.HashSet

/**
 * Author   : xiaogo
 * Date     : 2015-04-08 10:01
 * Copyright: Kingdee Co.,Ltd
 */

class Scheduler(starts_urls:List[String],
                allowed_domain:List[String],
                use_spider:String) extends Actor{
	var urls:List[String] = starts_urls

	val downloader = context.actorOf(Props[Downloader]
		.withRouter(RoundRobinRouter(nrOfInstances = 10)),"downloader")

	val spiderClazz = Class.forName("com.kingdee.safe.spiders."+use_spider)
	val spiders = context.actorOf(new Props(new Deploy,spiderClazz,Nil)
		.withRouter(RoundRobinRouter(nrOfInstances = 10)) )

	println("Scheduler %s has been initialized.".format(self))

	for (i<- urls)
		//Send First Download Task.
		downloader ! Requests(i,"GET","")


	def receive = {
		//Dispatch the URL Download Requests.
		case Requests(url,method,params) =>
			if(filter(url)) {
				downloader ! Requests(url,method,params)
			}

		case Response(req,resp) =>
			spiders ! Response(req,resp)
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
