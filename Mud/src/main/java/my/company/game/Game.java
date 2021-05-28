package my.company.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Data;
import my.company.game.enums.Direction;
import my.company.game.enums.Moveable;
import my.company.menu.GameMenu;
import my.company.utils.ColorText;
import my.company.utils.UserResponse;

@Data
public class Game implements Serializable {

  private Player player;
  private transient GameMenu gameMenu;

  public Game (Player player) {
    this.player = player;
    this.gameMenu = new GameMenu(player, this);
    initialiseGame();
  }

  private void initialiseGame() {
    prepareLocations();
    UserResponse response = new UserResponse();
    response.getResponse(this);
  }

  private void prepareLocations() {
    List<Location> blockedLocationsList = new ArrayList<>();
    List<Item> items = new ArrayList<>();
    Item socks = new Item("Socks", "Smelly dirty socks", Moveable.MOBILE);
    items.add(socks);
    player.getInventory().addAll(items);
    items.clear();

    Location loc00 = new Location("Mushroom forest");
    loc00.setDescription("Forest. You become calmer while breathing air in it");
    Item mushroom = new Item("Mushroom", "Huge colored shining mushroom"
        + " that seems totally eatable. You can't take it with hands. Perhaps you can cut a piece", Moveable.STATIONARY);
    items.add(mushroom);
    loc00.getInventory().addAll(items);

    Location loc01 = new Location("Training field");
    loc01.setDescription("Abandoned training field");
    items.clear();
    Item dummy = new Item("Target dummy", "Old target dummy with a knife in it");
    items.add(dummy);
    items.add(new Item("Pointer","Pointer points at Grand Mother's"
        + " house located on the other side of the river" ));
    Item knife = new Item("Knife", "Rusty knife stucked at target dummy", Moveable.MOBILE);
    items.add(knife);
    loc01.getInventory().addAll(items);

    Location loc02 = new Location("GrandMa house");
    loc02.setDescription("Grand Mother's old wooden house with small garden");
    blockedLocationsList.add(loc02);
    items.clear();
    Item npcGrandMa = new Item("GrandMa", "Asks you to bring pie fillings(mushroom flavored meat) to cook pie");
    npcGrandMa.setNpc(true);
    items.add(npcGrandMa);
    loc02.getInventory().addAll(items);

    Location loc10 = new Location("Red Hut's house");
    loc10.setDescription("Big wooden house with backyard and garden");
    items.clear();
    Item npcMother = new Item("Mother", "Mother asked you to bring pies from Grand Mother");
    npcMother.setNpc(true);
    items.add(npcMother);
    Item doll = new Item("Doll", "Old doll for girls", Moveable.MOBILE);
    items.add(doll);
    Item spinner = new Item("Spinner", "Stupid toy for kids and degenerates", Moveable.MOBILE);
    items.add(spinner);
    loc10.getInventory().addAll(items);

    Location loc11 = new Location("Riverside");
    loc11.setDescription("The riverside of cold mountain river with small pier");
    items.clear();
    Item emptyBucket = new Item("Empty bucket", "Empty bucket with handle", Moveable.MOBILE);
    items.add(emptyBucket);
    Item pier = new Item("Pier", "An old pier witch is used to collect water and laundry");
    items.add(pier);
    loc11.getInventory().addAll(items);

    Location loc12 = new Location("Wolfs nest");
    loc12.setDescription("A strait road with wolfs nest nearby");
    items.clear();
    Item npcWolf = new Item("Wolf", "You see a strange wolf wearing old lady's dress."
        + " Wolfs wants to play and not letting you pass");
    npcWolf.setNpc(true);
    items.add(npcWolf);
    loc12.getInventory().addAll(items);

    Location loc20 = new Location("Potato field");
    loc20.setDescription("A field with planted potato. The field seems to be very dry. The rain was long time ago");
    items.clear();
    Item npcBugs = new Item("Angry bugs", "Bugs buzzing angrily not letting you pass. They look thirsty");
    npcBugs.setNpc(true);
    items.add(npcBugs);
    Item shovel = new Item("Shovel", "A shovel forgotten in the field", Moveable.MOBILE);
    items.add(shovel);
    loc20.getInventory().addAll(items);
    List<Item> blockedItems = new ArrayList<>();
    blockedItems.add(shovel);
    loc20.setBlockedItemsList(blockedItems);

    Location loc21 = new Location("Troll bridge");
    loc21.setDescription("The bridge over the fast river. It seems the only way to pass to the other riverside");
    items.clear();
    Item npcTroll = new Item("Troll", "Huge troll blocking the way. He looks very bored");
    npcTroll.setNpc(true);
    items.add(npcTroll);
    loc21.getInventory().addAll(items);

    Location loc22 = new Location("Rabbit meadow");
    loc22.setDescription("A sunny meadow full of flowers");
    blockedLocationsList.add(loc22);
    items.clear();
    Item npcRabbit = new Item("Rabbit", "Impudent rabbit giggling and teasing you."
        + " He jumps away when you come too close");
    npcRabbit.setNpc(true);
    items.add(npcRabbit);
    loc22.getInventory().addAll(items);

    Map<Integer, Combo> combos = new HashMap<>();
    Item mushroomPiece = new Item("Mushroom piece", "Mushroom piece with attractive smell", Moveable.MOBILE);
    combos.put(0, new Combo(knife, player.getInventory(), mushroom, loc00.getInventory(), mushroomPiece,
        ColorText.colorCyan(mushroomPiece.getName()) + " appeared in your inventory"));
    Item bucketWithWater = new Item("Bucket with water", "Bucket full of cold fresh water", Moveable.MOBILE);
    combos.put(1, new Combo(emptyBucket, player.getInventory(), pier, loc11.getInventory(), bucketWithWater,
        ColorText.colorCyan(bucketWithWater.getName()) + " appeared in your inventory"));
    Item dirtyWaterBucket = new Item("Dirty water bucket", "Bucket full of smelly and dirty water", Moveable.MOBILE);
    combos.put(2, new Combo(socks, player.getInventory(), bucketWithWater, player.getInventory(), dirtyWaterBucket,
        ColorText.colorCyan(dirtyWaterBucket.getName()) + " appeared in your inventory"));
    combos.put(3, new Combo(bucketWithWater, player.getInventory(), npcBugs, loc20.getInventory(), emptyBucket,
        "Seems that nothing had changed. " + ColorText.colorCyan(emptyBucket.getName())
            + " appeared in your inventory"));
    combos.put(4, new Combo(dirtyWaterBucket, player.getInventory(), npcBugs, loc20.getInventory(), emptyBucket,
        "Bugs are terrified of the smell, they are running away. You can take what you want now. "
            + ColorText.colorCyan(emptyBucket.getName()) + " appeared in your inventory"));
    Item busyTroll = new Item("Busy troll", "Troll is rolling the spinner");
    busyTroll.setNpc(true);
    combos.put(5, new Combo(spinner, player.getInventory(), npcTroll, loc21.getInventory(), busyTroll,
        ColorText.colorCyan(busyTroll.getName()) +" starts playing with the toy, he does not pay"
            + " any attention to you, you can step on the bridge now"));
    Item smashedRabbit = new Item("Smashed rabbit", "Rabbit is like a beefsteak", Moveable.MOBILE);
    combos.put(6, new Combo(shovel, player.getInventory(), npcRabbit, loc22.getInventory(), smashedRabbit,
        "You smashed rabbit with shovel as hard as you can. After that you pick up "
            + ColorText.colorCyan(smashedRabbit.getName()) + " to your inventory"));
    Item freshMeat = new Item("Fresh meat", "Nice fresh rabbit meat", Moveable.MOBILE);
    combos.put(7, new Combo(knife, player.getInventory(), smashedRabbit, player.getInventory(), freshMeat,
        "You make a fresh meat of rabbit's corpse. " + ColorText.colorCyan(freshMeat.getName())
            + " appears in your inventory"));
    Item npcCuteWolf = new Item("Cute wolf", "Wolf is playing with a doll");
    npcCuteWolf.setNpc(true);
    combos.put(8, new Combo(doll, player.getInventory(), npcWolf, loc12.getInventory(), npcCuteWolf,
        ColorText.colorCyan(npcCuteWolf.getName()) + " is very happy playing with doll."
            + " He no longer blocks the road"));
    Item flavouredMeat = new Item("Mushroom flavoured meat","Meat mixed with mushrooms",
        Moveable.MOBILE);
    combos.put(9, new Combo(freshMeat, player.getInventory(), mushroomPiece, player.getInventory(), flavouredMeat,
        ColorText.colorCyan(flavouredMeat.getName()) + " appeared in your inventory. This is a nice pile filling"));
    combos.put(12, new Combo(mushroomPiece, player.getInventory(), freshMeat, player.getInventory(), flavouredMeat,
        ColorText.colorCyan(flavouredMeat.getName()) + " appeared in your inventory. This is a nice pile filling"));
    Item pie = new Item("Pie", "Hot pie with meat", Moveable.MOBILE);
    combos.put(10, new Combo(flavouredMeat, player.getInventory(), npcGrandMa, loc02.getInventory(), pie,
        ColorText.colorCyan(npcGrandMa.getName()) + " cooked a nice " + ColorText.colorCyan(pie.getName())
            +" for you and your mother. "+ ColorText.colorCyan(pie.getName()) + " appeared in your inventory"));
    combos.put(11, new Combo(pie, player.getInventory(), npcMother, loc10.getInventory(), socks,
        "You completed task! Congratulations! It's time to have some tea with pie!"));

    Map<Direction, Location> map00 = new TreeMap<>();
    map00.put(Direction.SOUTH, loc10);
    map00.put(Direction.EAST, loc01);
    loc00.setDirections(map00);

    Map<Direction, Location> map10 = new TreeMap<>();
    map10.put(Direction.NORTH, loc00);
    map10.put(Direction.SOUTH, loc20);
    map10.put(Direction.EAST, loc11);
    loc10.setDirections(map10);

    Map<Direction, Location> map20 = new TreeMap<>();
    map20.put(Direction.NORTH, loc10);
    map20.put(Direction.EAST, loc21);
    loc20.setDirections(map20);

    Map<Direction, Location> map01 = new TreeMap<>();
    map01.put(Direction.SOUTH, loc11);
    map01.put(Direction.WEST, loc00);
    loc01.setDirections(map01);

    Map<Direction, Location> map11 = new TreeMap<>();
    map11.put(Direction.NORTH, loc01);
    map11.put(Direction.SOUTH, loc21);
    map11.put(Direction.WEST, loc10);
    loc11.setDirections(map11);

    Map<Direction, Location> map21 = new TreeMap<>();
    map21.put(Direction.NORTH, loc11);
    map21.put(Direction.WEST, loc20);
    map21.put(Direction.EAST, loc22);
    loc21.setDirections(map21);

    Map<Direction, Location> map22 = new TreeMap<>();
    map22.put(Direction.NORTH, loc12);
    map22.put(Direction.WEST, loc21);
    loc22.setDirections(map22);

    Map<Direction, Location> map12 = new TreeMap<>();
    map12.put(Direction.NORTH, loc02);
    map12.put(Direction.SOUTH, loc22);
    loc12.setDirections(map12);

    Map<Direction, Location> map02 = new TreeMap<>();
    map02.put(Direction.SOUTH, loc12);
    loc02.setDirections(map02);

    player.setLocation(loc10);
    player.setCombos(combos);
    player.setBlockedLocationsList(blockedLocationsList);
  }
}
