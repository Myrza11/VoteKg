package org.example.votekg.repository;

import org.example.votekg.model.OfficialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficialUserRepository extends JpaRepository<OfficialUser, Long> {
    List<OfficialUser> findByIsVerifiedTrue();
    List<OfficialUser> findByIsVerifiedFalse();
}
