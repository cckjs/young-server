package com.young.scala.weixin.api

import com.young.java.util.http.HttpUtils
import com.young.java.util.json.JsonUtils
import com.young.java.util.xml.XMLUtils
import com.young.scala.weixin.entity.{WeixinAppInfo, WeixinConfig, ErrorMessage, WeixinResponse}
import com.young.scala.weixin.exception.WeixinException
import org.apache.commons.logging.LogFactory

/**
 * Created by dell on 2016/1/20.
 */
trait Api {

  var log = LogFactory.getLog(classOf[Api])
  @throws(classOf[WeixinException])
  def parserJson[T <: ErrorMessage](response: WeixinResponse, clazz: Class[T]): T = {
    if (response.getStateCode != 200) {
      throw new WeixinException("接口调用失败,cause:[" + response.getMessage + "]", null)
    }
    try {
      val t: T = JsonUtils.getObject(response.getContent, clazz)
      if (t.errcode != 0) {
        throw new WeixinException("接口返回错误,请检查参数,content:[" + t + "]", null)
      }
      t
    } catch {
      case e: Exception => throw new WeixinException("接口调用失败,content:[" + response.getContent + "]", e)
    }
  }
}
