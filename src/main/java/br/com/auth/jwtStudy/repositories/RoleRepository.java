package br.com.auth.jwtStudy.repositories;

import br.com.auth.jwtStudy.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Optional<Role> findByDescription(String description);
}
