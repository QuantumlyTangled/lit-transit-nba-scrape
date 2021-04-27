package dev.quantumly.litTransportNBAScraper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class NBATest {

  @Test
  public void testBasicNBAParametersPassing() {
    String[] args = new String[] { "Luka", "Doncic" };
    NBA nba = new NBA();
    new CommandLine(nba).execute(args);

    String[] player = nba.getPlayer();
    Assertions.assertArrayEquals(player, args);
  }

}
