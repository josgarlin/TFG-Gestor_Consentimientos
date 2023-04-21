package com.tfg.gestor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.gestor.persistence.entity.Sanitario;
import com.tfg.gestor.service.SanitarioService;
import com.tfg.gestor.service.dto.SanitarioInDTO;

@RestController
@RequestMapping("/sanitarios")
public class SanitarioController {

	private final SanitarioService sanitarioService;
	
	public SanitarioController(SanitarioService sanitarioService) {
		this.sanitarioService = sanitarioService;
	}

	@PostMapping
	public Sanitario createSanitario(@RequestBody SanitarioInDTO sanitarioInDTO) {
		return this.sanitarioService.createSanitario(sanitarioInDTO);
	}
	
	@GetMapping("/login/{user}&{pass}")
	public void check(@PathVariable("user") String username, @PathVariable("pass") String password) {
		this.sanitarioService.startProcess(username, password);
	}
}
