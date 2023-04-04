package com.maxtrain.bootcamp.ers.expense;

import com.maxtrain.bootcamp.ers.employee.Employee;

import jakarta.persistence.*;

@Entity
@Table(name="Expenses")

public class Expense {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	 
	private int id;
	@Column(length=80, nullable=false)
	private String description;
	@Column(length=10, nullable=false)
	private String status;
	@Column(columnDefinition="decimal(11,2) NOT NULL")
	private double total;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="employeeId", columnDefinition="int")
	private Employee employee;
	
	public Expense() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	

}
