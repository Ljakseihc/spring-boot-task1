package epam.springboot.advanced.demo.controllers;

import epam.springboot.advanced.demo.models.dto.CustomerSaveDTO;
import epam.springboot.advanced.demo.models.entities.Customer;
import epam.springboot.advanced.demo.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String,Long>> saveCustomer(@Valid @RequestBody CustomerSaveDTO dto) {
        return ResponseEntity.ok((
                Map.of("id", service.saveCustomer(dto))
        ));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Customer> getCustomerById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Customer> updateCustomer(
            @Valid @RequestBody CustomerSaveDTO dto,
            @RequestParam("id") Long id) {
        return ResponseEntity.ok(service.updateCustomer(dto, id));
    }

    @DeleteMapping()
    public void removeCustomer(@RequestParam("id") Long id) {
        service.deleteCustomer(id);
    }

    @RequestMapping("/purchase")
    @GetMapping(produces = "application/json")
    public ResponseEntity<Customer> purchase(
            @RequestParam("customerId") Long customerId,
            @RequestParam("productId") Long productId) {
        return ResponseEntity.ok(service.addProductToCustomer(customerId, productId));
    }


}
