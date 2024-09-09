package prodApi;


import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ProdApi extends Simulation {

  private final HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://66da59bbf47a05d55be4998e.mockapi.io")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Mobile Safari/537.36");



  private final ScenarioBuilder scn = scenario("ProdApi")
          .exec(
                  http("All product")
                          .get("/test-vegeta/prod")
          ).pause(1)

          .exec(
                  http("Get product with id 5")
                          .get("/test-vegeta/prod/5")
          ).pause(1)

          .exec(
                  http("Add Product ")
                          .post("/test-vegeta/prod")
                          .body(StringBody("{ \"libelle\": \"Produit de Jordan\", \"prix\": 1000 }"))
                          .asJson() // Indiquer que le corps de la requête est du JSON
          ).pause(1)

          .exec(
                  http("Update Product ")
                          .put("/test-vegeta/prod/10")
                          .body(StringBody("{ \"libelle\": \"Product Updated\", \"prix\": 1500 }"))
                          .asJson() // Indiquer que le corps de la requête est du JSON
          ).pause(1);

  {
	  setUp(scn.injectOpen(rampUsers(10).during(20))).protocols(httpProtocol);
  }
}
