package org.example.openbid.services;

import org.example.openbid.domain.AppUser;
import org.example.openbid.domain.Item;

import java.util.List;

public interface ItemService {
    Item addItem(Item item, AppUser owner);
    List<Item> getItemsByUser(AppUser user);
}
