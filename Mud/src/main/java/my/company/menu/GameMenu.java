package my.company.menu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import my.company.game.Game;
import my.company.game.Location;
import my.company.game.Player;
import my.company.game.SavedGame;
import my.company.game.enums.Direction;
import my.company.utils.ColorText;
import my.company.utils.UserResponse;

public class GameMenu extends Menu{

  private final Player player;
  private final Game game;

  {
    MENU_ITEM_LIST.put(1, "Player info");
    MENU_ITEM_LIST.put(2, "Inventory");
    MENU_ITEM_LIST.put(3, "Lookaround");
    MENU_ITEM_LIST.put(4, "Change location");
    MENU_ITEM_LIST.put(5, "Save game");
    MENU_ITEM_LIST.put(6, "Go to Main menu");
    MENU_ITEM_LIST.put(0, "Go back to world");
  }

  public GameMenu(Player player, Game game) {
    this.player = player;
    this.game = game;
  }

  @Override
  public void toggle() {
    printMenu("GAME MENU");
    int optionsNumber = countNonZeroMenuOptions();
    int response = UserResponse.getNumber(optionsNumber);
    menuItemPick(response);
  }

  @Override
  public void exit() {
    new MainMenu().toggle();
  }

  @Override
  public void menuItemPick(int number) {
    switch (number) {
      case 6:
        System.out.println("Are you sure you want to exit? yes or no");
        if (UserResponse.getYesNo()) {
          exit();
        } else {
          new UserResponse().getResponse(game);
        }
        break;
      case 1:
        System.out.println("Player's name: " + ColorText.colorYellow(player.getName()));
        new UserResponse().getResponse(game);
        break;
      case 2:
        player.inventory();
        new UserResponse().getResponse(game);
        break;
      case 3:
        player.lookAround();
        new UserResponse().getResponse(game);
        break;
      case 4:
        System.out.println();
        Location location = player.getLocation();
        var directionsList = new ArrayList<>(location.getDirections().entrySet());
        int directionsListSize = directionsList.size();
        for (int i = 0; i < directionsListSize; i++) {
          var entry = directionsList.get(i);
          System.out.println(i + " : " + ColorText.ANSI_CYAN_BACKGROUND +
              "["+ entry.getKey() + " : " + entry.getValue().getName() + "]"
              + ColorText.ANSI_RESET);
          }
        System.out.println("Select location:");
        int response = UserResponse.getNumber(directionsListSize - 1);
        Direction direction = directionsList.get(response).getKey();
        player.go(location.findNext(direction));
        new UserResponse().getResponse(game);
        break;
      case 5:
        SavedGame savedGame = new SavedGame(game);
        try (FileOutputStream outputStream = new FileOutputStream(MainMenu.SAVE_PATH);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
          objectOutputStream.writeObject(savedGame);
        } catch (IOException e) {
          UserResponse.ROOT_LOGGER.error(e);
        }
        break;
      case 0:
        new UserResponse().getResponse(game);
        break;
    }
  }
}
