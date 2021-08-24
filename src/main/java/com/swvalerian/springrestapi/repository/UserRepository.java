package com.swvalerian.springrestapi.repository;

import com.swvalerian.springrestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Наследования этого интерфейса - является достаточным в реализации слоя для работы с БД.
// Теперь за нас весь геморой (МАГИю) делает СПРИНГ!
public interface UserRepository extends JpaRepository<User, Long> {
}
