package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDto;
import guru.springfamework.api.v1.model.VendorListDto;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "This is my Vendor Controller")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @ApiOperation(value = "This will get a list of vendors.", notes = "These are some notes about the API.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDto getAllVendors() {
        return vendorService.getAllVendors();
    }


    @ApiOperation(value = "This will get a vendor by id.")
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDto getVendorById(@PathVariable Long id){
        return vendorService.getVendorById(id);
    }

    @ApiOperation(value = "This will create new vendor.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDto createNewVendor(@RequestBody VendorDto vendorDto) {
        return vendorService.createNewVendor(vendorDto);
    }

    @ApiOperation(value = "This will update a vendor.")
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDto updateVendor(@PathVariable Long id, @RequestBody VendorDto vendorDto){
        return vendorService.saveVendorByDTO(id, vendorDto);
    }

    @ApiOperation(value = "This will update existing vendor.")
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDto pathVendor(@PathVariable Long id, @RequestBody VendorDto vendorDto){
        return vendorService.patchVendor(id, vendorDto);
    }

    @ApiOperation(value = "This will delete a vendor by id.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }

}
