package com.young.weixin

import java.io.FileInputStream

import com.young.java.util.xml.XMLUtils
import com.young.scala.weixin.entity.{WeixinAppInfo, WeixinConfig}

/**
 * Created by dell on 2016/1/20.
 */
trait BaseTest {

  val xml = new XMLUtils(Array(classOf[WeixinConfig],classOf[WeixinAppInfo]))

  val configFile = "E:\\project\\young\\scala\\young-server\\src\\main\\resources\\api-config.xml"

  val weixinConfig = xml.fromXml(new FileInputStream(configFile),classOf[WeixinConfig])
}
