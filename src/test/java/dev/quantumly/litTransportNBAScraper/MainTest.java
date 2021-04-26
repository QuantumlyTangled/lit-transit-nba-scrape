package dev.quantumly.litTransportNBAScraper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class MainTest {

  @Test
  public void testBasicNBAParametersPassing() {
    String[] args = new String[] { "Luka", "Doncic" };
    Main main = new Main();
    new CommandLine(main).execute(args);

    String[] player = main.getPlayer();
    Assertions.assertArrayEquals(player, args);
  }

}
