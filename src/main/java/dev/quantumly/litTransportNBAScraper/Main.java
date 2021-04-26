package dev.quantumly.litTransportNBAScraper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.concurrent.Callable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "nba", mixinStandardHelpOptions = true, version = "1.0.0")
public class Main implements Callable<Integer> {

  @Parameters(paramLabel = "PLAYER") String[] player;

  public static void main(String[] args) {
    System.exit(new CommandLine(new Main()).execute(args));
  }

  @Override
  public Integer call() throws IOException, InterruptedException {
    String surnameIndex = String.valueOf(player[1].charAt(0)).toLowerCase();
    System.out.println(surnameIndex);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://www.basketball-reference.com/players/" + surnameIndex + "/"))
        .GET() // GET is default
        .build();

    HttpResponse<String> response = client.send(request,
        HttpResponse.BodyHandlers.ofString());

    Document doc = Jsoup.parse(response.body());

    System.out.println(response.body());
    return 0;
  }
}
