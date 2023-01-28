package camila.peopleregistration.controller;

import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.service.AddressService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/address")
@AllArgsConstructor
public class AddressController {

    private final AddressService service;

    @PostMapping("/{id}")
    public AddressEntity criarEndereco(@RequestBody AddressEntity endereco, @PathVariable Long id) {
        return service.createAddress(endereco, id);
    }

    @GetMapping("/{id}/addresses")
    @ApiOperation("Show all addresses by person id")
    public List<AddressEntity> getAddressesByPersonId(@PathVariable Long id) {
        return service.getAddressesByPersonId(id);
    }
}
