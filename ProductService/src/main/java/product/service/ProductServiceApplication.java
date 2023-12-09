package product.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient

@Log4j2
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@GetMapping("/resource")
	public String resource(@AuthenticationPrincipal Jwt jwt) {
		log.trace("***** JWT Headers: {}", jwt.getHeaders());
		log.trace("***** JWT Claims: {}", jwt.getClaims().toString());
		log.trace("***** JWT Token: {}", jwt.getTokenValue());
		return String.format("Resource accessed by: %s (with subjectId: %s)" ,
				jwt.getClaims().get("user_name"),
				jwt.getSubject());
	}


	static class AudienceValidator implements OAuth2TokenValidator<Jwt> {
		OAuth2Error error = new OAuth2Error("custom_code", "Custom error message", null);

		@Override
		public OAuth2TokenValidatorResult validate(Jwt jwt) {
			if (jwt.getAudience().contains("messaging")) {
				return OAuth2TokenValidatorResult.success();
			} else {
				return OAuth2TokenValidatorResult.failure(error);
			}
		}
	}


	OAuth2TokenValidator<Jwt> audienceValidator() {
		return new AudienceValidator();
	}
}
