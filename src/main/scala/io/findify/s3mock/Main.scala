package io.findify.s3mock

import better.files.File
import com.typesafe.config.ConfigFactory
import io.findify.s3mock.provider.FileProvider

object Main extends App {

  val config = ConfigFactory.load().getConfig("s3mock")
  val port = config.getInt("port")
  val host = config.getString("host")

  val server = new S3Mock(host, port, new FileProvider(File.newTemporaryDirectory(prefix = "s3mock").pathAsString))
  server.start
}
