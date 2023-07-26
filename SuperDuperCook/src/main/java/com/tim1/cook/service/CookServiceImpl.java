package com.tim1.cook.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tim1.cook.entities.CookEntity;
import com.tim1.cook.repositories.CookRepository;



@Service
public class CookServiceImpl implements CookService {

	@Autowired
	private CookRepository cookRepository;
	
	
	@Override
	public CookEntity createCook(CookEntity newcook) {
		CookEntity cook = new CookEntity();
		cook.setUsername(newcook.getUsername());
		cook.setPassword(newcook.getPassword());
		cookRepository.save(cook);
		return cook;
	}

	@Override
	public CookEntity updateCook(Integer id, CookEntity updatedCook) {
		CookEntity cook = cookRepository.findById(id).get();		
		cook.setUsername(updatedCook.getUsername());
		cook.setPassword(updatedCook.getPassword());
		cookRepository.save(cook);
		return cook;

	}


}
