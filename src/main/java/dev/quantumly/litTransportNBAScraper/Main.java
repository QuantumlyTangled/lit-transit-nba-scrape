package dev.quantumly.litTransportNBAScraper;

import java.io.IOException;
import java.util.Arrays;
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
    Document searchDocument = Jsoup.connect("https://www.basketball-reference.com/search/search.fcgi?search=" + String.join("+", player)).get();
    String playerProfile = searchDocument.select(".search-item-name").first().select("a").first().attr("href");

    // In theory I could write this to use not statements but I'm not sure how JSoup would handle it
    Document profileDocument = Jsoup.connect("https://www.basketball-reference.com" + playerProfile).get();
    var SeasonsRaw = profileDocument.select("[data-stat=\"season\"]").eachText().toArray();
    var Seasons = Arrays.copyOfRange(SeasonsRaw, 1, SeasonsRaw.length - 1);

    var ThreePARaw = profileDocument.select("[data-stat=\"fg3a_per_g\"]").textNodes().toArray();
    var ThreePA = Arrays.copyOfRange(ThreePARaw, 1, ThreePARaw.length - 1);
    System.out.println(Seasons[0] + " " + ThreePA[0]);

    return 0;
  }
}
