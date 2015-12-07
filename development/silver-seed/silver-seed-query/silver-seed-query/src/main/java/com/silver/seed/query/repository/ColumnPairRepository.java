package com.silver.seed.query.repository;

import com.silver.seed.query.entity.JoinColumns;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Liaojian on 2015/3/5.
 */
public interface ColumnPairRepository extends JpaRepository<JoinColumns, String> {
}
