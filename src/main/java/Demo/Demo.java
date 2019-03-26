package Demo;

import controller.ItemController;
import model.Item;

import java.util.Date;

public class Demo {

    public static void main(String[] args) throws Exception {

        ItemController itemController = new ItemController();

        Item item = new Item("Azov", new Date(), new Date(), "Время было такое");
        Item item2 = new Item("Bandera", new Date(), new Date(), "USA");
        Item item3 = new Item("Грабовский", new Date(), new Date(), "Ко-ка-ко-ла");

       // itemController.add(item);
       // itemController.add(item2);
       // itemController.add(item3);



        itemController.update(item2);

        System.out.println(itemController.itemsList());

    }

}
