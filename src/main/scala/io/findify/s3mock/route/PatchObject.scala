package io.findify.s3mock.route

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.Directives._
import io.findify.s3mock.provider.Provider

/**
  * Hack used to return content length of object without returning the object itself (akka http limitation)
  */
case class PatchObject(implicit val provider: Provider) {

  def route(bucket: String, key: String) = patch {
    val length = provider.getObjectLength(bucket, key)
    complete(HttpResponse(headers = List(RawHeader("ContentLength", length.toString))))
  }
}
