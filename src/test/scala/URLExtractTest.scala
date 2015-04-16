import com.kingdee.safe.spiders.UrlExtractor

import scala.io.Source

/**
 * Author   : xiaogo
 * Date     : 2015-04-13 17:58
 * Copyright: Kingdee Co.,Ltd
 */
object URLExtractTest {
	def main(args: Array[String]) {
		val s = "http://y.baidu.com/songlist/wdaspl/?pst=shouyeGD_wdaspl&a=1&b=2"
		val fil = Source.fromFile("hello.txt")

		for (s<-fil.getLines()) {
			try {
				val r = UrlExtractor.extractParam(s)
				val row = r match {
					case (ripped_url:String,kvMap:Map[Any,Any]) =>
						println(s)
						println(ripped_url)
						for( (k,v)<- kvMap)
							println(k,v)
				}
			}catch{
				case e:Exception => e.printStackTrace()
			}
		}
	}
}
