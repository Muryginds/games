package my.company.menu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import my.company.game.Game;
import my.company.game.SavedGame;
import my.company.utils.ColorText;
import my.company.utils.UserResponse;

public class MainMenu extends Menu {

  public static final String CONTROLS = ColorText.printHeader("CONTROLS:")
      + "\nCALL CONTROLS: "  + ColorText.colorCommands("controls") + " OR " + ColorText.colorCommands("help")
      + "\nCALL MENU: "  + ColorText.colorCommands("menu")
      + "\nCHANGE LOCATION: " + ColorText.colorCommands("go %direction") + " OR "
      + ColorText.colorCommands("go %location_name") + " OR PICK FROM MENU"
      + "\n(DIRECTIONS: "+ ColorText.colorCommands("north, south, west, east)")
      + "\nPICK AN ITEM: " + ColorText.colorCommands("pick %item_name")
      + "\nCALL INVENTORY: "  + ColorText.colorCommands("inventory") + " OR PICK FROM MENU"
      + "\nCOMBINING ITEMS: "  + ColorText.colorCommands("use %item1 on %item2")
      + "\nLOOKAROUND: "  + ColorText.colorCommands("lookaround") + " OR PICK FROM MENU"
      + "\nLOCATION INFO: "  + ColorText.colorCommands("location")
      + "\nSAVE: " + ColorText.colorCommands("save") + " OR PICK FROM MENU"
      + "\nCREDITS: " + ColorText.colorCommands("credits") + " OR PICK FROM MENU"
      + "\nEXIT GAME: " + ColorText.colorCommands("exit") + " OR PICK FROM MENU";

  public static final String CREDITS = ColorText.printHeader("CREDITS:")
      + "\n\nmuryginds@gmail.com"
      + "\ngithub.com/muryginds\n";

  public static final String SAVE_PATH = "saves/save.ser";

  {
    MENU_ITEM_LIST.put(1, "Start game");
    MENU_ITEM_LIST.put(2, "Load game");
    MENU_ITEM_LIST.put(3, "Controls");
    MENU_ITEM_LIST.put(4, "Credits");
    MENU_ITEM_LIST.put(0, "Exit game");
  }

  @Override
  public void toggle() {
    printMenu("MAIN MENU");
    int optionsNumber = countNonZeroMenuOptions();
    int response = UserResponse.getNumber(optionsNumber);
    menuItemPick(response);
  }

  @Override
  public void exit() {
    System.out.println("Exiting game");
    System.exit(0);
  }

  @Override
  public void menuItemPick(int number) {
    switch (number) {
      case 0:
        exit();
        break;
      case 1:
        new StartMenu(this).toggle();
        break;
      case 2:
        try(FileInputStream fileInputStream = new FileInputStream(SAVE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
          SavedGame savedGame = (SavedGame) objectInputStream.readObject();
          System.out.println(ColorText.printHeader("GAME LOADED") + " ("
              + savedGame.getGame().getPlayer().getName() + ")");
          Game game = savedGame.getGame();
          game.setGameMenu(new GameMenu(game.getPlayer(), game));
          System.out.println(game.getPlayer().getLocation().getDescription());
          new UserResponse().getResponse(game);
        } catch (IOException | ClassNotFoundException e) {
          UserResponse.ROOT_LOGGER.error(e);
        }
        break;
      case 3:
        System.out.println(CONTROLS);
        toggle();
        break;
      case 4:
        System.out.println(CREDITS);
        toggle();
        break;
    }
  }
}
