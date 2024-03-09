package net.codejava.UserRegistration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IntroRepository extends JpaRepository<Intro, Long> {
        @Query("SELECT u FROM Intro u WHERE u.userId = ?1")
        Intro findByUserId(Long userId);
}
