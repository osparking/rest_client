package space.bumtiger.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import space.bumtiger.domain.Ingredient;

public class HamburgerClient {
	private RestTemplate rest = new RestTemplate();

	public Ingredient getIngredientById(String ingredientId) {
		Map<String, String> urlVariables = new HashMap<>();
		urlVariables.put("id", ingredientId);
		return rest.getForObject(
				"http://localhost:8080/data-api/ingredients/{id}",
				Ingredient.class, urlVariables);
	}
}
