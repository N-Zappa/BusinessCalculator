package com.application.businesscalc.Repository;

import com.application.businesscalc.Model.Item;
import org.springframework.data.repository.CrudRepository;
public interface ItemRepo extends CrudRepository<Item, Long> {

}
