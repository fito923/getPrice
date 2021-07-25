package com.java.price.checking.repositories;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.price.checking.entities.PricesEntity;



public interface PricesRepository extends JpaRepository<PricesEntity, Integer> {
	
	public List<PricesEntity> getData(HashMap<String, Object> conditions);
}

