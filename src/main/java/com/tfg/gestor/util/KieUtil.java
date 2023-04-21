package com.tfg.gestor.util;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class KieUtil {

	private String URL = "http://localhost:8080/kie-server/services/rest/server";
	private String USERNAME = "wbadmin";
	private String PASSWORD = "wbadmin";
	
	private KieServicesConfiguration config;

	public KieServicesClient getKieServicesClient() {
		config = KieServicesFactory.newRestConfiguration(URL, USERNAME, PASSWORD);
		config.setMarshallingFormat(MarshallingFormat.JSON);
//		Set<Class<?>> set = new HashSet<Class<?>>();
//		set.add(Sanitario.class);
//		config.addJaxbClasses(set);
		return KieServicesFactory.newKieServicesClient(config);
	}

}
