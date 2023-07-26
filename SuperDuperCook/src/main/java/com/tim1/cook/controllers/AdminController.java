package com.tim1.cook.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.tim1.cook.controllers.util.RESTError;
import com.tim1.cook.entities.AdminEntity;
import com.tim1.cook.repositories.AdminRepository;
import com.tim1.cook.service.AdminService;


@RestController
@RequestMapping(value = "/api/v1/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private AdminService adminService;
		
	private final Logger logger= (Logger) LoggerFactory.getLogger(this.getClass());
	
	
	//@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/")	
	public ResponseEntity<?> createAdmin(@Valid @RequestBody AdminEntity newadmin) {
		logger.info("/api/v1/admin/createAdmin started.");
		try {	
		AdminEntity admin = adminService.createAdmin(newadmin);			
				} catch (Exception e) {
						logger.error("Exception occurred: " + e.getMessage());
						return new ResponseEntity<RESTError>(
								new RESTError(1, "nije dobar zahtev, morate uneti username i pass"),HttpStatus.BAD_REQUEST);
			}
		logger.info("Finished OK.");
		return new ResponseEntity<> (newadmin, HttpStatus.CREATED);
	}

	//@Secured("ROLE_ADMIN")
	//@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ResponseEntity<List<AdminEntity>> getAllAdmins() {
		logger.info("/api/v1/admin/getAllAdmins started.");
		logger.info("Finished OK.");
		return new ResponseEntity(adminRepository.findAll(), HttpStatus.OK);
	}
	
	//Azuriranje admina
	//@Secured("ROLE_ADMIN")
	//@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody AdminEntity updatedAdmin) {
		logger.info("/api/v1/admin/updateAdmin started.");	
		AdminEntity admin = adminService.updateAdmin(id, updatedAdmin);
			
		if (updatedAdmin.getUsername()== null || updatedAdmin.getUsername().equals("")) {
		logger.info("Username is null.");
			return new ResponseEntity<RESTError>(new RESTError(1, "Please provide a username"), HttpStatus.BAD_REQUEST);
		}
		if (updatedAdmin.getPassword() == null || updatedAdmin.getPassword().equals("")) {
		logger.info("Password is null.");
			return new ResponseEntity<RESTError>(new RESTError(2, "Please provide a password"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AdminEntity>(admin, HttpStatus.CREATED);
	}
		
	//Brisanje admina
	//@Secured("ROLE_ADMIN")
	//@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public AdminEntity deleteAdmin(@PathVariable Integer id) {
		logger.info("/api/v1/admins/deleteAdmin started.");
		AdminEntity admin = adminRepository.findById(id).get();
		logger.info("Admin found.");
		adminRepository.delete(admin);
		logger.info("Finished OK.");
		return admin;
	}

	@ResponseStatus(code=HttpStatus.BAD_REQUEST)  
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {   
			String fieldName = "";
			String errorMessage = error.getDefaultMessage();
			if (error instanceof FieldError) {        
				fieldName = ((FieldError)error).getField();
				errorMessage = error.getDefaultMessage();
			} else if (error instanceof ObjectError) {
				fieldName = ((ObjectError)error).getObjectName();
				errorMessage = error.getDefaultMessage();
				}					
			errors.put(fieldName, errorMessage);  
		});
		return errors;
	}	
}
