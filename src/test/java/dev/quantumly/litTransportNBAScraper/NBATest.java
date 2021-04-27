package dev.quantumly.litTransportNBAScraper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import picocli.CommandLine;

public class NBATest {

  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @Test
  public void testBasicNBAParametersPassing() {
    String[] args = new String[] { "Luka", "Doncic" };
    NBA nba = new NBA();
    new CommandLine(nba).execute(args);

    String[] player = nba.getPlayer();
    assertArrayEquals(args, player);
  }

  @Test
  public void testNBARun() {
    String[] args = new String[] { "Luka", "Doncic" };
    NBA nba = new NBA();
    new CommandLine(nba).execute(args);

    assertEquals(String.join(System.lineSeparator(), new String[] {
        "Season 2018-19 - 3PA 7.1",
        "Season 2019-20 - 3PA 8.9",
        "Season 2020-21 - 3PA 8.3",
        ""
    }), systemOutRule.getLog());
  }

  @Test
  public void testNBARunRedirect() {
    String[] args = new String[] { "Luc", "Mb" };
    NBA nba = new NBA();
    new CommandLine(nba).execute(args);

    assertEquals(String.join(System.lineSeparator(), new String[] {
        "Season 2008-09 - 3PA 0.0",
        "Season 2009-10 - 3PA 0.2",
        "Season 2010-11 - 3PA 0.1",
        "Season 2011-12 - 3PA 0.1",
        "Season 2012-13 - 3PA 0.6",
        "Season 2013-14 - 3PA 0.3",
        "Season 2013-14 - 3PA 0.3",
        "Season 2013-14 - 3PA 0.3",
        "Season 2014-15 - 3PA 3.0",
        "Season 2015-16 - 3PA 0.5",
        "Season 2016-17 - 3PA 1.4",
        "Season 2017-18 - 3PA 2.8",
        "Season 2018-19 - 3PA 1.5",
        "Season 2019-20 - 3PA 0.7",
        ""
    }), systemOutRule.getLog());
  }

}
