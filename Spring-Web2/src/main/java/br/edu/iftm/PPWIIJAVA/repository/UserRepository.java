package br.edu.iftm.PPWIIJAVA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iftm.PPWIIJAVA.modelo.User;



public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);
}