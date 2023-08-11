package com.tim1.cook.controllers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tim1.cook.entities.RoleEntity;
import com.tim1.cook.repositories.RoleRepository;
import com.tim1.cook.service.RoleService;

@RestController
@RequestMapping(value = "/api/v1/roles")
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

	@Autowired
	public RoleService roleService;
	@Autowired
	public RoleRepository roleRepository;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createRole(@RequestBody RoleEntity newRole) {
		logger.info("/api/v1/createRole started.");
		RoleEntity role = roleService.createRole(newRole);
		logger.info("Finished OK.");
		return new ResponseEntity<>(newRole, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<RoleEntity>> getAllRoles() {
		logger.info("/api/v1/getAllRoles started.");
		logger.info("Finished OK.");
		return new ResponseEntity(roleRepository.findAll(), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public RoleEntity deleteRole(@PathVariable Integer id) {
		logger.info("/api/v1/deleteRole started.");
		RoleEntity role = roleRepository.findById(id).get();
		logger.info("Role found.");
		roleRepository.deleteById(id);
		role.setDeleted(true);
		logger.info("Finished OK.");
		return role;
	}
}
