package my.company;

import static org.assertj.core.api.Assertions.assertThat;

import jdk.jfr.Name;
import my.company.game.Game;
import my.company.game.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GameTests {
  private static Player player;
  private static Game game;

  @BeforeAll
  public static void prepareGame() {
    player = new Player();
    game = new Game(player);
  }

  @Test
  @Name("Проверка стартовой локации")
  public void checkStartLocation() throws Exception {
    String result = player.getLocation().getName();
    assertThat(result).isEqualTo("Red Hut's house");
  }

  @Test
  @Name("Проверка загрузки списка комбинаций")
  public void playerHasCombos() throws Exception {
    Boolean result = player.getCombos().size() == 13;
    assertThat(result).isTrue();
  }

  @Test
  @Name("Проверка стартового инвентаря")
  public void playerHasItem() throws Exception {
    String result = player.getInventory().find("Socks").getName();
    assertThat(result).isEqualTo("Socks");
  }
}
