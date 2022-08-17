package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDto;
import guru.springfamework.api.v1.model.VendorDto;
import guru.springfamework.api.v1.model.VendorListDto;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.domain.Vendor;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;


import static guru.springfamework.controllers.v1.VendorController.BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    List<VendorDto> vendors;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();

        vendors = loadVendors();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        VendorListDto vendorListDto = new VendorListDto(vendors);
        when(vendorService.getAllVendors()).thenReturn(vendorListDto);

        mockMvc.perform(get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void createNewVendor() throws Exception {
        VendorDto vendor = new VendorDto();
        vendor.setName("Nikola");

        VendorDto vendorDto = new VendorDto();
        vendorDto.setName(vendor.getName());
        vendorDto.setVendorUrl(BASE_URL + "/1");

        when(vendorService.createNewVendor(vendor)).thenReturn(vendorDto);

        mockMvc.perform(post(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Nikola")))
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        VendorDto vendor = new VendorDto();
        vendor.setName("Nikola");

        VendorDto vendorDto = new VendorDto();
        vendorDto.setName(vendor.getName());
        vendorDto.setVendorUrl(BASE_URL + "/1");

        when(vendorService.patchVendor(anyLong(),  any(VendorDto.class))).thenReturn(vendorDto);

        mockMvc.perform(patch(BASE_URL + "/1")
                        .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Nikola")))
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));

    }


        private List<VendorDto> loadVendors() {
        List<VendorDto> vendorDtos = new ArrayList<>();

        VendorDto nikola = new VendorDto();
        nikola.setName("Nikola");
        nikola.setVendorUrl("api/v1/vendors/1");

        VendorDto ivan = new VendorDto();
        ivan.setName("Ivan");
        ivan.setVendorUrl("api/v1/vendors/2");

        vendorDtos.add(nikola);
        vendorDtos.add(ivan);


        return vendorDtos;
    }
}