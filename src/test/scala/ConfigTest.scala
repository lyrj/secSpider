import com.typesafe.config.ConfigFactory

/**
 * Author   : xiaogo
 * Date     : 2015-04-10 18:47
 * Copyright: Kingdee Co.,Ltd
 */
object ConfigTest {
	def main(args: Array[String]) {
		val config = ConfigFactory.parseFile(new java.io.File(System.getProperty("user.dir") + "/application.conf"))
		print(config.getString("test.test"))
//		print(System.getProperty("user.dir"))
	}
}
