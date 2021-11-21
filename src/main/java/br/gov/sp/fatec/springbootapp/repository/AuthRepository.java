package br.gov.sp.fatec.springbootapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.springbootapp.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    public Auth findByRole(String role);

}
