package br.gov.sp.fatec.springbootapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.springbootapp.controller.RegistrationDto;
import br.gov.sp.fatec.springbootapp.entity.Auth;
import br.gov.sp.fatec.springbootapp.entity.Profile;
import br.gov.sp.fatec.springbootapp.entity.Registration;
import br.gov.sp.fatec.springbootapp.repository.AuthRepository;
import br.gov.sp.fatec.springbootapp.repository.ProfileRepository;
import br.gov.sp.fatec.springbootapp.repository.RegistrationRepository;

@Service("ValidationService")
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    private RegistrationRepository regRepo;

    @Autowired
    private ProfileRepository profRepo;

    @Autowired
    private AuthRepository authRepo;

    @Transactional
    public Registration createRegistration(RegistrationDto registrationDto) {

        if (registrationDto.getEmail().isEmpty() || registrationDto.getPassword().isEmpty()
                || registrationDto.getName().isEmpty() || registrationDto.getCellphone().isEmpty()
                || registrationDto.getUniqueHash().isEmpty() || registrationDto.getUserAgent().isEmpty()
                || registrationDto.getNameBrowser().isEmpty() || registrationDto.getVersionBrowser().isEmpty()
                || registrationDto.getSystem().isEmpty() || registrationDto.getGpuModel().isEmpty()
                || registrationDto.getIp().isEmpty()) {

            throw new RuntimeException("Invalid params");
        }

        Registration registration = regRepo.findByEmail(registrationDto.getEmail());
        if (registration != null) {
            throw new RuntimeException("The email address must be unique");
        }

        Auth auth = authRepo.findByRole("USER");
        if (auth == null) {
            auth = new Auth();
            auth.setRole("USER");
            authRepo.save(auth);
        }

        registration = new Registration();
        registration.setEmail(registrationDto.getEmail());
        registration.setPassword(registrationDto.getPassword());
        registration.setName(registrationDto.getName());
        registration.setCellphone(registrationDto.getCellphone());
        registration.setUserAgent(registrationDto.getUserAgent());
        registration.setNameBrowser(registrationDto.getNameBrowser());
        registration.setVersionBrowser(registrationDto.getVersionBrowser());
        registration.setSystem(registrationDto.getSystem());
        registration.setGpuModel(registrationDto.getGpuModel());
        registration.setIp(registrationDto.getIp());
        registration.setAuths(new HashSet<Auth>());
        registration.getAuths().add(auth);
        regRepo.save(registration);

        Profile profile = profRepo.findByUniqueHash(registrationDto.getUniqueHash());
        if (profile == null) {
            profile = new Profile();
            profile.setUuid(UUID.randomUUID().toString());
            profile.setUniqueHash(registrationDto.getUniqueHash());
            profile.setRegistrations(new HashSet<Registration>());
        }

        profile.getRegistrations().add(registration);
        profRepo.save(profile);

        return registration;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Profile> findAllProfiles() {
        return profRepo.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Profile findProfileById(Long id) {
        Optional<Profile> profileOptional = profRepo.findById(id);
        if (profileOptional.isPresent()) {
            return profileOptional.get();
        }
        throw new RuntimeException("Profile not found for id: " + id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Profile findProfileByHash(String hash) {
        Profile profile = profRepo.findByUniqueHash(hash);
        if (profile != null) {
            return profile;
        }
        throw new RuntimeException("Profile not found for hash: " + hash);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Registration> findAllRegistrations() {
        return regRepo.findAll();
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'USER')")
    public Registration findRegistrationById(Long id) {
        Optional<Registration> registrationOptional = regRepo.findById(id);
        if (registrationOptional.isPresent()) {
            return registrationOptional.get();
        }
        throw new RuntimeException("Registration not found for id: " + id);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'USER')")
    public Long deleteProfile(Long id) {
        Optional<Profile> pOptional = profRepo.findById(id);
        if (pOptional.isPresent()) {
            profRepo.delete(pOptional.get());
            return id;
        }
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'USER')")
    public Long deleteRegistration(Long id) {
        Optional<Registration> rOptional = regRepo.findById(id);
        if (rOptional.isPresent()) {
            regRepo.delete(rOptional.get());
            return id;
        }
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'USER')")
    public Registration updateRegistration(RegistrationDto registration, Long id) {
        Optional<Registration> oldReg = regRepo.findById(id);
        if (oldReg.isPresent()) {
            Registration reg = oldReg.get();
            reg.setName(registration.getName());
            regRepo.save(reg);
            return reg;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca algum usuário com o username (e-mail)
        Registration registration = regRepo.findByEmail(username);
        if (registration == null) {
            throw new UsernameNotFoundException("Usuário " + username + " não encontrado!");
        }
        // Retorna um UserDetails com os dados para a autenticação
        return User.builder().username(username).password(registration.getPassword())
                // Cria uma lista do tipo String com cada autorização do usuário
                .authorities(registration.getAuths().stream().map(Auth::getRole).collect(Collectors.toList())
                        .toArray(new String[registration.getAuths().size()]))
                .build();
    }
}