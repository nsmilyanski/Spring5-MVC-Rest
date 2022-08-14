package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDto;
import guru.springfamework.api.v1.model.VendorDto;
import guru.springfamework.api.v1.model.VendorListDto;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, ModelMapper modelMapper) {
        this.vendorRepository = vendorRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public VendorDto getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(e -> modelMapper.map(e, VendorDto.class))
                .map(vendorDto -> {
                    //set API URL
                    vendorDto.setVendorUrl("/api/v1/customer/" + id);
                    return vendorDto;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorListDto getAllVendors() {
        return new VendorListDto(vendorRepository.findAll()
                .stream().map(vendor -> modelMapper.map(vendor, VendorDto.class))
                .collect(Collectors.toList()));
    }

    @Override
    public VendorDto createNewVendor(VendorDto vendorDTO) {
        Vendor save = vendorRepository.save(modelMapper.map(vendorDTO, Vendor.class));

        return saveAndReturnDTO(save);
    }

    @Override
    public VendorDto saveVendorByDTO(Long id, VendorDto vendorDTO) {
        Optional<Vendor> byId = vendorRepository.findById(id);

        if (!byId.isPresent()) {
            throw new ResourceNotFoundException();
        }

        Vendor map = modelMapper.map(vendorDTO, Vendor.class);
        map.setId(id);
        map.setVendorUrl("/api/v1/vendors/" + id);

        return modelMapper.map(vendorRepository.save(map), VendorDto.class);
    }

    @Override
    public VendorDto patchVendor(Long id, VendorDto vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {

            if(vendorDTO.getName() != null){
                vendor.setName(vendorDTO.getName());
            }

            VendorDto map = modelMapper.map(vendorRepository.save(vendor), VendorDto.class);

            map.setVendorUrl("/api/v1/vendors/" + id);

            return map;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        if (!vendorRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException();
        }
        vendorRepository.deleteById(id);
    }

    private VendorDto saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDto returnDto = modelMapper.map(savedVendor, VendorDto.class);

        returnDto.setVendorUrl("/api/v1/customer/" + savedVendor.getId());

        return returnDto;
    }
}
