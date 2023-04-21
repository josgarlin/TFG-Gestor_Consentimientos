package com.tfg.gestor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.springframework.stereotype.Service;

import com.tfg.gestor.mapper.SanitarioInDTOToSanitario;
import com.tfg.gestor.persistence.entity.Sanitario;
import com.tfg.gestor.persistence.entity.SanitarioStatus;
import com.tfg.gestor.persistence.repository.SanitarioRepository;
import com.tfg.gestor.service.dto.SanitarioInDTO;
import com.tfg.gestor.util.KieUtil;

@Service
public class SanitarioService {

	private final SanitarioRepository repository;
	private final SanitarioInDTOToSanitario mapper;

	public SanitarioService(SanitarioRepository repository, SanitarioInDTOToSanitario mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public Sanitario createSanitario(SanitarioInDTO sanitarioInDTO) {
		Sanitario sanitario = mapper.map(sanitarioInDTO);
		return this.repository.save(sanitario);
	}
	
//	@Transactional
//	public Boolean check(String username, String password) {
//		Boolean result = false;
//		Sanitario sanitario = this.repository.findByUsernameAndPassword(username, password);
//		
//		if (sanitario != null && sanitario.getStatus() == SanitarioStatus.OUTSIDE) {
//			result = true;
//			this.repository.markInside(sanitario.getDni());
//		}
//		
//		return result;
//	}
	
	public void startProcess(String username, String password) {
		KieUtil util = new KieUtil();
		KieServicesClient kieServicesClient = util.getKieServicesClient();
		ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
		
		Long id = processClient.startProcess("gestor_1.0.0-SNAPSHOT", "gestor.LoginProcess");
		System.out.println("Process id is:::: "+id);
		
		startTask(username, password);
	}
	
	private void startTask(String username, String password) {
		KieUtil util = new KieUtil();
		KieServicesClient kieServicesClient = util.getKieServicesClient();
		UserTaskServicesClient userClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
		
		Long id = obtenerIdTarea(userClient, "LOGIN");
		userClient.startTask("gestor_1.0.0-SNAPSHOT", id, "wbadmin");
		
		completeTask(username, password, id);
	}
	
	private void completeTask(String username, String password, Long id) {
		KieUtil util = new KieUtil();
		KieServicesClient kieServicesClient = util.getKieServicesClient();
		UserTaskServicesClient userClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
		
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean result = false;
		Sanitario sanitario = this.repository.findByUsernameAndPassword(username, password);
		
		if (sanitario != null && sanitario.getStatus() == SanitarioStatus.OUTSIDE) {
			result = true;
			this.repository.markInside(sanitario.getDni());
		}
		map.put("result", result);
		userClient.completeTask("gestor_1.0.0-SNAPSHOT", id, "wbadmin", map);
	}
	
	private Long obtenerIdTarea(UserTaskServicesClient userClient, String nombreTarea) {
		Long id = null;
		
		List<TaskSummary> taskList = userClient.findTasks("wbadmin", 0, 0);
		for (TaskSummary task : taskList) {
		    if (task.getName().equals(nombreTarea)) {
		        id = task.getId();
		        break;
		    }
		}
		
		return id;
	}
	
}
