package my.company.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import my.company.utils.ColorText;

@Data
public class Player implements Serializable {

  private Location location;
  private Inventory inventory = new Inventory();
  private String name = "default";
  private Map<Integer, Combo> combos = new HashMap<>();
  private List<Location> blockedLocationsList = new ArrayList<>();

  public void setLocation(Location location) {
    this.location = location;
    System.out.println("You are at " + location.getDescription());
  }

  public void lookAround() {
    location.printDirections();
  }

  public void go(Location location) {
    if (!blockedLocationsList.contains(location)) {
      System.out.println(ColorText.ANSI_RED + name + ColorText.ANSI_RESET + " switches location ...");
      setLocation(location);
    } else {
      System.out.println("You don't have access to that location");
    }
  }

  public void use(String input1, String input2) {
    Item item1 = itemSearch(input1);
    Item item2 = itemSearch(input2);

    if (item1 != null || item2 != null) {
      var result = searchCombo(item1, item2);
      if (result.isPresent()) {
        makeCombo(result.get());
      } else {
        System.out.println("Can't combine items");
      }
    }
  }

  private void makeCombo(Entry<Integer, Combo> comboEntry) {
    Combo combo = comboEntry.getValue();
    switch (comboEntry.getKey()){
      case 0:
        combo.getDestInventory().add(combo.getResult());
        break;
      case 1:
      case 3:
      case 7:
        combo.getDestInventory().remove(combo.getObject());
        combo.getDestInventory().add(combo.getResult());
        break;
      case 2:
      case 9:
      case 12:
        combo.getDestInventory().remove(combo.getObject());
        combo.getDestInventory().add(combo.getResult());
        combo.getTargetInventory().remove(combo.getSubject());
        break;
      case 5:
        combo.getDestInventory().remove(combo.getObject());
        combo.getTargetInventory().remove(combo.getSubject());
        combo.getTargetInventory().add(combo.getResult());
        blockedLocationsList.removeAll(blockedLocationsList.stream()
            .filter(l1 -> l1.getName().equals("Rabbit meadow"))
            .collect(Collectors.toList()));
        break;
      case 4:
        combo.getDestInventory().remove(combo.getObject());
        combo.getDestInventory().add(combo.getResult());
        combo.getTargetInventory().remove(combo.getSubject());
        Item shovel = combo.getTargetInventory().find("shovel");
        List<Item> blockedList = location.getBlockedItemsList();
        blockedList.removeAll(blockedList.stream()
            .filter(i -> i.equals(shovel)).collect(Collectors.toList()));
        break;
      case 6:
      case 10:
        combo.getDestInventory().add(combo.getResult());
        combo.getTargetInventory().remove(combo.getSubject());
        break;
      case 8:
        combo.getDestInventory().remove(combo.getObject());
        combo.getTargetInventory().remove(combo.getSubject());
        combo.getTargetInventory().add(combo.getResult());
        blockedLocationsList.removeAll(blockedLocationsList.stream()
            .filter(l1 -> l1.getName().equals("GrandMa house"))
            .collect(Collectors.toList()));
        break;
      case 11:
        combo.getDestInventory().remove(combo.getObject());
        combo.getDestInventory().add(combo.getResult());
        System.out.println(combo.getMessage());
        System.exit(0);
    }
    System.out.println(combo.getMessage());
  }

  private Optional <Entry<Integer, Combo>> searchCombo(Item itemObject, Item itemSubject) {
    return combos.entrySet().stream().filter(i -> {
      Combo combo = i.getValue();
      return combo.getObject().equals(itemObject) && combo.getSubject().equals(itemSubject);
    }).findFirst();
  }

  private Item itemSearch(String string) {
    Item item = null;
    try {
      item = inventory.find(string);
    } catch (NullPointerException ex) {
      try {
        item = location.getInventory().find(string);
      } catch (NullPointerException ex1) {
        System.out.println("Wrong item name :" + string);
      }
    }
    return item;
  }

  public void inventory() {
    inventory.show();
  }
}