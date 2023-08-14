package com.ashgram.photogram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // JPA Query creation from method names
    User findByUsername(String username); // login 시, 아이디(username) 식별을 위함
}
