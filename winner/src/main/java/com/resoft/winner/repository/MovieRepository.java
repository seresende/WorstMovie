package com.resoft.winner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resoft.winner.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
