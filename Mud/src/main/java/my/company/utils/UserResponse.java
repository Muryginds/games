package my.company.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.Scanner;
import my.company.game.Game;
import my.company.game.Item;
import my.company.game.Location;
import my.company.game.Player;
import my.company.game.SavedGame;
import my.company.menu.MainMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserResponse {

  private static final Scanner SCANNER = new Scanner(System.in);
  public static final Logger ROOT_LOGGER = LogManager.getRootLogger();

  public void getResponse(Game game) {
    String input = SCANNER.nextLine().toLowerCase(Locale.ROOT);
    switch (input) {
      case "menu":
        game.getGameMenu().toggle();
        break;
      case "controls":
      case "help":
        System.out.println(MainMenu.CONTROLS);
        getResponse(game);
        break;
      case "save":
        SavedGame savedGame = new SavedGame(game);
        try (FileOutputStream outputStream = new FileOutputStream(MainMenu.SAVE_PATH);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
          objectOutputStream.writeObject(savedGame);
          System.out.println("Game saved successfully");
        } catch (IOException e) {
          ROOT_LOGGER.error("Saving error: " + e);
        }
        getResponse(game);
        break;
      case "inventory":
        game.getPlayer().inventory();
        getResponse(game);
        break;
      case "lookaround":
        game.getPlayer().lookAround();
        getResponse(game);
        break;
      case "credits":
        System.out.println(MainMenu.CREDITS);
        getResponse(game);
        break;
      case "location":
        System.out.println(game.getPlayer().getLocation().getDescription());
        getResponse(game);
        break;
      case "exit":
        System.out.println("Are you sure you want to exit? yes or no");
        if (getYesNo()) {
          game.getGameMenu().exit();
        } else {
          getResponse(game);
        }
        break;
      default:
        String[] result = input.split(" ", 2);
        Player player = game.getPlayer();
        Location currentLocation = player.getLocation();
        switch (result[0]) {
          case "go":
            try {
              Location location = currentLocation.findNext(result[1]);
              player.go(location);
            } catch (NullPointerException ex) {
              System.out.println(ex.getMessage());
              ROOT_LOGGER.error(ex);
            }
            getResponse(game);
            break;
          case "pick":
            try {
              Item item = currentLocation.getInventory().find(result[1]);
              currentLocation.pickUp(item, player.getInventory());
            } catch (NullPointerException ex) {
              System.out.println(ex.getMessage());
              ROOT_LOGGER.error(ex);
            }
            getResponse(game);
            break;
          case "use":
            String combo = result[1];
            String regex = "([A-zА-я|\\s]+) on ([A-zА-я|\\s]+)";
            String item1 = combo.replaceAll(regex, "$1");
            String item2 = combo.replaceAll(regex, "$2");
            player.use(item1, item2);
            getResponse(game);
            break;
          case "username":
            player.setName(result[1]);
            getResponse(game);
            break;
          default:
            System.out.println("Wrong input. Try again");
            ROOT_LOGGER.warn("Wrong input: " + input);
            getResponse(game);
            break;
        }
    }
  }

  public static int getNumber(int optionsNumber) {
    String input = SCANNER.nextLine();
    int result;
    try {
      result = Integer.parseInt(input);
      boolean correctInput = result >= 0 && result <= optionsNumber;
      if (!correctInput) {
        System.out.println("Wrong option number. Try again");
        result = getNumber(optionsNumber);
      }
    } catch (NumberFormatException ex) {
      System.out.println("Wrong input. Try again");
      result = getNumber(optionsNumber);
    }
    return result;
  }

  public static String getName() {
    String result = SCANNER.nextLine();
    if (result.equals("")) {
      System.out.println("Input non empty name");
      return getName();
    }
    return result;
  }

  public static boolean getYesNo() {
    String result = SCANNER.nextLine().toLowerCase(Locale.ROOT);
    if (result.equals("yes") ) {
      return true;
    } else if (result.equals("no")) {
      return false;
    }
    else {
      System.out.println("Input " + ColorText.colorCommands("yes") + " or "
          + ColorText.colorCommands("no"));
      return getYesNo();
    }
  }
}
