package com.maxtrain.bootcamp.ers.expenseline;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.maxtrain.bootcamp.ers.expense.Expense;
import com.maxtrain.bootcamp.ers.item.Item;

import jakarta.persistence.*;

@Entity
@Table(name="Expenselines")
	
public class Expenseline {
	

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id;
		private int quantity = 1;
		
		
		@JsonBackReference
		@ManyToOne(optional=false)
		@JoinColumn(name="expenseId", columnDefinition="int")
		private Expense expense;
		
		@ManyToOne(optional=false)
		@JoinColumn(name="itemId", columnDefinition="int")
		private Item item;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public Expense getExpense() {
			return expense;
		}

		public void setExpense(Expense expense) {
			this.expense = expense;
		}

		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}
		

    }