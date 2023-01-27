package camila.peopleregistration.controller;

import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.service.AddressService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/api/cep")
@AllArgsConstructor
public class AddressController {

    private final AddressService service;

//    @GetMapping("/{cep}")
//    @ApiOperation("Get a cep")
//    public AddressEntity getCep(@PathVariable String cep) {
//        log.info("Mostrando dados sobre o CEP " + cep);
//        return service.consultarCep(cep);
//    }
}
