package dev.quantumly.litTransportNBAScraper;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
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
    // In theory I could write this to use not statements but I'm not sure how JSoup would handle it
    Document profileDocument = Jsoup.connect(getRedirectableSearch(String.join("+", player))).get();

    var Seasons = extractTableValues(profileDocument, "season");
    var ThreePA = extractTableValues(profileDocument, "fg3a_per_g");

    for (int i = 0; i < Seasons.length; i++) {
      System.out.println(Seasons[i] + " " + ThreePA[i]);
    }

    return 0;
  }

  private static String getRedirectableSearch(String profile) throws IOException {
    Connection initialPlayerProfile = Jsoup.connect("https://www.basketball-reference.com/search/search.fcgi?search=" + profile);
    Response followedUrl = initialPlayerProfile.followRedirects(true).execute();

    if (followedUrl.url().toString().contains("?")) {
      Document searchDocument = Jsoup.connect("https://www.basketball-reference.com/search/search.fcgi?search=" + profile).get();
      return "https://www.basketball-reference.com" + searchDocument.select(".search-item-name").first().select("a").first().attr("href");
    } else return followedUrl.url().toString();
  }

  /**
   * Extracts the centre values from a specified "data-stat"
   * @param document The `Document` on which the selection should be performed
   * @param stat The "data-stat" property that should be checked
   * @return An array of entries with the total count and header removed
   */
  private static Object[] extractTableValues(Document document, String stat) {
    var Raw = document.select("[data-stat=" + stat + "]").eachText().toArray();
    return Arrays.copyOfRange(Raw, 1, Raw.length - 1);
  }
}
