package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.CarModel;
/*
 * CarService
 */
public interface CarService {
	void addCar(CarModel car);
	List<CarModel> sortByPriceDesc(Long dealer_id);
	void deleteCar(CarModel car);
	CarModel getCar(long id);
	void updateCar(Long id, String brand, String type, Long price, Integer amount);

}
