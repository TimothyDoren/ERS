package com.maxtrain.bootcamp.ers.expense;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxtrain.bootcamp.ers.employee.Employee;
import com.maxtrain.bootcamp.ers.employee.EmployeeRepository;


@CrossOrigin
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
	@SuppressWarnings("unused")
	private final String NEW = "NEW";
	private final String REVIEW = "REVIEW";
	private final String APPROVED = "APPROVED";
	private final String REJECTED = "REJECTED";
	private final String PAID = "PAID";
	
	@Autowired
	private ExpenseRepository expRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Expense>> getExpenses() {
		Iterable<Expense> expense = expRepo.findAll();
		return new ResponseEntity<Iterable<Expense>>(expense, HttpStatus.OK); 
	}
	@GetMapping("{id}")
	public ResponseEntity<Expense> getExpense(@PathVariable int id){
		Optional<Expense> expense = expRepo.findById(id);
		if(expense.isEmpty()) { 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Expense> (expense.get(), HttpStatus.OK);
	}
	@GetMapping("reviews")
	public ResponseEntity<Iterable<Expense>> getExpensesInReview(){
		Iterable<Expense> expensesInReview = (Iterable<Expense>) expRepo.findByStatus(REVIEW); 
		return new ResponseEntity<Iterable<Expense>>(expensesInReview, HttpStatus.OK);
	} 
	@GetMapping("approved")
	public ResponseEntity<Iterable<Expense>> getApprovedExpenses(){
		Iterable<Expense> approvedExpenses = expRepo.findByStatus(APPROVED);
		return new ResponseEntity<Iterable<Expense>>(approvedExpenses, HttpStatus.OK);
	}
	@PostMapping  
	public ResponseEntity<Expense> postExpense (@RequestBody Expense expense){
		Expense newExpense = expRepo.save(expense);
		return new ResponseEntity<Expense>(newExpense, HttpStatus.CREATED);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putExpense(@PathVariable int id, @RequestBody Expense expense) {
		if(expense.getId() != id) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		expRepo.save(expense);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("pay/{id}")
	public ResponseEntity PayExpense(@PathVariable int id, @RequestBody Expense expense) {
		Optional<Expense> expenseToBePaid = expRepo.findById(id);
		if(expenseToBePaid.isEmpty()) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		expenseToBePaid.get().setStatus(PAID);
		var total = expenseToBePaid.get().getTotal();
		Optional<Employee> employee = empRepo.findById(expenseToBePaid.get().getEmployee().getId());
		employee.get().setExpensesPaid(total);
		var expensesDue = employee.get().getExpensesDue();
		employee.get().setExpensesDue(expensesDue -= total);
		expRepo.save(expenseToBePaid.get());
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@PutMapping("review/{id}")
	public ResponseEntity reviewExpenses(@PathVariable int id, @RequestBody Expense expense) {
		String newStatus = expense.getTotal() <= 75 ? APPROVED : REVIEW;
		expense.setStatus(newStatus);
		Optional<Employee> employee = empRepo.findById(expense.getEmployee().getId());
		if(newStatus.equals("APPROVED")) {
			employee.get().setExpensesDue(employee.get().getExpensesDue() + expense.getTotal());
		}
		empRepo.save(employee.get());
		return putExpense(id, expense);   
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("approved/{id}")
	public ResponseEntity approveExpense(@PathVariable int id, @RequestBody Expense expense) {
		expense.setStatus(APPROVED); 
		return putExpense(id, expense);
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectExpense(@PathVariable int id, @RequestBody Expense expense) {
		expense.setStatus(REJECTED);
		return putExpense(id, expense);
	}
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteExpense(@PathVariable int id) {
		Optional<Expense> expense = expRepo.findById(id);
		if(expense.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	    }
	    expRepo.delete(expense.get());
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
