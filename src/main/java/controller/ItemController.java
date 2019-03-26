package controller;

import model.Item;
import service.ItemService;

import java.util.List;

public class ItemController {
    //private ItemSer
    private ItemService itemService = new ItemService();


    public Item add(Item item) {
        return itemService.add(item);
    }

    public Item delete(long id) {
        return itemService.delete(id);
    }

    public Item update(Item item) {

        return itemService.update(item);
    }

    public Item findById(long id) {
        return itemService.findById(id);
    }

    public List<Item> itemsList() {
        return itemService.itemsList();
    }

    public Item findByName(String name) {
        return itemService.findByName(name);
    }


}
