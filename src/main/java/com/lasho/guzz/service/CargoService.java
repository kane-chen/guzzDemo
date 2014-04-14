package com.lasho.guzz.service;

import java.util.List;

import com.lasho.guzz.domain.custom.Cargo;

public interface CargoService {

	int save(Cargo cargo,Object tableCondition) ;
	
	boolean update(Cargo cargo,Object tableCondition) ;
	
	Cargo findById(int cargoId,Object tableCondition) ;
	
	List<Cargo> findAll(Object tableCondition);
}
