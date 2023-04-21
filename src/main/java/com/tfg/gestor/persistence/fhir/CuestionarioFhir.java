package com.tfg.gestor.persistence.fhir;

import java.util.Date;

import org.hl7.fhir.r5.model.Coding;
import org.hl7.fhir.r5.model.DateTimeType;
import org.hl7.fhir.r5.model.Questionnaire;
import org.hl7.fhir.r5.model.QuestionnaireResponse;
import org.hl7.fhir.r5.model.ResourceType;
import org.hl7.fhir.r5.model.Enumerations.PublicationStatus;
import org.hl7.fhir.r5.model.IdType;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;

@Component
public class CuestionarioFhir {

	private FhirContext ctx = FhirContext.forR5();
	private IGenericClient client = ctx.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
	private IParser parser = ctx.newJsonParser().setPrettyPrint(true);
	
	public String createMainQuest() {
		String id = null;
		
		// Crear un objeto de tipo Questionnaire
		Questionnaire questionnaire = new Questionnaire();

		// Establecer los valores de los campos del Questionnaire
		questionnaire.setStatus(PublicationStatus.ACTIVE);
		questionnaire.setId(new IdType("Consentimientos"));
		questionnaire.addSubjectType(ResourceType.Patient.name());
		questionnaire.setDateElement(new DateTimeType(new Date()));
		questionnaire.setTitle("Consentimiento para el uso de datos sanitarios");
		questionnaire.setDescription("Este cuestionario se utiliza para obtener el consentimiento del paciente para el uso de sus datos sanitarios.");

		// Crear el primer item de la encuesta
		Questionnaire.QuestionnaireItemComponent item1 = new Questionnaire.QuestionnaireItemComponent();
		item1.setLinkId("1");
		item1.setText("¿Está de acuerdo en permitir que se utilicen sus datos sanitarios con fines de investigación médica?");
		item1.setType(Questionnaire.QuestionnaireItemType.CHOICE);
		item1.setRequired(true);
		item1.setAnswerValueSet("http://terminology.hl7.org/ValueSet/v2-0136");

		// Agregar opciones de respuesta a la pregunta
		Coding optin = new Coding("http://terminology.hl7.org/CodeSystem/v3-ActConsentDirective", "OPTIN", "Permitir uso");
		Coding optout = new Coding("http://terminology.hl7.org/CodeSystem/v3-ActConsentDirective", "OPTOUT", "No permitir uso");
		item1.addAnswerOption().setValue(optin);
		item1.addAnswerOption().setValue(optout);

		// Crear el segundo item de la encuesta
		Questionnaire.QuestionnaireItemComponent item2 = new Questionnaire.QuestionnaireItemComponent();
		item2.setLinkId("2");
		item2.setText("¿Está de acuerdo en permitir que se compartan sus datos sanitarios con otros investigadores?");
		item2.setType(Questionnaire.QuestionnaireItemType.CHOICE);
		item2.setRequired(true);
		item2.setAnswerValueSet("http://terminology.hl7.org/ValueSet/v2-0136");

		// Agregar opciones de respuesta a la pregunta
		item2.addAnswerOption().setValue(optin);
		item2.addAnswerOption().setValue(optout);

		// Crear el tercer item de la encuesta
		Questionnaire.QuestionnaireItemComponent item3 = new Questionnaire.QuestionnaireItemComponent();
		item3.setLinkId("3");
		item3.setText("¿Hasta que fecha está de acuerdo en permitir que se utilicen y compartan sus datos sanitarios?");
		item3.setType(Questionnaire.QuestionnaireItemType.DATE);
		item3.setRequired(true);

		// Agregar los items al Questionnaire
		questionnaire.addItem(item1);
		questionnaire.addItem(item2);
		questionnaire.addItem(item3);
		
		MethodOutcome outcome = client.create().resource(questionnaire).execute();
     	id = outcome.getId().getIdPart();
     	
     	return id;
	}
	
	public Questionnaire getQuest(String id) {
		Questionnaire cuestionario = client.read().resource(Questionnaire.class).withId(id).execute();
		return cuestionario;
	}
	
	public String createQuest(Questionnaire questionnaire) {
		String id = null;
		
		MethodOutcome outcome = client.create().resource(questionnaire).execute();
     	id = outcome.getId().getIdPart();
     	
     	return id;
	}
	
	public String createQuestResp(QuestionnaireResponse response) {
		String id = null;
		
		MethodOutcome outcome = client.create().resource(response).execute();
     	id = outcome.getId().getIdPart();
     	
     	return id;
	}
	
	public String showQuest(Questionnaire questionnaire) {
		return parser.encodeResourceToString(questionnaire);
	}

}
