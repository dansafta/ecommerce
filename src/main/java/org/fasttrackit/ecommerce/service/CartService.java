package org.fasttrackit.ecommerce.service;

import org.fasttrackit.ecommerce.domain.Cart;
import org.fasttrackit.ecommerce.domain.Customer;
import org.fasttrackit.ecommerce.exception.ResourceNotFoundException;
import org.fasttrackit.ecommerce.repository.CartRepository;
import org.fasttrackit.ecommerce.transfer.cart.AddProductToCartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);


    private final CartRepository cartRepository;
    private final CustomerService customerService;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
    }
    @Transactional
    public void addProductToCart(AddProductToCartRequest request) throws ResourceNotFoundException {
        LOGGER.info("Saving cart {}", request);


        Customer customer = customerService.getCustomer(request.getCustometId());

        Cart cart = new Cart();
        cart.setCustomer(customer);

        cartRepository.save(cart);


    }}