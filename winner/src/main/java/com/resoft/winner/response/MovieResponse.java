package com.resoft.winner.response;

import java.io.Serializable;

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
public class MovieResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String producer;
	private int interval;
	private int previousWin;
	private int followingWin;

}
