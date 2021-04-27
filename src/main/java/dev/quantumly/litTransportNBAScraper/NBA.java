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
public class NBA implements Callable<Integer> {

  @Parameters(paramLabel = "PLAYER") String[] player;

  public static void main(String[] args) {
    System.exit(new CommandLine(new NBA()).execute(args));
  }

  @Override
  public Integer call() throws IOException, InterruptedException {
    // In theory I could write this to use not statements but I'm not sure how JSoup would handle it
    Document profileDocument = Jsoup.connect(getRedirectableSearch(String.join("+", player))).get();

    // After we load the profile into JSoup we look for "[data-stat="season"]" and "[data-stat="fg3a_per_g"]"
    // Once we select all elements with the property we remove the first entry and filter out any that contains "season" or "Career"
    var Seasons = extractTableValues(profileDocument, "season");
    var ThreePA = extractTableValues(profileDocument, "fg3a_per_g");

    for (int i = 0; i < Seasons.length; i++) {
      System.out.printf("Season %s - 3PA %s%n", Seasons[i], ThreePA[i]);
    }

    return 0;
  }

  public String[] getPlayer() {
    return player;
  }

  /**
   * Determines a profile page URL from a joined name
   * @param profile The search string from which to determine a profile URL
   * @return The final URL to use for scraping a profile page after a potential redirect has been executed
   */
  private static String getRedirectableSearch(String profile) throws IOException {
    Connection initialPlayerProfile = Jsoup.connect("https://www.basketball-reference.com/search/search.fcgi?search=" + profile);
    Response followedUrl = initialPlayerProfile.followRedirects(true).execute();

    // It's better to try and assume that we will be redirected since a potential call to the website is rather costly.
    // Both in time and in the risk of a potential IP blacklist.
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
    var RawFiltered = Arrays.stream(Raw).filter(e -> !(e.toString().contains("season") || e.toString().contains("Career"))).toArray();
    return Arrays.copyOfRange(RawFiltered, 1, RawFiltered.length);
  }
}
