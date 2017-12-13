package webSemantica.app.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import webSemantica.app.service.JenaAPI;



@Controller
public class AppController {
	
	private JenaAPI api;

	@RequestMapping("/")
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/index");
		return modelAndView;
	}

	@RequestMapping(value="/search", method = RequestMethod.POST)
	public ModelAndView searchParking(@RequestParam String input, Model parking){
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		result = JenaAPI.getParkingByDistrict(JenaAPI.read(), input.toUpperCase());
		ModelAndView modelAndView = new ModelAndView();
		parking.addAttribute("parking", result);
		modelAndView.setViewName("/index");
		return modelAndView;
	}

}
