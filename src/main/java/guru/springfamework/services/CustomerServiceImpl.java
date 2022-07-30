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
        return modelMapper.map(customerRepository.findCustomerByFirstName(firstName), CustomerDto.class);
    }

    @Override
    public CustomerDto createNewCustomer(CustomerDto customerDto) {
        Customer map = modelMapper.map(customerDto, Customer.class);

        Customer save = customerRepository.save(map);

        CustomerDto customerDto1 = modelMapper.map(save, CustomerDto.class);

        customerDto1.setCustomerUrl("/api/v1/customer/" + save.getId());

        return customerDto1;
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).get();

        CustomerDto map = modelMapper.map(customer, CustomerDto.class);

        map.setCustomerUrl("/api/v1/customer/" + id);

        return map;
    }
}


