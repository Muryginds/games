package my.company.utils;

public interface ColorText {

  //System.out.println(ANSI_GREEN_BACKGROUND + ANSI_RED + "This text has a green background and red text!" + ANSI_RESET);

  String ANSI_RESET = "\u001B[0m";
  String ANSI_BLACK = "\u001B[30m";
  String ANSI_RED = "\u001B[31m";
  String ANSI_GREEN = "\u001B[32m";
  String ANSI_YELLOW = "\u001B[33m";
  String ANSI_BLUE = "\u001B[34m";
  String ANSI_PURPLE = "\u001B[35m";
  String ANSI_CYAN = "\u001B[36m";
  String ANSI_WHITE = "\u001B[37m";

  String ANSI_BLACK_BACKGROUND = "\u001B[40m";
  String ANSI_RED_BACKGROUND = "\u001B[41m";
  String ANSI_GREEN_BACKGROUND = "\u001B[42m";
  String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
  String ANSI_BLUE_BACKGROUND = "\u001B[44m";
  String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
  String ANSI_CYAN_BACKGROUND = "\u001B[46m";
  String ANSI_WHITE_BACKGROUND = "\u001B[47m";

  static String printHeader(String caption){
    return ANSI_PURPLE + "\t" + caption + ANSI_RESET;
  }

  static String colorCommands(String text) {
    return ANSI_BLUE + text + ANSI_RESET;
  }

  static String colorCyan(String text) {
    return ANSI_CYAN +  text + ANSI_RESET;
  }

  static String colorYellow(String text) {
    return ANSI_YELLOW +  text + ANSI_RESET;
  }
}
