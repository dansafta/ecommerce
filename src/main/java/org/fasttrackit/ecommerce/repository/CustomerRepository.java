package org.fasttrackit.ecommerce.repository;

import org.fasttrackit.ecommerce.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
