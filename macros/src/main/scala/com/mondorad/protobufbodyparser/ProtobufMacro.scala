package com.mondorad.protobufbodyparser

import play.api.mvc.BodyParser

import scala.reflect.macros.whitebox

object ProtobufMacro {

  def parserImpl[T: c.WeakTypeTag](c: whitebox.Context)(): c.Expr[BodyParser[T]] = {
    import c.universe._
    val T = weakTypeOf[T].companion
    c.Expr[BodyParser[T]](
      q"""play.api.mvc.BodyParser("protobuf"){request => {
          import scala.concurrent.ExecutionContext.Implicits.global
           play.api.mvc.BodyParsers.parse.raw(request).map {
              case Right(x) =>
                x.asBytes() match {
                  case Some(ba) =>
                    try {
                      Right($T.parseFrom(ba))
                    } catch {
                      case ex: com.google.protobuf.InvalidProtocolBufferException => Left(new play.api.mvc.Results.Status(play.api.http.Status.BAD_REQUEST))
                    }
                  case None => Left(new play.api.mvc.Results.Status(play.api.http.Status.BAD_REQUEST))
                }
              case Left(x) => Left(x)
            }(global)
          }}""")
  }

  def parserWMaxSizeImpl[T: c.WeakTypeTag](c: whitebox.Context)(maxSize: c.Expr[Int]): c.Expr[BodyParser[T]] = {
    import c.universe._
    val T = weakTypeOf[T].companion
    c.Expr[BodyParser[T]](
      q"""play.api.mvc.BodyParser("protobuf"){request => {
            import scala.concurrent.ExecutionContext.Implicits.global
           play.api.mvc.BodyParsers.parse.raw($maxSize)(request).map {
              case Right(x) =>
                x.asBytes() match {
                  case Some(ba) =>
                    try {
                      Right($T.parseFrom(ba))
                    } catch {
                      case ex: com.google.protobuf.InvalidProtocolBufferException => Left(new play.api.mvc.Results.Status(play.api.http.Status.BAD_REQUEST))
                    }
                  case None => Left(new play.api.mvc.Results.Status(play.api.http.Status.BAD_REQUEST))
                }
              case Left(x) => Left(x)
            }(global)
          }}""")
  }
}
