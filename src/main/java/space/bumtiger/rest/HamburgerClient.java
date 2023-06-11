package space.bumtiger.rest;

import java.text.SimpleDateFormat;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;

import lombok.extern.slf4j.Slf4j;
import space.bumtiger.domain.Ingredient;
import space.bumtiger.domain.Ingredient.Type;


@Slf4j
public class HamburgerClient {
	private RestTemplate rest = new RestTemplate();

	public Ingredient getIngredientById(String ingredientId) {
		ResponseEntity<Ingredient> responseEntity = 
				rest.getForEntity(
						"http://localhost:8080/data-api/ingredients/{id}",
						Ingredient.class, ingredientId);
		SimpleDateFormat simpleDateFormat = 
				new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss"); 
  	//원하는 데이터 포맷 지정
		String strNowDate = simpleDateFormat.format(
				responseEntity.getHeaders().getDate()); 
		log.info("채취 시각: {}", strNowDate);
		return responseEntity.getBody();
	}
	
	public void updateIngredient(Ingredient ingredient) {
		rest.put("http://localhost:8080/data-api/ingredients/{id}",
				ingredient, ingredient.getId());
	}
	
	public void deleteIngredient(Ingredient ingredient) {
		rest.delete("http://localhost:8080/data-api/ingredients/{id}",
				ingredient.getId());
	}
	
	public Ingredient postIngredient(Ingredient ingredient) 
			throws JsonProcessingException {
		var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    ObjectWriter ow = 
    		new ObjectMapper().writer().withDefaultPrettyPrinter();
    String ingreJsonStr = ow.writeValueAsString(ingredient);
    
    HttpEntity<String> request = 
        new HttpEntity<String>(ingreJsonStr, headers);    
		return rest.postForObject(
				"http://localhost:8080/data-api/ingredients",
				request, Ingredient.class);
	}

	public static void main(String... args) 
			throws JsonProcessingException {
		HamburgerClient instance = new HamburgerClient();
		Ingredient ingredient = 
				new Ingredient("DDSH", "독도새우", Type.PROTEIN);
		log.info(instance.postIngredient(ingredient).toString());
	}
}
