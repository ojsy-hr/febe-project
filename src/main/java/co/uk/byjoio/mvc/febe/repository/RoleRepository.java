package co.uk.byjoio.mvc.febe.repository;

import co.uk.byjoio.mvc.febe.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * @param role name to search
     * @return role object
     */
    Optional<Role> findByRole(String role);
}
