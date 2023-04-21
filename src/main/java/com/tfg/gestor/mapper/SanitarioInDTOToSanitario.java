package com.tfg.gestor.mapper;

import org.hl7.fhir.r5.model.Practitioner;
import org.springframework.stereotype.Component;

import com.tfg.gestor.persistence.entity.Sanitario;
import com.tfg.gestor.persistence.entity.SanitarioStatus;
import com.tfg.gestor.persistence.fhir.SanitarioFhir;
import com.tfg.gestor.service.dto.SanitarioInDTO;

@Component
public class SanitarioInDTOToSanitario implements IMapper<SanitarioInDTO, Sanitario> {

	private final SanitarioFhir fhir;
	
	public SanitarioInDTOToSanitario(SanitarioFhir fhir) {
		this.fhir = fhir;
	}

	@Override
	public Sanitario map(SanitarioInDTO in) {
		Sanitario sanitario = new Sanitario();
		Practitioner practitioner = fhir.findPractitioner(in.getDni());
		String name = practitioner.getName().get(0).getGiven().get(0).toString() + " " +
				practitioner.getName().get(0).getFamily();
		
		sanitario.setDni(in.getDni());
		sanitario.setName(name);
		sanitario.setUsername(in.getUsername());
		sanitario.setPassword(in.getPassword());
		sanitario.setStatus(SanitarioStatus.OUTSIDE);
		
		return sanitario;
	}

}
