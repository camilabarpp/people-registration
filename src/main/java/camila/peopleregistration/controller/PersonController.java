package camila.peopleregistration.controller;

import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;
import camila.peopleregistration.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @ApiOperation("Show all people")
    public List<PersonResponse> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Show person by id")
    public PersonResponse findById(@PathVariable Long id) {
        return personService.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Create person")
    public PersonResponse create(@RequestBody @Valid PersonRequest personRequest) {
        return personService.create(personRequest);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update person")
    public PersonResponse update(@PathVariable Long id, @RequestBody @Valid PersonRequest personRequest) {
        return personService.update(id, personRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Delete person by ID")
    public void deleteById(@PathVariable Long id) {
        personService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Delete all people")
    public void deleteAll() {
        personService.deleteAll();
    }
}
