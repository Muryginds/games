package my.company.game;

import java.io.Serializable;
import lombok.Data;

@Data
public class Combo implements Serializable {
  private Item object;
  private Inventory destInventory;
  private Item subject;
  private Inventory targetInventory;
  private Item result;
  private String message;

  public Combo(Item object, Inventory destInventory, Item subject,
      Inventory targetInventory, Item result, String message) {
    this.object = object;
    this.destInventory = destInventory;
    this.subject = subject;
    this.targetInventory = targetInventory;
    this.result = result;
    this.message = message;
  }
}