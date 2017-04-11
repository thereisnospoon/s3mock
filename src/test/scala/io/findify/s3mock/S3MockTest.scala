package io.findify.s3mock

import better.files.File
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.S3Object
import io.findify.s3mock.provider.FileProvider
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.io.Source

/**
  * Created by shutty on 8/9/16.
  */
trait S3MockTest extends FlatSpec with Matchers with BeforeAndAfterAll {

  val s3 = AmazonS3ClientBuilder.standard()
    .withEndpointConfiguration(new EndpointConfiguration("http://127.0.0.1:8001", ""))
    .build()

  val workDir = File.newTemporaryDirectory().pathAsString
  val server = new S3Mock(8001, new FileProvider(workDir))

  override def beforeAll = {
    if (!File(workDir).exists) File(workDir).createDirectory()
    server.start
    super.beforeAll
  }
  override def afterAll = {
    super.afterAll
    server.stop
    File(workDir).delete()
  }

  def getContent(s3Object: S3Object): String = Source.fromInputStream(s3Object.getObjectContent, "UTF-8").mkString

}
