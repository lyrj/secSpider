package com.kingdee.safe.core

import com.typesafe.config.{Config, ConfigFactory}

/**
 * Author   : xiaogo
 * Date     : 2015-04-13 10:56
 * Copyright: Kingdee Co.,Ltd
 */

object ReadingConfig {
	//initializing config
	private var config:Config = null
	var argList:List[String] = null
	def parseArgList() = {
		if(argList.length == 1) {
			config = ConfigFactory.parseFile(new java.io.File(System.getProperty("user.dir") +"/"+ argList(0)))
		}
		else
			throw new Exception("cannot parse arg.")
	}
	def apply () = {
		if (this.config eq null)
			this.parseArgList()
		config
	}
}
trait ReadingConfig
{
	val _conf = ReadingConfig()
	def conf:Config = _conf
}
