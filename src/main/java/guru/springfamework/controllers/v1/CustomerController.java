package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDto;
import guru.springfamework.api.v1.model.CustomerListDto;
import guru.springfamework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "This is my Customer Controller")
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "This will get a list of customers.", notes = "These are some notes about the API.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDto getAllCustomers(){
        List<CustomerDto> allCustomers = customerService
                .getAllCustomers();
        return new CustomerListDto(allCustomers);
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @GetMapping("/byName/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomerByFirstName(@PathVariable String firstName) {
        CustomerDto customerByFirstName = customerService.getCustomerByFirstName(firstName);
        return customerByFirstName;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createNewCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.createNewCustomer(customerDto);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDTO){
        return customerService.saveCustomerByDTO(id, customerDTO);
    }

    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto pathCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDTO){
        return customerService.patchCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}
