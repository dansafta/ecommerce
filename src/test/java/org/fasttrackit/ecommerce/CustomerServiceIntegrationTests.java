package org.fasttrackit.ecommerce;

import org.fasttrackit.ecommerce.domain.Customer;
import org.fasttrackit.ecommerce.service.CustomerService;
import org.fasttrackit.ecommerce.transfer.customer.CreateCustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceIntegrationTests {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateCustomer_whenValidRequest_thenReturnCustomer(){
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFirstName("Gigi");
        request.setLastName("Duru");
        request.setAge(82);

        Customer customer = customerService.createCustomer(request);

        assertThat(customer, notNullValue());
        assertThat(customer.getId(), greaterThan(0L));
        assertThat(customer.getFirstName(), is (request.getFirstName()));
        assertThat(customer.getLastName(), is (request.getLastName()));
        assertThat(customer.getAge(), is (request.getAge()));

    }

}
