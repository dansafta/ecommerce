package org.fasttrackit.ecommerce;

import org.fasttrackit.ecommerce.domain.Product;
import org.fasttrackit.ecommerce.exception.ResourceNotFoundException;
import org.fasttrackit.ecommerce.service.ProductService;
import org.fasttrackit.ecommerce.transfer.CreateProductRequest;
import org.fasttrackit.ecommerce.transfer.UpdateProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

	@Autowired
	private ProductService productService;

	@Test
	public void testCreateProduct_whenValidRequest_thenReturnCreatedProduct() {
		createProduct();
	}

	private Product createProduct() {
		CreateProductRequest request = new CreateProductRequest();
		request.setName("Nivea");
		request.setPrice(99.95);
		request.setQuantity(10);

		Product createdProduct = productService.createProduct(request);

		assertThat(createdProduct, notNullValue());
		assertThat(createdProduct.getId(), greaterThan(0L));
		assertThat(createdProduct.getName(), is(request.getName()));
		assertThat(createdProduct.getPrice(), is(request.getPrice()));
		assertThat(createdProduct.getQuantity(), is(request.getQuantity()));

		return createdProduct;
	}

	@Test(expected = TransactionSystemException.class)
	public void testCreateProduct_whenMissingMandatoryProperties_thenThrowException() {
		CreateProductRequest request = new CreateProductRequest();
		productService.createProduct(request);
	}

	@Test
	public void testGetProduct_whenExistingId_thenReturnProduct() throws ResourceNotFoundException {
		Product createdProduct = createProduct();

		Product product = productService.getProduct(createdProduct.getId());

		assertThat(product, notNullValue());
		assertThat(product.getId(), is(createdProduct.getId()));
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetProduct_whenNonExistingId_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
		productService.getProduct(9999L);
	}

	@Test
	public void testUpdateProduct_whenValidRequest_thenReturnUpdatedProduct() throws ResourceNotFoundException {
		Product createdProduct = createProduct();

		UpdateProductRequest request = new UpdateProductRequest();
		request.setName(createdProduct.getName() + "Updated");
		request.setPrice(createdProduct.getPrice() + 10);
		request.setQuantity(createdProduct.getQuantity() + 10);

		Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);

		assertThat(updatedProduct, notNullValue());
		assertThat(updatedProduct.getId(), is(createdProduct.getId()));

		assertThat(updatedProduct.getQuantity(), not(is(createdProduct.getQuantity())));
		assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));

		assertThat(updatedProduct.getPrice(), not(is(createdProduct.getPrice())));
		assertThat(updatedProduct.getPrice(), is(request.getPrice()));

		assertThat(updatedProduct.getName(), not(is(createdProduct.getName())));
		assertThat(updatedProduct.getName(), is(request.getName()));
	}}
