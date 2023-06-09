package space.bumtiger.rest;

import org.springframework.web.client.RestTemplate;

import space.bumtiger.domain.Ingredient;

public class HamburgerClient {
	private RestTemplate rest = new RestTemplate();

	public Ingredient getIngredientById(String ingredientId) {
		return rest.getForObject(
				"http://localhost:8080/data-api/ingredients/{id}",
				Ingredient.class, ingredientId);
	}
}
