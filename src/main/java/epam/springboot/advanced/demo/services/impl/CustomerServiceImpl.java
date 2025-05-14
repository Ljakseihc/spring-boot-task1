package epam.springboot.advanced.demo.services.impl;

import epam.springboot.advanced.demo.models.dto.CustomerSaveDTO;
import epam.springboot.advanced.demo.models.entities.Customer;
import epam.springboot.advanced.demo.models.exceptions.ConflictWithExistingResourceException;
import epam.springboot.advanced.demo.repositories.CustomerRepository;
import epam.springboot.advanced.demo.services.CustomerService;
import epam.springboot.advanced.demo.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final ProductService productService;

    public CustomerServiceImpl(CustomerRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @Override
    public Long saveCustomer(CustomerSaveDTO dto) {
        var customer = repository.save(new Customer(
                dto.name(),
                dto.email(),
                dto.money()
        ));

        return customer.getId();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer with id " + id + " have not found"));
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Customer with email " + email + " have not found"));
    }

    @Override
    public Customer updateCustomer(CustomerSaveDTO dto, Long id) {
        if(repository.findByEmail(dto.email()).isPresent()) {
            throw new ConflictWithExistingResourceException("Customer with email " + dto.email() + " does exist");
        }

        var customer = this.getCustomerById(id);
        if(Objects.nonNull(dto.name())) customer.setName(dto.name());
        if(Objects.nonNull(dto.email())) customer.setEmail(dto.email());
        if(Objects.nonNull(dto.money())) customer.setMoney(dto.money());

        return repository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Customer addProductToCustomer(Long customerId, Long productId) {
        var product = productService.getProductById(productId);
        if(!product.getIsAvailable()) throw new ConflictWithExistingResourceException("Product with id " + productId + " is not available");

        var customer = this.getCustomerById(customerId);
        if(customer.getMoney().compareTo(product.getCost()) < 0) {
            throw new ConflictWithExistingResourceException("Not enough money for customer " + customerId);
        }

        customer.getProducts().add(product);
        return repository.save(customer);
    }
}
