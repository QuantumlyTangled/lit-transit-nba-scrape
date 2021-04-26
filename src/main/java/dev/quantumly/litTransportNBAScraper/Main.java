package dev.quantumly.litTransportNBAScraper;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "nba", mixinStandardHelpOptions = true, version = "1.0.0")
public class Main implements Callable<Integer> {
  public static void main(String[] args) {
    System.exit(new CommandLine(new Main()).execute(args));
  }

  @Override
  public Integer call() {
    System.out.println("Hi!");

    return 0;
  }
}
