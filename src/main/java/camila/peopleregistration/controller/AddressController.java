package camila.peopleregistration.controller;

import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.service.AddressService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RestController
@RequestMapping("v1/person")
@AllArgsConstructor
public class AddressController {

    private final AddressService service;

    @GetMapping("/{personId}/address")
    @ApiOperation("Show all addresses by person id")
    public List<AddressEntity> getAddressesByPersonId(@PathVariable Long personId) {
        return service.getAddressesByPersonId(personId);
    }

    @PostMapping("/{personId}/address")
    @ResponseStatus(CREATED)
    public AddressEntity criarEndereco(@RequestBody AddressEntity endereco, @PathVariable Long personId) {
        return service.createNewAddress(endereco, personId);
    }

    @PutMapping("/{personId}/address/{addressId}")
    @ApiOperation("Update address by id")
    @ResponseStatus(CREATED)
    public AddressEntity updateAddress(@PathVariable Long personId, @RequestBody AddressEntity endereco, @PathVariable Long addressId) {
        return service.updateAddressByPersonId(endereco, personId, addressId);
    }

    @DeleteMapping("/{personId}/address/{addressId}")
    @ApiOperation("Delete address by id")
    @ResponseStatus(NO_CONTENT)
    public void deleteAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        service.deleteAddressByPersonId(personId, addressId);
    }
}
