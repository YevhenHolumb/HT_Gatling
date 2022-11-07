package ChallengeFlood;

import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class RunTestChallengeFlood extends Simulation {

    private static int t_min = 1;
    private static int t_max = 2;
    {
        HttpProtocolBuilder httpProtocol = http
                .baseUrl("https://challenge.flood.io")
                .inferHtmlResources(AllowList(), DenyList("", ".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
                .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                .acceptEncodingHeader("gzip, deflate")
                .acceptLanguageHeader("en-GB,en;q=0.5")
                .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0");

        Map<CharSequence, String> headers_0 = new HashMap<>();
        headers_0.put("Sec-Fetch-Dest", "document");
        headers_0.put("Sec-Fetch-Mode", "navigate");
        headers_0.put("Sec-Fetch-Site", "none");
        headers_0.put("Sec-Fetch-User", "?1");
        headers_0.put("Upgrade-Insecure-Requests", "1");

        Map<CharSequence, String> headers_1 = new HashMap<>();
        headers_1.put("Origin", "https://challenge.flood.io");
        headers_1.put("Sec-Fetch-Dest", "document");
        headers_1.put("Sec-Fetch-Mode", "navigate");
        headers_1.put("Sec-Fetch-Site", "same-origin");
        headers_1.put("Sec-Fetch-User", "?1");
        headers_1.put("Upgrade-Insecure-Requests", "1");

        Map<CharSequence, String> headers_5 = new HashMap<>();
        headers_5.put("Accept", "*/*");
        headers_5.put("Sec-Fetch-Dest", "empty");
        headers_5.put("Sec-Fetch-Mode", "cors");
        headers_5.put("Sec-Fetch-Site", "same-origin");
        headers_5.put("X-Requested-With", "XMLHttpRequest");

        Map<CharSequence, String> headers_7 = new HashMap<>();
        headers_7.put("Accept", "text/html, application/xhtml+xml");
        headers_7.put("Sec-Fetch-Dest", "empty");
        headers_7.put("Sec-Fetch-Mode", "cors");
        headers_7.put("Sec-Fetch-Site", "same-origin");
        headers_7.put("Turbolinks-Referrer", "https://challenge.flood.io/done");


        ScenarioBuilder scn = scenario("RunTestChallengeFlood")
                .exec(
                        http("GET Home Page")
                                .get("/")
                                .headers(headers_0)
                                .check(status().is(200))
                                .check(css("input[name='authenticity_token']","value").saveAs("myToken"))
                                .check(css("#challenger_step_id","value").saveAs("myStepId"))
                                .check(css("#challenger_step_number","value").saveAs("myStepNumber"))


                )
                .pause(t_min,t_max)
                .exec(
                        http("POST Start Button")
                                .post("/start")
                                .headers(headers_1)
                                .formParam("utf8", "✓")
                                .formParam("authenticity_token", "#{myToken}")
                                .formParam("challenger[step_id]", "#{myStepId}")
                                .formParam("challenger[step_number]", "#{myStepNumber}")
                                .formParam("commit", "Start")
                                .check(status().is(200))
                                .check(css("#challenger_step_id","value").saveAs("myStepId"))
                                .check(css("#challenger_step_number","value").saveAs("myStepNumber"))
                )
                .pause(t_min,t_max)
                .exec(
                        http("POST Enter Your Age")
                                .post("/start")
                                .headers(headers_1)
                                .formParam("utf8", "✓")
                                .formParam("authenticity_token", "#{myToken}")
                                .formParam("challenger[step_id]", "#{myStepId}")
                                .formParam("challenger[step_number]", "#{myStepNumber}")
                                .formParam("challenger[age]", Integer.toString(new Random().nextInt((100 - 18) + 1) + 18))
                                .formParam("commit", "Next")
                                .check(status().is(302))
                                .check(css("#challenger_step_id","value").saveAs("myStepId"))
                                .check(css("#challenger_step_number","value").saveAs("myStepNumber"))

                                .check(css("input[name='challenger[largest_order]']","value").saveAs("myToken"))

                )
      .pause(t_min,t_max)
      .exec(
        http("POST Select And Enter Order Value")
          .post("/start")
          .headers(headers_1)
          .formParam("utf8", "✓")
          .formParam("authenticity_token", "#{myToken}")
          .formParam("challenger[step_id]", "#{myStepId}")
          .formParam("challenger[step_number]", "#{myStepNumber}")
          .formParam("challenger[largest_order]", "224")
          .formParam("challenger[order_selected]", "NG1TQklFblh2MXhaUC9zclprUWNEQT09LS1ycGFjT21uY3hmQUh1aTBZTEVya2hRPT0=--7d6d10068a2ef572d137e510f3dadeabf72e2d4f")
          .formParam("commit", "Next")
                .check(status().is(302))
                .check(css("#challenger_step_id","value").saveAs("myStepId"))
                .check(css("#challenger_step_number","value").saveAs("myStepNumber"))
      )
      .pause(t_min,t_max)
      .exec(
        http("POST Just Press Next Button")
          .post("/start")
          .headers(headers_1)
          .formParam("utf8", "✓")
          .formParam("authenticity_token", "#{myToken}")
          .formParam("challenger[step_id]", "#{myStepId}")
          .formParam("challenger[step_number]", "#{myStepNumber}")
          .formParam("challenger[order_7]", "1667753508")
          .formParam("challenger[order_4]", "1667753508")
          .formParam("challenger[order_4]", "1667753508")
          .formParam("challenger[order_11]", "1667753508")
          .formParam("challenger[order_12]", "1667753508")
          .formParam("challenger[order_7]", "1667753508")
          .formParam("challenger[order_6]", "1667753508")
          .formParam("challenger[order_8]", "1667753508")
          .formParam("challenger[order_15]", "1667753508")
          .formParam("challenger[order_17]", "1667753508")
          .formParam("commit", "Next")
          .resources(
            http("request_5")
              .get("/code")
              .headers(headers_5)
          )
                .check(status().is(302))
                .check(css("#challenger_step_id","value").saveAs("myStepId"))
                .check(css("#challenger_step_number","value").saveAs("myStepNumber"))
      )
      .pause(t_min,t_max)
      .exec(
        http("POST Enter One Time Token")
          .post("/start")
          .headers(headers_1)
          .formParam("utf8", "✓")
          .formParam("authenticity_token", "#{myToken}")
          .formParam("challenger[step_id]", "#{myStepId}")
          .formParam("challenger[step_number]", "#{myStepNumber}")
          .formParam("challenger[one_time_token]", "2765432110")
          .formParam("commit", "Next")
                .check(status().is(302))
      )
      .pause(t_min,t_max)
      .exec(
        http("GET Press Start Again Button")
          .get("/")
          .headers(headers_7)
                .check(status().is(200))
      ) ;


//        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);

          {
            setUp(
                    scn.injectOpen(rampUsers(5).during(60))
            ).protocols(httpProtocol);
        }
    }
}
