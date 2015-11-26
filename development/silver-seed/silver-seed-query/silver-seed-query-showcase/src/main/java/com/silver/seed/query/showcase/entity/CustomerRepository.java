/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.silver.seed.query.showcase.entity;

import com.silver.seed.query.showcase.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Liaojian
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
}
