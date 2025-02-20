package com.example.moattravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.moattravel.entity.House;
import com.example.moattravel.repository.HouseRepository;

@Controller
@RequestMapping("/admin/houses")

public class AdminHouseController {
	private final HouseRepository houseRepository;
	
	public AdminHouseController(HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}
	
	@GetMapping
//	public String index(Model model) {
//	public String index(Model model, Pageable pageable) {
//		public String index(Model model, @PageableDefault(page =0,size=10, sort="id", direction= Direction.ASC) Pageable pageable) {
		public String index(Model model, @PageableDefault(page =0, size =10, sort ="id", direction=Direction.ASC) Pageable pageable, 
				@RequestParam(name = "keyword", required = false) String keyword) {
		
//		List<House> houses = houseRepository.findAll();
//		Page<House> housePage = houseRepository.findAll(pageable);
		Page<House>housePage;
		
		if (keyword != null && !keyword.isEmpty()) {
			housePage =houseRepository.findByNameLike("%" + keyword + "%", pageable);
		} else {
			housePage = houseRepository.findAll(pageable);
		}
	
//		model.addAttribute("houses" , houses);
		model.addAttribute("housePage", housePage);
		model.addAttribute("keyword", keyword);
		
		
		return "admin/houses/index";
	}
	

}
