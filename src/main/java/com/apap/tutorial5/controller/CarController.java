package com.apap.tutorial5.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add (@PathVariable(value = "dealerId") Long dealerId, Model model) {
		
		
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		ArrayList<CarModel> list = new ArrayList<CarModel>();
		list.add(new CarModel());
		dealer.setListCar(list);
		
		model.addAttribute("title", "Add Car");
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params= {"save"})
	private String addCarSubmit (@ModelAttribute DealerModel dealer, Model model) {
		DealerModel dealerSkrg = dealerService.getDealerDetailById(dealer.getId()).get();
		for(CarModel car : dealer.getListCar()) {
			car.setDealer(dealerSkrg);
			carService.addCar(car);
		}
		model.addAttribute("title", "Add Car");

		return "add";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", params= {"addRow"}, method = RequestMethod.POST)
	private String addRow (@ModelAttribute DealerModel dealer, Model model) {
		dealer.getListCar().add(new CarModel());
		model.addAttribute("dealer", dealer);
		
		model.addAttribute("title", "Add Car");

		
		return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", params={"removeRow"}, method=RequestMethod.POST)
	public String removeRow(@ModelAttribute DealerModel dealer, Model model, HttpServletRequest req) {
		int rowIndex = Integer.parseInt(req.getParameter("removeRow"));
		dealer.getListCar().remove(rowIndex);
		
		model.addAttribute("dealer", dealer);
		
		model.addAttribute("title", "Add Car");
		
		return "addCar";		
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
						
			return "update";
		}
		model.addAttribute("title", "Error");
		return "error";
		
	}
	

}
