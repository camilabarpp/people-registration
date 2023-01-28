package camila.peopleregistration.controller;

import camila.peopleregistration.model.address.entity.AddressEntity;
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

    @GetMapping("/{cep}")
    @ApiOperation("Get a cep")
    public AddressEntity getCep(@PathVariable String cep) {
        log.info("Mostrando dados do CEP " + cep);
        return service.findCep(cep);
    }

    @PostMapping
    public AddressEntity createAddress(@RequestBody AddressEntity addressEntity) {
        return service.createAddress(addressEntity);
    }

//    @GetMapping("/idPerson")
//    public List<AddressEntity> listPersonAddress(@PathVariable Long idPerson) {
//        return service.listPersonAddress(idPerson);
//    }
}
