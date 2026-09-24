package co.uk.byjoio.mvc.febe.repository;

import co.uk.byjoio.mvc.febe.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

}
