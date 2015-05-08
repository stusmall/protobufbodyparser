package com.mondorad.protobufbodyparser

import com.mondorad.testserialization.Messages._
import play.api.http.Status
import play.api.libs.iteratee.Enumerator
import play.api.mvc.Result
import play.api.test._

object ProtobufBodyParserSpec extends PlaySpecification {

  def parseEmpty(contentType: Option[String], body: Array[Byte]): Either[Result, EmptyMsg] = {
    val request = FakeRequest("POST", "/x").withHeaders(contentType.map(CONTENT_TYPE -> _).toSeq: _*)
    val parser = ProtobufBodyParser.parser[EmptyMsg]()
    val i = parser(request)
    await(Enumerator(body) |>>> i)
  }

  def parseMessage1(contentType: Option[String], body: Array[Byte]): Either[Result, Message1] = {
    val request = FakeRequest("POST", "/x").withHeaders(contentType.map(CONTENT_TYPE -> _).toSeq: _*)
    val parser = ProtobufBodyParser.parser[Message1]()
    val i = parser(request)
    await(Enumerator(body) |>>> i)
  }

  def parseAm(contentType: Option[String], body: Array[Byte]): Either[Result, AggregateMessage] = {
    val request = FakeRequest("POST", "/x").withHeaders(contentType.map(CONTENT_TYPE -> _).toSeq: _*)
    val parser = ProtobufBodyParser.parser[AggregateMessage]()
    val i = parser(request)
    await(Enumerator(body) |>>> i)
  }

  def buildEmptyMesg: EmptyMsg = {
    EmptyMsg.newBuilder().build()
  }

  def buildMsg1(p: Int): Message1 = {
    Message1.newBuilder().setV(p).build()
  }

  def buildMsg2(p: Int): Message2 = {
    Message2.newBuilder().setV(p).build()
  }

  def buildMsg3(p: Int): Message3 = {
    Message3.newBuilder().setV(p).build()
  }

  def buildAggregateMessage(p1: Int, p2: Int, p3: Int): AggregateMessage = {
    AggregateMessage.newBuilder()
      .setM1(buildMsg1(p1))
      .setM2(buildMsg2(p2))
      .setM3(buildMsg3(p3)).build()
  }

  "The protobuf body parser" should {
    "parse an empty message" in {
      val result = parseEmpty(None, buildEmptyMesg.toByteArray)
      result.isRight mustEqual true
    }

    "parse a simple message" in {
      val result = parseMessage1(None, buildMsg1(23).toByteArray)
      result.right.get.getV mustEqual 23
    }

    "parse an aggregated message" in {
      val am = buildAggregateMessage(34, 4956, 3)
      val result = parseAm(None, am.toByteArray)
      val parsedAM = result.right.get
      am.getM1.getV mustEqual 34
      am.getM2.getV mustEqual 4956
      am.getM3.getV mustEqual 3
    }

    "A bad request should return 400" in {
      val ba = Array.fill[Byte](40)(0xff.asInstanceOf[Byte])
      val result = parseAm(None, ba)
      result.left.get.header.status mustEqual Status.BAD_REQUEST
      true mustEqual true
    }
  }
}
