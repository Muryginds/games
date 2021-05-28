package my.company.game;

import my.company.menu.MainMenu;

public class Main {

  public static void main(String[] args) {
    System.out.println("Welcome to MUD");
    MainMenu mainMenu = new MainMenu();
    mainMenu.toggle();
  }
}