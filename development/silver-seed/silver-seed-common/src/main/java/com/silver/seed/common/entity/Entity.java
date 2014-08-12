package com.silver.seed.common.entity;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Serializable{
	public ID getId();
}
