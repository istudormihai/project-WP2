package org.example.openbid.repositories;

import org.example.openbid.domain.AppUser;
import org.example.openbid.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {


    List<Item> findByOwner(AppUser user);
}
