package com.tfg.gestor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.gestor.service.CuestionarioService;

@RestController
public class CuestionarioController {

	private final CuestionarioService cuestionarioService;
	
	public CuestionarioController(CuestionarioService cuestionarioService) {
		this.cuestionarioService = cuestionarioService;
	}

	@GetMapping("/quest/{id}")
	public String showQuestionnaire(@PathVariable("id") String id) {
		return this.cuestionarioService.showQuestionnaire(id);
	}
	
	@GetMapping("/form/{id}")
	public String showForm(@PathVariable("id") String id) {
		return this.cuestionarioService.showForm(id);
	}
}
