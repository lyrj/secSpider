package com.kingdee.safe.spiders

/**
 * Author   : xiaogo
 * Date     : 2015-04-10 18:38
 * Copyright: Kingdee Co.,Ltd
 */
//URL http://
object UrlExtractor {
	def extractParam(url:String)= {
		if(url.contains("?")) {
			val params = url.split('?')(1)
			val pa = params.split('&')
			val v1:Array[String] = pa.map((x:String)=>x.split('=')(0))

			val v2:Array[Any] = pa.map((x:String)=>{
				if(x.split('=').length==2)
					x.split('=')(1)
				else
					None
			})
			val kv2 = (0 to v1.length -1).map((i)=> (v1(i) , v2(i))).toList.toMap
			(url.split('?')(0),kv2)
		}
		else
			(url,Map[Any,Any]())
	}

}
