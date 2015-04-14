package com.kingdee.safe.spiders

/**
 * Author   : xiaogo
 * Date     : 2015-04-10 18:38
 * Copyright: Kingdee Co.,Ltd
 */
//URL http://
object UrlExtractor {
	def extract(url:String)= {
		if(url.contains("?")) {
			val params = url.split('?')(1)
			val pa = params.split('&')
			val v1:Array[String] = pa.map((x:String)=>x.split('=')(0))
			val v2:Array[String] = pa.map((x:String)=>x.split('=')(1))
			(url.split('?')(0),v1->v2)
		}
		else
			Nil
	}
	def ## = {

	}

}
