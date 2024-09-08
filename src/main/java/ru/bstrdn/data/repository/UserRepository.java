package ru.bstrdn.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bstrdn.data.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
