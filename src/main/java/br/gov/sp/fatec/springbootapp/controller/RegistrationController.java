package br.gov.sp.fatec.springbootapp.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.gov.sp.fatec.springbootapp.entity.Registration;
import br.gov.sp.fatec.springbootapp.service.ValidationService;

@RestController
@RequestMapping(value = "/registration")
@CrossOrigin
public class RegistrationController {

    @Autowired
    private ValidationService validService;

    @GetMapping
    @JsonView(View.RegistrationAllView.class)
    public List<Registration> get() {
        return validService.findAllRegistrations();
    }

    @GetMapping(value = "/{id}")
    @JsonView(View.RegistrationAllView.class)
    public Registration getById(@PathVariable("id") Long id) {
        return validService.findRegistrationById(id);
    }

    @PostMapping
    @JsonView(View.RegistrationAllView.class)
    public ResponseEntity<Registration> create(@RequestBody RegistrationDto registration, UriComponentsBuilder uriComponentsBuilder) {
        Registration reg = validService.createRegistration(registration);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(
            uriComponentsBuilder.path("/registration/" + reg.getId()).build().toUri()
        );
        return new ResponseEntity<Registration>(reg, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @JsonView(View.RegistrationAllView.class)
    public ResponseEntity<Registration> updateRegistration(@RequestBody RegistrationDto registration, @PathVariable Long id) {
        Registration reg = validService.updateRegistration(registration, id);
        if (reg != null) {
            return new ResponseEntity<Registration>(reg, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteRegistration(@PathVariable Long id) {
        if (validService.deleteRegistration(id) != null) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
