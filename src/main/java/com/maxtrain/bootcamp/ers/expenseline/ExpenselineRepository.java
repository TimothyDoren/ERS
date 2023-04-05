package com.maxtrain.bootcamp.ers.expenseline;

import org.springframework.data.repository.CrudRepository;

public interface ExpenselineRepository extends CrudRepository<Expenseline, Integer> {
	Iterable<Expenseline> findByRequestId (int requestId);
	

}
