package br.gov.sp.fatec.springbootapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.springbootapp.entity.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    public Registration findByEmail(String email);

    public List<Registration> findByProfilesUniqueHash(String uniqueHash);

    @Query("select r from Registration r inner join r.profiles p where p.uniqueHash = ?1")
    public List<Registration> findByOneOfHashes(String hash);

    public List<Registration> findByAuthsRole(String auth);
}