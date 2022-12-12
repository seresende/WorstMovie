package com.resoft.winner.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MovieListResponse {
	
	private List<MovieResponse> min = new ArrayList<MovieResponse>();
	private List<MovieResponse> max = new ArrayList<MovieResponse>();

}
