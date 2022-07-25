package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDto;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;


    CustomerService customerService;

    ModelMapper modelMapper;

    List<Customer> customers;


    @Before
    public void setUp() throws Exception {
        modelMapper = new ModelMapper();

        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerRepository, modelMapper);

        customers = loadCustomers();
    }

    @Test
    public void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDto> allCustomers = customerService.getAllCustomers();

        Assert.assertEquals(2, allCustomers.size());
    }

    private List<Customer> loadCustomers() {
        List<Customer> customerDtoList = new ArrayList<>();

        Customer customer = new Customer();
        customer.setFirstName("Nikola");
        customer.setLastName("Smilyanski");
        customer.setCustomertUrl("/api/v1/customers/");

        Customer customer1 = new Customer();
        customer1.setFirstName("Nikola");
        customer1.setLastName("Smilyanski");
        customer1.setCustomertUrl("/api/v1/customers/");

        customerDtoList.add(customer);
        customerDtoList.add(customer1);

        return customerDtoList;
    }
}
