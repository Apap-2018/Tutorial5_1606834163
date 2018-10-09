package com.apap.tutorial5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

/*
 * DealerController
 */
@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "Home");
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("title", "Add Dealer");
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer, Model model) {
		model.addAttribute("title", "Add Dealer");
		dealerService.addDealer(dealer);
		return "add";
	}
	
	@RequestMapping("/dealer/view")
	public String viewId(@RequestParam(value="id",required = true) Long id, Model model) {
		DealerModel archive = dealerService.getDealerDetailById(id).get();
		model.addAttribute("title", "View Dealer "+ id);
		model.addAttribute("dealer",archive);
		return "view-dealer";
	}

	@RequestMapping(value = "dealer/view" , method = RequestMethod.GET)
	private String viewDealer(@RequestParam ("dealerId") long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		
		List<CarModel> archiveListCar =carService.sortByPriceDesc(dealerId);
		dealer.setListCar(archiveListCar);
		model.addAttribute("dealer", dealer);
		
		model.addAttribute("title", "View Dealer " + dealerId);
		
		return "view-dealer-with-car";
	}
	
	@RequestMapping(value = "/dealer/delete", method = RequestMethod.POST)
	private String deleteDealerById(@RequestParam("id") Long id, Model model) {
		if(carService.getCar(id) != null) {
			dealerService.deleteDealer(id);
			model.addAttribute("title", "Delete Dealer "+ id);
			return "delete";
		}
		model.addAttribute("title", "Error ");

		return "error";
	}
	
	@RequestMapping(value = "/dealer/update", method = RequestMethod.GET)
	private String updateDealerById(@RequestParam("id") Long id, Model model, @RequestParam("alamat") String alamat, @RequestParam("noTelp") String noTelp) {
		if(carService.getCar(id) != null) {
			dealerService.updateDealer(id, alamat, noTelp);
			model.addAttribute("title", "Update Dealer "+id);
			return "update";
		}
		model.addAttribute("title", "Error ");

		return "error";

	}
	
	@RequestMapping(value = "/dealer/view-all", method = RequestMethod.GET)
	public String viewAllDealer(Model model) {
		List<DealerModel> listAllDealer = dealerService.viewAllDealer();
		model.addAttribute("listAllDealer", listAllDealer);
		
		model.addAttribute("title", "View All");
		
		return "viewAll";
	}
}
