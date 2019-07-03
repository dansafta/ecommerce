package org.fasttrackit.ecommerce.repository;

import org.fasttrackit.ecommerce.domain.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
