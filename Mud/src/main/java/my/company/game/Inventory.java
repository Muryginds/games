package my.company.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import my.company.utils.ColorText;


public class Inventory implements Serializable {

  private final Map<Item, Integer> items = new HashMap<>();

  public Map<Item, Integer> getItems() {
    return items;
  }

  public void add(Item item, int amount) {
    if (amount > 0 && item != null) {
      if (items.containsKey(item)) {
        items.put(item, items.get(item) + amount);
      } else
      {
        items.put(item, amount);
      }
    } else {
      System.out.println("Wrong amount or item");
    }
  }

  public void add(Item item) {
    add(item, 1);
  }

  public void addAll(List<Item> list) {
    for (Item item : list) {
      add(item);
    }
  }

  public boolean remove(Item item, int amount) {
    if (amount < 1 || item == null) {
      return false;
    }
    boolean result;
    if (items.containsKey(item)) {
      int currentAmount = items.get(item);
      if (currentAmount > amount) {
        items.put(item, currentAmount - amount);
        result = true;
      } else if (currentAmount == amount) {
        items.remove(item);
        result = true;
      } else {
        System.out.println("No such amount");
        result = false;
      }
    } else {
      System.out.println("No such item");
      result = false;
    }
    return result;
  }

  public boolean remove(Item item) {
    return remove(item, 1);
  }

  public void show() {
    System.out.println(ColorText.printHeader("INVENTORY:"));
    String result = items.entrySet().stream().map(i -> ColorText.colorCyan(i.getKey().getName())
        + " : " + i.getKey().getDescription() + "(" + i.getValue() + " pc.)").collect(Collectors.joining(",\n"));
    System.out.println(result);
  }

  public Item find(String itemName) throws NullPointerException {
    String name = itemName.toLowerCase(Locale.ROOT);
    return items.keySet().stream()
        .filter(i -> i.getName().toLowerCase(Locale.ROOT).equals(name))
        .findFirst().orElseThrow(() -> new NullPointerException("Item " + itemName + " not found"));
  }

  @Override
  public String toString() {
    return items.keySet().stream().map(i -> i.getDescription()
    + "(" + ColorText.colorCyan(i.getName()) + ")").collect(Collectors.joining(", "));
  }
}