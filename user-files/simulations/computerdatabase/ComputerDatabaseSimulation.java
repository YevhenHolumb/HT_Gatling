package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This sample is based on our official tutorials:
 * <ul>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/quickstart">Gatling quickstart tutorial</a>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/advanced">Gatling advanced tutorial</a>
 * </ul>
 */
public class ComputerDatabaseSimulation extends Simulation {

    FeederBuilder.Batchable<String> feeder =
            csv("search.csv").random();// 1, 2
    ChainBuilder search = exec(http("Home")
            .get("/"))
            .pause(1)
            .feed(feeder) // 3
            .exec(http("Search")
                    .get("/computers?f=#{searchCriterion}") // 4
                    .check(
                            css("a:contains('#{searchComputerName}')", "href")
                                    .saveAs("computerUrl") // 5
                    )
            )
            .pause(1)
            .exec(http("Select")
                    .get("#{computerUrl}")) // 6
            .pause(1);


    // Repeat is a loop resolved at RUNTIME
    ChainBuilder browse =
        // Note how we force the counter name, so we can reuse it
            repeat(5, "n").on( // 1
                    exec(http("Page #{n}").get("/computers?p=#{n}")) // 2
                            .pause(1)
            );

    ChainBuilder edit =
            exec(http("Form").get("/computers/new"))
                    .pause(1)
                    .exec(http("Post")
                            .post("/computers")
                            .check(status().is(session ->
                                    200 + java.util.concurrent.ThreadLocalRandom.current().nextInt(2) // 2
                            ))
                    );
/*    ChainBuilder tryMaxEdit = tryMax(2).on( // 1
            exec(edit)
    ).exitHereIfFailed(); // 2*/

    HttpProtocolBuilder httpProtocol =
        http.baseUrl("https://computer-database.gatling.io")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
            );


    ScenarioBuilder users = scenario("Users").exec(search, browse);
    ScenarioBuilder admins = scenario("Admins").exec(search, browse, edit);
    {
        setUp(
                users.injectOpen(rampUsers(10).during(10)),
                admins.injectOpen(rampUsers(2).during(10))
        ).protocols(httpProtocol);
    }

}
