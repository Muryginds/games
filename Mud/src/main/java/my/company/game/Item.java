package my.company.game;

import java.io.Serializable;
import lombok.Data;
import my.company.game.enums.Moveable;
import my.company.utils.ColorText;

@Data
public class Item implements Serializable {
  private String name;
  private String description;
  private Moveable moveable = Moveable.STATIONARY;
  private boolean isNpc = false;

  public Item (String name, String description, Moveable moveable) {
    this(name, description);
    this.moveable = moveable;
  }

  public Item (String name, String description) {
    this.name = name;
    this.description = description;
  }

  @Override
  public String toString() {
    return ColorText.ANSI_RED + name + ColorText.ANSI_RESET;
  }
}