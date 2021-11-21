package br.gov.sp.fatec.springbootapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.springbootapp.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    public Profile findByUniqueHash(String uniqueHash);

}
