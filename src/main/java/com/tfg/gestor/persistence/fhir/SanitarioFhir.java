package com.tfg.gestor.persistence.fhir;

import org.hl7.fhir.r5.model.Practitioner;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

@Component
public class SanitarioFhir {

	private FhirContext ctx = FhirContext.forR5();
	private IGenericClient client = ctx.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
//	private IParser parser = ctx.newJsonParser().setPrettyPrint(true);
	
	public Practitioner findPractitioner(String id) {
		Practitioner practitioner = client.read().resource(Practitioner.class).withId(id).execute();
		
		return practitioner;
	}
}
