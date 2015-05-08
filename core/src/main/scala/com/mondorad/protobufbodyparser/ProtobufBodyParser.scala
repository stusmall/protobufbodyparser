
package com.mondorad.protobufbodyparser

import com.google.protobuf.Message
import play.api.mvc.BodyParser

import scala.language.experimental.macros


object ProtobufBodyParser {

  def parser[T <: Message](): BodyParser[T] = macro ProtobufMacro.parserImpl[T]

  def parser[T <: Message](maxSize: Int): BodyParser[T] = macro ProtobufMacro.parserWMaxSizeImpl[T]


 // def parser1[T <: Message](): BodyParser[T] = play.api.mvc.BodyParsers.parse.raw
}

