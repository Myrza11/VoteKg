package org.example.votekg.repository;

import org.example.votekg.model.Election;
import org.example.votekg.model.ElectionCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectionCandidateRepository extends JpaRepository<ElectionCandidate, Long> {
}
