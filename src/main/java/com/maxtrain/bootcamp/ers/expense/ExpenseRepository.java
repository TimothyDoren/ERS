package com.maxtrain.bootcamp.ers.expense;



import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository <Expense, Integer> {
    Iterable<Expense> findByStatus(String status);
    Iterable<Expense> findByEmployeeId(int employeeId);
}