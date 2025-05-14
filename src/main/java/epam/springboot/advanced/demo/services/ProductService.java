package epam.springboot.advanced.demo.services;

import epam.springboot.advanced.demo.models.dto.ProductSaveDTO;
import epam.springboot.advanced.demo.models.entities.Product;

public interface ProductService {

    Long saveProduct(ProductSaveDTO dto);

    Product getProductById(Long id);

    Product getProductByName(String name);

    Product updateProductById(ProductSaveDTO dto, Long id);

    void deleteProduct(Long id);
}
