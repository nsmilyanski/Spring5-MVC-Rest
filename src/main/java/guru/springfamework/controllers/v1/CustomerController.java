package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDto;
import guru.springfamework.api.v1.model.CustomerListDto;
import guru.springfamework.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/customers/")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDto> getAllCategories(){
        List<CustomerDto> allCustomers = customerService
                .getAllCustomers();
        return new ResponseEntity<CustomerListDto>(new CustomerListDto(allCustomers), HttpStatus.OK);
    }

    @GetMapping("{firstName}")
    public ResponseEntity<CustomerDto> getCustomerByFirstName(@PathVariable String firstName) {
        return new ResponseEntity<CustomerDto>(customerService.getCustomerByFirstName(firstName), HttpStatus.OK);
    }
}
