package com.kingdee.safe.pipeline

import java.io.{File, PrintWriter}

import com.kingdee.safe.core.{ReadingConfig, OutputLine}

/**
 * Author   : xiaogo
 * Date     : 2015-04-15 10:27
 * Copyright: Kingdee Co.,Ltd
 */
class textFile extends OutputLine {
	//Implements of outputLine
	val fileName = conf.getString("secSpider.output_File")
	val writer = new PrintWriter(new File(fileName))
	override def write(data:Map[Any,Any]) = {
		writer.write(data.toString())
		writer.write("\n")
		writer.flush()
	}
}
