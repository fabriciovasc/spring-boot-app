package br.gov.sp.fatec.springbootapp.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.gov.sp.fatec.springbootapp.controller.RegistrationDto;
import br.gov.sp.fatec.springbootapp.entity.Profile;
import br.gov.sp.fatec.springbootapp.entity.Registration;

public interface ValidationService extends UserDetailsService {

    public Registration createRegistration(RegistrationDto registration);

    public List<Profile> findAllProfiles();

    public Profile findProfileById(Long id);

    public Profile findProfileByHash(String hash);

    public List<Registration> findAllRegistrations();

    public Registration findRegistrationById(Long id);

    public Long deleteProfile(Long id);

    public Long deleteRegistration(Long id);

    public Registration updateRegistration(RegistrationDto registration, Long id);
    
}

