package com.resoft.winner.response.csv;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

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
public class MovieCSV implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@CsvBindByName(column = "year", required = true)
	private int year;

	@CsvBindByName(column = "title", required = true)
	private String title;

	@CsvBindByName(column = "studios", required = true)
	private String studios;

	@CsvBindByName(column = "producers", required = true)
	private String producers;

	@CsvCustomBindByName(column = "winner", required = false, converter = WinnerConverter.class)
	private boolean winner;

}
