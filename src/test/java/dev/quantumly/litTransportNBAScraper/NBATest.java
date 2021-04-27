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
        "2018-19 7.1",
        "2019-20 8.9",
        "2020-21 8.3",
        ""
    }), systemOutRule.getLog());
  }

}
