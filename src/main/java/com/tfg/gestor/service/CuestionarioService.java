package com.tfg.gestor.service;

import java.util.Map;

import org.hl7.fhir.r5.model.Questionnaire;
import org.hl7.fhir.r5.model.QuestionnaireResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tfg.gestor.mapper.FormToResp;
import com.tfg.gestor.mapper.QuestToForm;
import com.tfg.gestor.persistence.fhir.CuestionarioFhir;

import jakarta.annotation.PostConstruct;

@Service
public class CuestionarioService {

	private final CuestionarioFhir fhir;
	private final QuestToForm mapperForm;
	private FormToResp mapperResp;

	public CuestionarioService(CuestionarioFhir fhir, QuestToForm mapperForm) {
		this.fhir = fhir;
		this.mapperForm = mapperForm;
	}
	
	@PostConstruct
	public void createMainQuest() {
		System.out.println("Questionnaire creado exitosamente con ID: " + this.fhir.createMainQuest());
	}
	
	public String showQuestionnaire(String id) {
		Questionnaire questionnaire = this.fhir.getQuest(id);
		return this.fhir.showQuest(questionnaire);
	}
	
	public String showForm(String id){
		Questionnaire questionnaire = this.fhir.getQuest(id);
		Map<String, Object> result = this.mapperForm.map(questionnaire);
		mapperResp = new FormToResp(questionnaire);
		QuestionnaireResponse response = mapperResp.map(result);
//		return this.fhir.createQuestResp(response);
		return null;
	}
}
