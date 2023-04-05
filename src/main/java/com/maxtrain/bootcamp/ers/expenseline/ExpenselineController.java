package com.maxtrain.bootcamp.ers.expenseline;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.maxtrain.bootcamp.ers.expense.Expense;
import com.maxtrain.bootcamp.ers.expense.ExpenseRepository;
import com.maxtrain.bootcamp.ers.item.Item;

import jakarta.persistence.*;


public class ExpenselineController {
	
	@Autowired
	private ExpenseRepository expRepo;
	@Autowired 
	private ExpenselineRepository explRepo;

	
}
