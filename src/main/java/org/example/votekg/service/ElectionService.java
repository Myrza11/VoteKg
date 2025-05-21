package org.example.votekg.service;

import org.example.votekg.dto.ElectionCandidateDTO;
import org.example.votekg.dto.ElectionDTO;
import org.example.votekg.dto.VoteDTO;
import org.example.votekg.model.Election;
import org.example.votekg.model.ElectionCandidate;
import org.example.votekg.model.User;
import org.example.votekg.model.Vote;
import org.example.votekg.repository.ElectionCandidateRepository;
import org.example.votekg.repository.ElectionRepository;
import org.example.votekg.repository.PostRepository;
import org.example.votekg.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionService {
    @Autowired
    private ElectionCandidateRepository candidateRepository;
    @Autowired
    private ElectionRepository electionRepository;
    @Autowired
    private VoteRepository voteRepository;

    public void createElection(ElectionDTO electionDTO) {
        Election election = new Election();
        election.setName(electionDTO.getName());
        election.setDescription(electionDTO.getDescription());
        electionRepository.save(election);
    }

    public void deleteElection(Long electionId) {
        electionRepository.deleteById(electionId);
    }

    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    public void createElectionCandidate(ElectionCandidateDTO electionCandidateDTO) {
        ElectionCandidate electionCandidate = new ElectionCandidate();
        electionCandidate.setName(electionCandidateDTO.getName());
        electionCandidate.setDescription(electionCandidateDTO.getDescription());
        electionCandidate.setImage(electionCandidate.getImage());
        electionCandidate.setElection(electionCandidate.getElection());
        electionCandidate.getElection().getCandidates().add(electionCandidate);
        candidateRepository.save(electionCandidate);
    }
    public List<ElectionCandidate> getAllElectionCandidates(Long electionId) {
        return electionRepository.getById(electionId).getCandidates();
    }
    public void deleteElectionCandidate(Long electionCandidateId) {
        candidateRepository.deleteById(electionCandidateId);
    }

    public void voteElection(VoteDTO voteDTO, User currentUser) {
        Election election = electionRepository.findById(voteDTO.getElectionId())
                .orElseThrow(() -> new RuntimeException("Election not found"));

        ElectionCandidate candidate = candidateRepository.findById(voteDTO.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        if (!election.getCandidates().contains(candidate)) {
            throw new RuntimeException("Candidate does not belong to this election");
        }

        Vote existingVote = voteRepository.findByUserAndElection(currentUser, election);

        if (existingVote != null) {
            if (existingVote.getCandidate().equals(candidate)) {
                voteRepository.delete(existingVote);
                return;
            } else {
                existingVote.setCandidate(candidate);
                voteRepository.save(existingVote);
                return;
            }
        }

        Vote vote = new Vote();
        vote.setElection(election);
        vote.setCandidate(candidate);
        vote.setUser(currentUser);
        voteRepository.save(vote);
    }


}
