package com.resoft.winner.entity;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Movie implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
    
	@Column(name = "`year`")
	private int year;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "studios")
	private String studios;
	
	@ManyToMany
	@JoinTable(name = "movie_producer", 
		joinColumns = @JoinColumn(name = "producer_id"), 
		inverseJoinColumns = @JoinColumn(name = "movie_id"))
	@OrderBy("name ASC")	
	private List<Producer> producers;
	
	private boolean winner;
	
	public Movie( int year, String title, String studios, List<Producer> producers, boolean winner) {		
		this.year = year;
		this.title = title;
		this.studios = studios;
		this.producers = producers;
		this.winner = winner;
	}
	

}
