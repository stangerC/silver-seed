package com.silver.seed.query.showcase.repository;

import com.silver.seed.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Liaojian on 2014/10/8.
 */
public interface QueryRepository extends JpaRepository<Query, String> {
    public Query findByName(String name);
}
