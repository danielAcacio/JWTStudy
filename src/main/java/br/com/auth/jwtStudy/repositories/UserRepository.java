package br.com.auth.jwtStudy.repositories;

import br.com.auth.jwtStudy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUserName(String userName);
    public Boolean existsByEmail(String email);
    public Boolean existsByUserName(String userName);
}
