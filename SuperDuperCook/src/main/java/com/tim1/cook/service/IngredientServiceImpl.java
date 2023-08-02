package com.tim1.cook.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tim1.cook.entities.AllergenEntity;
import com.tim1.cook.entities.IngredientEntity;
import com.tim1.cook.entities.dto.IngredientDTO;
import com.tim1.cook.repositories.AllergenRepository;
import com.tim1.cook.repositories.IngredientRepository;

@Service
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private AllergenRepository allergenRepository;

	@Override
	public IngredientEntity saveIngredient(IngredientEntity ingredient) {
		return ingredientRepository.save(ingredient);
	}

	@Override
	public IngredientEntity getIngredientById(Integer id) {
		return ingredientRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteIngredientById(Integer id) {
		ingredientRepository.deleteById(id);
	}

	@Override
	public List<IngredientEntity> getAllIngredients() {
		return (List<IngredientEntity>) ingredientRepository.findAll();
	}
	
	@Override
	public IngredientEntity updateIngredient(Integer id, IngredientEntity updatedIngredient) {
        IngredientEntity existingIngredient = ingredientRepository.findById(id).orElse(null);
        if (existingIngredient == null) {
            return null; 
        }

        existingIngredient.setName(updatedIngredient.getName());
        existingIngredient.setMeasurementUnit(updatedIngredient.getMeasurementUnit());
        existingIngredient.setCalories(updatedIngredient.getCalories());
        existingIngredient.setCarboHydrate(updatedIngredient.getCarboHydrate());
        existingIngredient.setSugar(updatedIngredient.getSugar());
        existingIngredient.setFat(updatedIngredient.getFat());
        existingIngredient.setSaturatedFat(updatedIngredient.getSaturatedFat());
        existingIngredient.setProtein(updatedIngredient.getProtein());
        existingIngredient.setAllergens(updatedIngredient.getAllergens());

        return ingredientRepository.save(existingIngredient);
    }

	@Override
	public IngredientDTO createNewIngredient(IngredientDTO dto) {
		IngredientEntity in = new IngredientEntity();
		in.setName(dto.getName());
		in.setMeasurementUnit(dto.getMeasurementUnit());
		in.setCalories(dto.getCalories());
		in.setCarboHydrate(dto.getCarboHydrate());
		in.setSugar(dto.getSugar());
		in.setFat(dto.getFat());
		in.setSaturatedFat(dto.getSaturatedFat());
		in.setProtein(dto.getProtein());

		for (String a : dto.getAllergens()) {
			List<AllergenEntity> la = allergenRepository.findByName(a);
			if (la.isEmpty()) {
				AllergenEntity na = new AllergenEntity();
				na.setName(a);
				na.setIcon(a);
				allergenRepository.save(na);
				in.getAllergens().add(na);
			} else {
				in.getAllergens().add(la.get(0));
			}
		}
		ingredientRepository.save(in);
		return new IngredientDTO();
	}

	@Override
	public IngredientDTO updateIngredientDTO(Integer id, IngredientDTO dto) {
		Optional<IngredientEntity> o = ingredientRepository.findById(id);
		if (o.isPresent()) {
			IngredientEntity in = o.get();
			in.setName(dto.getName());
			in.setMeasurementUnit(dto.getMeasurementUnit());
			in.setCalories(dto.getCalories());
			in.setCarboHydrate(dto.getCarboHydrate());
			in.setSugar(dto.getSugar());
			in.setFat(dto.getFat());
			in.setSaturatedFat(dto.getSaturatedFat());
			in.setProtein(dto.getProtein());

			in.getAllergens().clear();
			for (String a : dto.getAllergens()) {
				List<AllergenEntity> la = allergenRepository.findByName(a);
				if (la.isEmpty()) {
					AllergenEntity na = new AllergenEntity();
					na.setName(a);
					allergenRepository.save(na);
					in.getAllergens().add(na);
				} else {
					in.getAllergens().add(la.get(0));
				}
			}
			ingredientRepository.save(in);
		}
		return new IngredientDTO();
	}

}