package com.young.scala.weixin.api

import com.young.java.util.http.HttpMethod
import com.young.scala.weixin.entity.{WeixinServer, WeixinConfig, AccessToken, GetAccessTokenParam}
import com.young.scala.weixin.exception.WeixinException

/**
 * Created by Administrator on 2016/1/19.
 */
class WeixinSenderApi extends BaseApi {

  @throws(classOf[WeixinException])
  def getAccessToken(getAccessTokenParam: GetAccessTokenParam): AccessToken = {
    val url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + getAccessTokenParam.grant_type + "&appid=" + getAccessTokenParam.appid + "&secret=" + getAccessTokenParam.secret
    val response = http.sendRequest(url, HttpMethod.GET, null)
    parserJson(response, classOf[AccessToken])
  }

  @throws(classOf[WeixinException])
  def getWeixinServerList(accessToken: AccessToken): WeixinServer = {
    val url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + accessToken.access_token
    val response = http.sendRequest(url, HttpMethod.GET, null)
    parserJson(response, classOf[WeixinServer])
  }
}
