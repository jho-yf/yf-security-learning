package cn.jho.security.uaa.repository;

import cn.jho.security.uaa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findOptionalByUsername(String username);

}
