package com.kingdee.safe.core.messages

/**
 * @author   : xiaogo
 * date     : 2015-04-07 21:50
 * copyright: Kingdee Co.,Ltd
 */
case class Requests(url:String,method:String = "GET",params:String = "") {}

case class Response(requests: Requests,responseBody: ResponseBody) {}

case class ResponseBody(status:Int, content:String) {}

case class Item(item:Map[Any,Any],name:String="default")
