package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDto;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerByFirstName(String firstName) {

        Customer customerByFirstName = customerRepository.findCustomerByFirstName(firstName);

        CustomerDto map = modelMapper.map(customerByFirstName, CustomerDto.class);
        map.setCustomerUrl( customerByFirstName.getCustomerUrl());

        return map;
    }

    @Override
    public CustomerDto createNewCustomer(CustomerDto customerDto) {
        Customer map = modelMapper.map(customerDto, Customer.class);

      return saveAndReturnDTO(map);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(e -> modelMapper.map(e, CustomerDto.class))
                .map(customerDTO -> {
                    //set API URL
                    customerDTO.setCustomerUrl("/api/v1/customer/" + id);
                    return customerDTO;
                })
                .orElseThrow(RuntimeException::new); //todo implement better exception handling
    }

    @Override
    public CustomerDto saveCustomerByDTO(Long id, CustomerDto customerDTO) {
        Customer map = modelMapper.map(customerDTO, Customer.class);
        map.setId(id);

        return saveAndReturnDTO(map);
    }

    @Override
    public CustomerDto patchCustomer(Long id, CustomerDto customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if(customerDTO.getFirstName() != null){
                customer.setFirstName(customerDTO.getFirstName());
            }

            if(customerDTO.getLastName() != null){
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDto map = modelMapper.map(customerRepository.save(customer), CustomerDto.class);

            map.setCustomerUrl("/api/v1/customer/" + id);

            return map;
        }).orElseThrow(RuntimeException::new); //todo implement better exception handling;
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDto saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDto returnDto = modelMapper.map(savedCustomer, CustomerDto.class);

        returnDto.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());

        return returnDto;
    }

}


