package com.young.scala.weixin.api

import com.young.java.util.http.HttpUtils
import com.young.java.util.json.JsonUtils
import com.young.scala.weixin.entity.WeixinResponse
import com.young.scala.weixin.exception.WeixinException

/**
 * Created by dell on 2016/1/20.
 */
trait BaseApi {

  val http = new HttpUtils()

  def parserJson[T](response: WeixinResponse): T = {
     if(response.getStateCode!=200){
       throw new WeixinException("接口调用失败,cause:["+response.getMessage+"]")
     }
     JsonUtils.getObject(response.getContent,classOf[T])
  }
}
