package my.company.menu;

import my.company.game.Game;
import my.company.game.Player;
import my.company.utils.UserResponse;

public class StartMenu extends Menu {

  private final MainMenu rootMenu;

  {
    MENU_ITEM_LIST.put(1, "Single player game");
    //MENU_ITEM_LIST.put(2, "Multi player game");
    MENU_ITEM_LIST.put(0, "Go back");
  }

  public StartMenu(MainMenu rootMenu) {
    this.rootMenu = rootMenu;
  }

  @Override
  public void toggle() {
    printMenu("START MENU");
    int optionsNumber = countNonZeroMenuOptions();
    int response = UserResponse.getNumber(optionsNumber);
    menuItemPick(response);
  }

  @Override
  public void exit() {
    rootMenu.toggle();
  }

  @Override
  public void menuItemPick(int number) {
    switch (number) {
      case 0:
        exit();
        break;
      case 1:
        Player newPlayer = new Player();
        System.out.println("Insert your name:");
        newPlayer.setName(UserResponse.getName());
        System.out.println("Starting the game...");
        System.out.println("You are a girl named Red Hut. Mother wakes you up early today, "
            + "she wants you to bring a fresh pie from Grand Mother.");
        new Game(newPlayer);
        break;
    }
  }
}
