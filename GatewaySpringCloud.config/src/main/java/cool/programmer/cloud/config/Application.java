package cool.programmer.cloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {

	@GetMapping("/")
	public String home() {
		serviceUrl();
		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private DiscoveryClient discoveryClient;

	public String serviceUrl() {
		List<ServiceInstance> list = discoveryClient.getInstances("STORES");
		if (list != null && list.size() > 0 ) {
			System.out.println(list.get(0).getUri().toString());
			return list.get(0).getUri().toString();
		}
		return null;
	}

	@Autowired
	private TokenRelayGatewayFilterFactory filterFactory;

//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route("resource", r -> r.path("/product-service", "")
//						.filters(f -> f.filter(filterFactory.apply())
//						)
//
//						.uri("lb://product-service"))
//				.build();
//	}
//
//	@Autowired
//	private TokenRelayGatewayFilterFactory filterFactory;

//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route("product-service", r -> r.path("/product-service")
//						.filters(f -> {
//							return f.filters(filterFactory.apply()).removeRequestHeader("Cookie");
//						})
//						// Prevents cookie being sent downstream
//						.uri("lb://product-service"))
//				.build();
//	}

}

