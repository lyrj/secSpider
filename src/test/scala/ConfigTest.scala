import com.kingdee.safe.core.OutputLine
import com.typesafe.config.ConfigFactory

/**
 * Author   : xiaogo
 * Date     : 2015-04-10 18:47
 * Copyright: Kingdee Co.,Ltd
 */
import scala.collection.JavaConversions._
object ConfigTest {
	def main(args: Array[String]) {
		val config = ConfigFactory.parseFile(new java.io.File(System.getProperty("user.dir") +"/"+ args(0)))
		val lis = config.getConfig("secSpider.pipeline.output_line")
		for(i<-lis.entrySet())
		{
			val s = lis.getString(i.getKey)
			println(s)
		}
//		print(System.getProperty("user.dir"))
	}
}
