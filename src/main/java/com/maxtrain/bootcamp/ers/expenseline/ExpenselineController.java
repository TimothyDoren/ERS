package com.maxtrain.bootcamp.ers.expenseline;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.maxtrain.bootcamp.ers.expense.Expense;
import com.maxtrain.bootcamp.ers.expense.ExpenseRepository;
import com.maxtrain.bootcamp.ers.item.Item;
import com.maxtrain.bootcamp.ers.item.ItemRepository;

import jakarta.persistence.*;

@CrossOrigin
@RestController
@RequestMapping("/api/expenselines")
public class ExpenselineController {
	
	@Autowired
	private ExpenseRepository expRepo;
	@Autowired 
	private ExpenselineRepository explRepo;
	@Autowired
	private ItemRepository itemRepo;
	

	@GetMapping
	public ResponseEntity<Iterable<Expenseline>> getExpenselines() { 
		Iterable<Expenseline> expenselines = explRepo.findAll();
		return new ResponseEntity<Iterable<Expenseline>>(expenselines, HttpStatus.OK); 
	} 
	
	@GetMapping("{id}")
	public ResponseEntity<Expenseline> getExpenseline(@PathVariable int id) {
		Optional<Expenseline> expenseline = explRepo.findById(id);
		if(expenseline.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Expenseline>(expenseline.get(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Expenseline> postExpenseline (@RequestBody Expenseline expenseline) {
		Expenseline newExpenseline = explRepo.save(expenseline);
		Optional<Expense> expense = expRepo.findById(expenseline.getExpense().getId());
		if(!expense.isEmpty() ) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<Expenseline>(newExpenseline, HttpStatus.CREATED);	
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putExpenseline (@PathVariable int id, @RequestBody Expenseline expenseline) {
		if(expenseline.getId() != id) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		explRepo.save(expenseline);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteExpenseline(@PathVariable int id) {
		Optional<Expenseline> expenseline = explRepo.findById(id);
		if(expenseline.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}	
		explRepo.delete(expenseline.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
