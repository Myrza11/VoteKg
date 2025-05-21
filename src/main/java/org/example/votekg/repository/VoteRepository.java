package org.example.votekg.repository;

import org.example.votekg.model.Election;
import org.example.votekg.model.User;
import org.example.votekg.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByUserAndElection(User user, Election election);
    void deleteByUserAndElection(User user, Election election);

}
