//> using dep org.http4s::http4s-blaze-client:1.0.0-M40,
//> using dep io.circe::circe-generic:0.14.6
//> using dep io.circe::circe-parser:0.14.6
//> using dep org.typelevel::cats-effect:3.5.4
//> using dep org.typelevel::log4cats-slf4j:2.6.0
//> using dep org.slf4j:slf4j-nop:2.0.12

import cats.effect._
import io.circe.generic.auto._
import io.circe.parser._
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.client.Client
import org.http4s.implicits._
import org.http4s.Uri
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory

object IPifyCLI extends IOApp with Http4sClientDsl[IO] {

  final case class IPifyResponse(ip: String)

  implicit val logging: LoggerFactory[IO] = Slf4jFactory.create[IO]

  override def run(args: List[String]): IO[ExitCode] = {
    val ipifyUrl = uri"https://api.ipify.org/?format=json"

    BlazeClientBuilder[IO].resource.use { client =>
      fetchIPAddress(ipifyUrl)(client).flatMap { ip =>
        IO(println(s"My IP address: $ip")) >> IO.pure(ExitCode.Success)
      }.handleErrorWith { e =>
        IO(println(s"Failed to fetch IP address: ${e.getMessage}")) >> IO.pure(ExitCode.Error)
      }
    }
  }

  def fetchIPAddress(uri: Uri)(implicit client: Client[IO]): IO[String] = {
    for {
      response <- client.expect[String](uri)
      ip <- IO.fromEither(decode[IPifyResponse](response)).map(_.ip)
    } yield ip
  }
}
