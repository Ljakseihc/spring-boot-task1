package epam.springboot.advanced.demo.services.impl;

import epam.springboot.advanced.demo.models.dto.ProductSaveDTO;
import epam.springboot.advanced.demo.models.entities.Product;
import epam.springboot.advanced.demo.models.exceptions.ConflictWithExistingResourceException;
import epam.springboot.advanced.demo.repositories.ProductRepository;
import epam.springboot.advanced.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Long saveProduct(ProductSaveDTO dto) {
        var product = repository.save(new Product(
                dto.name(),
                dto.cost(),
                dto.isAvailable()
        ));
        return product.getId();
    }

    @Override
    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with id " + id + " have not found"));
    }

    @Override
    public Product getProductByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Product with name " + name + " have not found"));
    }

    @Override
    public Product updateProductById(ProductSaveDTO dto, Long id) {
        if(repository.findByName(dto.name()).isPresent()){
            throw new ConflictWithExistingResourceException("Product with name " + dto.name() + " does exist");
        }

        var product = this.getProductById(id);

        if(Objects.nonNull(dto.name())) product.setName(dto.name());
        if(Objects.nonNull(dto.cost())) product.setCost(dto.cost());
        if(Objects.nonNull(dto.isAvailable())) product.setIsAvailable(dto.isAvailable());

        return repository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
