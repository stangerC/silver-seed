/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.silver.seed.query.showcase.service;

import com.silver.seed.core.paging.Paging;
import com.silver.seed.query.showcase.entity.Customer;
import com.silver.seed.query.showcase.entity.CustomerRepository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Liaojian
 */

public class CustomerService {

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
    
    public Page<Customer> getAll(Paging paging) {
        Pageable pageable = new PageRequest(paging.getPageNumber() - 1, paging.getPageSize());
        return customerRepository.findAll(pageable);
    }
}
