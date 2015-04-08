package com.kingdee.safe.core.messages

/**
 * @Author   : xiaogo
 * @Date     : 2015-04-07 21:50
 * @Copyright: Skyworth Co.,Ltd
 */
case class Requests(url:String,method:String,params:String) {}

case class Response(requests: Requests,responseBody: ResponseBody) {}

case class ResponseBody(status:Int,content:String)
