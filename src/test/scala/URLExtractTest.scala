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
				val r = UrlExtractor.extract(s)
				val row = r match {
					case Nil => s + '\n'
					case (ripped_url:String, params: Array[String],setters: Array[String]) =>
						ripped_url +"?"+ params.map(_ + "=#{p}").reduce(_ + "&" + _)
				}
				println("origin:" + s)
				println(row)
			}catch{
				case e:Exception => println("err: " + s)
			}
		}
	}
}
