package space.bumtiger.rest;

import java.text.SimpleDateFormat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import space.bumtiger.domain.Ingredient;


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

	public static void main(String... args) {
		HamburgerClient instance = new HamburgerClient();
		System.out.println(instance.getIngredientById("BNBD"));
	}
}
