package com.silver.seed.query.repository;

import com.silver.seed.query.entity.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Liaojian on 2015/11/26.
 */
public interface ConditionRepository extends JpaRepository<Condition, String> {
}
