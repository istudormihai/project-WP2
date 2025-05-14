package org.example.openbid.repositories;

import org.example.openbid.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {



}
