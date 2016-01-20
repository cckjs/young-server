package com.young.scala.weixin.api

import com.young.java.util.http.HttpUtils

/**
 * Created by dell on 2016/1/20.
 */
abstract class BaseApi extends Api{

  val http = new HttpUtils()

}
