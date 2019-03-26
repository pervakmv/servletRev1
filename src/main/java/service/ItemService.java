package service;

import model.Item;
import repository.ItemDAO;

import java.util.List;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();


    public Item add(Item item) {
        return itemDAO.save(item);
    }

    public Item delete(long itemId) {
        Item item = itemDAO.findById(itemId);
        return itemDAO.delete(item);
    }

    public Item update(Item item)  {
        return itemDAO.update(item);
    }

    public Item findById(long id)  {
        return itemDAO.findById(id);
    }


    public List<Item> itemsList() {
        return itemDAO.itemsList();
    }

    public Item findByName(String name) {
        return itemDAO.findByName(name);
    }

}
