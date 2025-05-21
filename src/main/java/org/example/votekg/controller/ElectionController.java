package org.example.votekg.controller;

import lombok.Getter;
import org.example.votekg.dto.ElectionCandidateDTO;
import org.example.votekg.dto.ElectionDTO;
import org.example.votekg.dto.VoteDTO;
import org.example.votekg.model.Election;
import org.example.votekg.model.ElectionCandidate;
import org.example.votekg.model.User;
import org.example.votekg.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/election")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @PostMapping("/create")
    public void createElection(@RequestBody ElectionDTO electionDTO) {
        electionService.createElection(electionDTO);
    }

    @DeleteMapping("/delete/{electionId}")
    public void deleteElection(@PathVariable Long electionId) {
        electionService.deleteElection(electionId);
    }

    @GetMapping
    public List<Election> getAllElections() {
        return electionService.getAllElections();
    }

    @PostMapping("/candidate/create")
    public void createElectionCandidate(@RequestBody ElectionCandidateDTO electionCandidateDTO) {
        electionService.createElectionCandidate(electionCandidateDTO);
    }

    @GetMapping("/candidates/{electionId}")
    public List<ElectionCandidate> getAllElectionCandidates(@PathVariable Long electionId) {
        return electionService.getAllElectionCandidates(electionId);
    }

    @DeleteMapping("/candidate/delete/{candidateId}")
    public void deleteElectionCandidate(@PathVariable Long candidateId) {
        electionService.deleteElectionCandidate(candidateId);
    }

    @PostMapping("/vote")
    public void voteElection(@RequestBody VoteDTO voteDTO, @AuthenticationPrincipal User currentUser) {
        electionService.voteElection(voteDTO, currentUser);
    }

}
