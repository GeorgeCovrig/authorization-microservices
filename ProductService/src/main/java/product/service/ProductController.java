package product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    List<String> products = List.of("Beans", "Oat cereals", "Bread", "Milk");


    @GetMapping("/all")
    public ResponseEntity<List<String>> getProducts() {
        return ResponseEntity.ok(products);

    }

}
