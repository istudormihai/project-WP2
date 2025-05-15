package org.example.openbid.services;

import org.example.openbid.domain.AppUser;
import org.example.openbid.domain.Item;
import org.example.openbid.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item addItem(Item item, AppUser owner) {
        item.setOwner(owner);
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getItemsByUser(AppUser user) {
        return itemRepository.findByOwner(user);
    }
}
