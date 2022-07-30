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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

        assertEquals(2, allCustomers.size());
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");

        Customer customer2 = new Customer();
        customer2.setId(2l);
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        //when
        List<CustomerDto> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(2, customerDTOS.size());

    }

    @Test
    public void getCustomerById() throws Exception {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

        //when
        CustomerDto customerDTO = customerService.getCustomerById(1L);

        assertEquals("Michale", customerDTO.getFirstName());
    }

    @Test
    public void createNewCustomer() throws Exception {

        //given
        CustomerDto customerDTO = new CustomerDto();
        customerDTO.setFirstName("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1l);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDto savedDto = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals("/api/v1/customer/1", savedDto.getCustomerUrl());
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
