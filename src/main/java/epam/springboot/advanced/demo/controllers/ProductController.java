package epam.springboot.advanced.demo.controllers;

import epam.springboot.advanced.demo.models.dto.CustomerSaveDTO;
import epam.springboot.advanced.demo.models.dto.ProductSaveDTO;
import epam.springboot.advanced.demo.models.entities.Customer;
import epam.springboot.advanced.demo.models.entities.Product;
import epam.springboot.advanced.demo.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;


    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String,Long>> saveProduct(@Valid @RequestBody ProductSaveDTO dto) {
        return ResponseEntity.ok((
                Map.of("id", service.saveProduct(dto))
        ));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Product> getProductById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Product> updateProduct(
            @Valid @RequestBody ProductSaveDTO dto,
            @RequestParam("id") Long id) {
        return ResponseEntity.ok(service.updateProductById(dto, id));
    }

    @DeleteMapping()
    public void removeProduct(@RequestParam("id") Long id) {
        service.deleteProduct(id);
    }
}
