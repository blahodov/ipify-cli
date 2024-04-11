import cats.effect._
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxApplicativeId
import org.http4s._
import org.http4s.client.Client
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IPifyCLISpec extends AnyFlatSpec with Matchers {

  private val testIP = "1.2.3.4"
  private val mockResponse = s"""{"ip":"$testIP"}"""

  private val testUri = Uri.unsafeFromString("http://test.com")

  private def mockClient(response: String): Client[IO] = Client.fromHttpApp(
    HttpApp[IO] { _ =>
      Response[IO](Status.Ok).withEntity(response).pure[IO]
    }
  )

  "fetchIPAddress" should "return the IP address from the response" in {
    val testClient = mockClient(mockResponse)

    val result = IPifyCLI.fetchIPAddress(testUri)(testClient).unsafeRunSync()

    result shouldBe testIP
  }

  it should "return an error message when the response does not contain an IP address" in {
    val testClient = mockClient(s"""{"Id":"42"}""".stripMargin)

    val result = IPifyCLI.fetchIPAddress(testUri)(testClient).attempt.unsafeRunSync()

    result.isLeft shouldBe true
  }

  it should "return an error message on invalid JSON" in {
    val testClient = mockClient("invalid json")

    val result = IPifyCLI.fetchIPAddress(testUri)(testClient).attempt.unsafeRunSync()

    result.isLeft shouldBe true
  }

}
