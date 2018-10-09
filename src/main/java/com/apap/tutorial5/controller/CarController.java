package com.apap.tutorial5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

/*
 * CarController
 */
@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		CarModel car = new CarModel();
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		car.setDealer(dealer);
		
		model.addAttribute("car", car);
		
		model.addAttribute("title", "Add Car "+dealerId);
		
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add", method = RequestMethod.POST)
	private String addCarSubmit(@ModelAttribute CarModel car, Model model) {
		carService.addCar(car);
		
		model.addAttribute("title", "Add Car");
		
		return "add";
	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String delete(@ModelAttribute DealerModel dealer, Model model) {	
		for(CarModel car : dealer.getListCar()) {
			carService.deleteCar(car);
		}
		
		model.addAttribute("title", "Remove Car");
		
		return "delete";

		
		
	}
	
	@RequestMapping(value = "/car/update", method = RequestMethod.GET)
	private String updateCarById(@RequestParam("id") Long id, Model model, @RequestParam("brand") String brand, @RequestParam("type") String type, 
			@RequestParam("price") Long price, @RequestParam("amount") Integer amount) {
		if(carService.getCar(id) != null) {
			carService.updateCar(id, brand, type, price, amount);
			
			model.addAttribute("title", "Update Car " +id);
			
			model.addAttribute("title", "Update Car "+id);
			
			return "update";
		}
		model.addAttribute("title", "Error");
		return "error";
		
	}
	

}
