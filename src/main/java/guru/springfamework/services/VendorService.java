package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDto;
import guru.springfamework.api.v1.model.VendorListDto;

public interface VendorService {
    VendorDto getVendorById(Long id);

    VendorListDto getAllVendors();

    VendorDto createNewVendor(VendorDto vendorDTO);

    VendorDto saveVendorByDTO(Long id, VendorDto vendorDTO);

    VendorDto patchVendor(Long id, VendorDto vendorDTO);

    void deleteVendorById(Long id);
}
