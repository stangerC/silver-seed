package com.silver.seed.core.entity;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Serializable{
	public ID getId();
}
