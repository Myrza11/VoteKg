package org.example.votekg.repository;

import org.example.votekg.model.Election;
import org.example.votekg.model.ElectionCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectionRepository extends JpaRepository<Election, Long> {

}
