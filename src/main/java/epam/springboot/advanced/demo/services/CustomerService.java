package epam.springboot.advanced.demo.services;

import epam.springboot.advanced.demo.models.dto.CustomerSaveDTO;
import epam.springboot.advanced.demo.models.entities.Customer;

public interface CustomerService {

    Long saveCustomer(CustomerSaveDTO dto);

    Customer getCustomerById(Long id);

    Customer getCustomerByEmail(String email);

    Customer updateCustomer(CustomerSaveDTO dto, Long id);

    void deleteCustomer(Long id);

    Customer addProductToCustomer(Long customerId, Long productId);
}
