package guru.springfamework.services;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerByFirstName(String firstName);

    CustomerDto createNewCustomer(CustomerDto customerDto);

    CustomerDto getCustomerById(Long id);

    CustomerDto saveCustomerByDTO(Long id, CustomerDto customerDTO);

    CustomerDto patchCustomer(Long id, CustomerDto customerDTO);

    void deleteCustomerById(Long id);
}
