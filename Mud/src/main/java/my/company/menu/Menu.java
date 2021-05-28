package my.company.menu;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import my.company.utils.ColorText;

public abstract class Menu {

  protected Map<Integer, String> MENU_ITEM_LIST = new LinkedHashMap<>();

  public abstract void toggle();

  public abstract void exit();

  public abstract void menuItemPick(int number);

  public int countNonZeroMenuOptions() {
    return (int) MENU_ITEM_LIST.keySet().stream().filter(i -> i != 0).count();
  }

  public void printMenu(String caption){
    System.out.println(ColorText.printHeader(caption));
    for (Entry<Integer, String> entry : MENU_ITEM_LIST.entrySet()) {
      System.out.println(entry.getKey()+ ": " + ColorText.ANSI_GREEN + "["+ entry.getValue() + "]" + ColorText.ANSI_RESET);
    }
    System.out.println("Pick menu item:");
  }
}