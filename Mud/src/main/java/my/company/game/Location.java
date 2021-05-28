package my.company.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.Data;
import my.company.game.enums.Direction;
import my.company.game.enums.Moveable;
import my.company.utils.ColorText;

@Data
public class Location implements Serializable {
  private String name;
  private String description;
  private Inventory inventory = new Inventory();
  private Map<Direction,Location> directions;
  private List<Item> blockedItemsList = new ArrayList<>();

  public Location(String name){
    this.name = name;
  }

  public String getDescription() {
    StringBuilder result = new StringBuilder(ColorText.colorYellow(name)).append(" - ")
        .append(description).append("\n");
    var items = inventory.getItems();
    var npcList = items.keySet().stream().filter(Item::isNpc).map(i -> ColorText.colorCyan(i.getName())
        + " (" + i.getDescription() + ")").collect(Collectors.joining(",\n"));
    if (npcList.length() > 0) {
      result.append("Npc on location:\n");
      result.append(npcList);
      result.append("\n");
    }

    var itemList = items.keySet().stream().filter(item -> !item.isNpc()).map(i -> i.getDescription()
        + " (" + ColorText.colorCyan(i.getName()) + ")").collect(Collectors.joining(",\n"));
    if (itemList.length() > 0) {
      result.append("Items located nearby:\n");
      result.append(itemList);
    }
    return result.toString();
  }

  public Location findNext(String location) throws NullPointerException {

    String input = location.toUpperCase(Locale.ROOT);
    if (Arrays.stream(Direction.values()).anyMatch((t) -> t.name().equals(input))) {
      return findNext(Direction.valueOf(input));
    }

    return directions.values().stream()
        .filter(l ->l.getName().toUpperCase(Locale.ROOT).equals(input))
        .findFirst().orElseThrow(() -> new NullPointerException("Location " + location + " not found"));
  }


  public Location findNext(Direction direction) throws NullPointerException {
    return directions.entrySet().stream().filter(e -> e.getKey().equals(direction)).map(
        Entry::getValue).findFirst().orElseThrow(() -> new NullPointerException("Can't move this direction"));
  }

  public void putOn(Item item) {
    putOn(item, 1);
  }

  public void putOn(Item item, int amount) {
    inventory.add(item, amount);
  }

  public void pickUp(Item item, Inventory targetInv) {
    pickUp(item, 1, targetInv);
  }

  public void pickUp(Item item, int amount, Inventory targetInv) {
    if (item.isNpc() || blockedItemsList.contains(item)) {
      System.out.println("You can't reach that item");
      return;
    }
    if (item.getMoveable().equals(Moveable.MOBILE)) {
      inventory.remove(item, amount);
      targetInv.add(item, amount);
      System.out.println(ColorText.colorCyan(item.getName()) + " was picked up");
    } else {
      System.out.println("Item is not movable");
    }
  }

  public void printDirections(){
    directions.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue().getName()).forEach(System.out::println);
  }

  @Override
  public String toString(){
    return name + " - " + description;
  }
}