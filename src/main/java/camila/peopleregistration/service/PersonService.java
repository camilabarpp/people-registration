package camila.peopleregistration.service;

import camila.peopleregistration.model.person.mapper.PersonMapper;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;
import camila.peopleregistration.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static camila.peopleregistration.model.person.mapper.PersonMapper.toEntity;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

//    public Flux<PersonResponse> findAll() {
//        return personRepository.findAll()
//                .map(PersonMapper::fromEntity);
//    }
//
//    public Mono<PersonResponse> findById(String id) {
//        return personRepository.findById(id)
//                .doOnSuccess(message -> log.info("ID '{}' found!", id))
//                .doOnError(error -> log.error("ID '{}' not found!", id))
//                .map(PersonMapper::fromEntity);
//    }
//
//    public Mono<PersonResponse> create(PersonRequest personRequest) {
//        return  personRepository.save(toEntity(personRequest))
//                .doOnSuccess(message -> log.info(personRequest.getName(), " created!"))
//                .doOnError(error -> log.error(personRequest.getName(), " not created!"))
//                .map(PersonMapper::fromEntity);
//    }
//
//    public Mono<PersonResponse> update(String id, PersonRequest personRequest) {
//        return personRepository.findById(id)
//                .map(personEntityFounded -> {
//                    personEntityFounded.setName(personRequest.getName());
//                    personEntityFounded.setBirthdate(personRequest.getBirthdate());
//                    personEntityFounded.setAddresses(personRequest.getAddresses());
//                    return personEntityFounded;
//                })
//                .flatMap(personRepository::save)
//                .doOnSuccess(message -> log.info(personRequest.getName(), " updated!"))
//                .doOnError(error -> log.error(personRequest.getName(), " not updated!"))
//                .map(PersonMapper::fromEntity);
//    }
//
//    public Mono<Void> deleteById(String id) {
//        return personRepository.deleteById(id)
//                .doOnSuccess(message -> log.info("ID '{}' deleted!", id))
//                .doOnError(error -> log.error("ID '{}' not deleted!", id));
//    }
//
//    public Mono<Void> deleteAll() {
//        return personRepository.deleteAll()
//                .doOnSuccess(message -> log.info("All persons deleted!"))
//                .doOnError(error -> log.error("All persons not deleted!"));
//    }

    public PersonResponse create(PersonRequest personRequest) {
        return PersonMapper.fromEntity(personRepository.save(toEntity(personRequest)));
    }

    public PersonResponse update(Long id, PersonRequest personRequest) {
        return PersonMapper.fromEntity(personRepository.save(toEntity(personRequest)));
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }

    public PersonResponse findById(Long id) {
        return PersonMapper.fromEntity(personRepository.findById(id).get());
    }

//    public List<PersonResponse> findAll() {
//        return PersonMapper.fromEntity(personRepository.findAll());
//    }
}
