/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.silver.seed.query.showcase.service;

import com.silver.seed.query.showcase.entity.Customer;
import com.silver.seed.query.showcase.repository.CustomerRepository;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Liaojian
 */
@Service("customerService")
public class CustomerService {
    @Resource
    CustomerRepository customerRepository;

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }      
    
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
