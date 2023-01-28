package camila.peopleregistration.integration;

import camila.peopleregistration.model.address.entity.AddressEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class IntegrationCep {
    private final RestTemplate restTemplate;

    public AddressEntity findCep(String cep){
        return restTemplate.getForObject("/ws/".concat(cep) + "/json", AddressEntity.class);
    }


}
