package vkudryashov.webserver.dao;

import vkudryashov.webserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.JoinTable;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
