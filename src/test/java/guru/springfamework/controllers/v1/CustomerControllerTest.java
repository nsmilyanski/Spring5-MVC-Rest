package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CustomerDto;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServlet;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    List<CustomerDto> customers;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

       customers = loadCustomers();
    }

    private List<CustomerDto> loadCustomers() {
        List<CustomerDto> customerDtoList = new ArrayList<>();

        CustomerDto customer = new CustomerDto();
        customer.setFirstName("Nikola");
        customer.setLastName("Smilyanski");
        customer.setCustomertUrl("/api/v1/customers/");

        CustomerDto customer1 = new CustomerDto();
        customer1.setFirstName("Nikola");
        customer1.setLastName("Smilyanski");
        customer1.setCustomertUrl("/api/v1/customers/");

        customerDtoList.add(customer);
        customerDtoList.add(customer1);

        return customerDtoList;
    }

    @Test
    public void testGetAllCategories() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetByNameCategories() throws Exception {
        CustomerDto customer1 = new CustomerDto();
        customer1.setFirstName("Nikola");
        customer1.setLastName("Smilyanski");

        when(customerService.getCustomerByFirstName(anyString())).thenReturn(customer1);

        mockMvc.perform(get("/api/v1/customers/Nikola")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Nikola")));
    }


}