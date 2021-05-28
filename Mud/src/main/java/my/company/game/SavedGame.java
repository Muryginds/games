package my.company.game;

import java.io.Serializable;

public class SavedGame implements Serializable {
  private static final long serialVersionUID = 1L;

  private Game game;

  public SavedGame (Game game) {
    this.game = game;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }
}