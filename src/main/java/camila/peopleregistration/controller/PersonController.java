package camila.peopleregistration.controller;

import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.mapper.PersonMapper;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;
import camila.peopleregistration.repository.PersonRepository;
import camila.peopleregistration.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

//@Validated
@RestController
@RequestMapping("/v1/person/")
@RequiredArgsConstructor
@Api(value = "People Registration API")
public class PersonController {

    private final PersonService personService;

    private final PersonRepository repos;

    @GetMapping
    @ApiOperation("Show all people")
//    public Flux<PersonResponse> findAll() {
//        return personService.findAll();
//    }
    public @ResponseBody List<PersonEntity> list() {
        return repos.findAll();
    }


    @GetMapping("/{id}")
    @ApiOperation("Show person by id")
//    public Mono<PersonResponse> findById(@PathVariable String id) {
//        return personService.findById(id);
//    }
    public PersonResponse findById(@PathVariable Long id) {
        return personService.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Create person")
//    public Mono<PersonResponse> create(@RequestBody @Valid PersonRequest personRequest) {
//        return personService.create(personRequest);
//    }
    public PersonResponse course(@RequestBody @Valid PersonRequest course) {
        return PersonMapper.fromEntity(repos.save(PersonMapper.toEntity(course)));
    }

    @PutMapping("/{id}")
    @ApiOperation("Update person")
//    public Mono<PersonResponse> update(@PathVariable String id, @RequestBody @Valid PersonRequest personRequest) {
//        return personService.update(id, personRequest);
//    }
    public PersonResponse update(@PathVariable Long id, @RequestBody @Valid PersonRequest personRequest) {
        return personService.update(id, personRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Delete person by ID")
//    public Mono<Void> deleteById(@PathVariable String id) {
//        return personService.deleteById(id);
//    }
    public void deleteById(@PathVariable Long id) {
        personService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Delete all people")
//    public Mono<Void> deleteAll() {
//        return personService.deleteAll();
//    }
    public void deleteAll() {
        personService.deleteAll();
    }
}
