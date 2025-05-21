package org.example.votekg.service;

import org.example.votekg.model.OfficialUser;
import org.example.votekg.repository.OfficialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuperUserService {
    @Autowired
    private OfficialUserRepository officialUserRepository;


    public List<OfficialUser> officialUsers() {
        return officialUserRepository.findAll();
    }

    public void makeOfficialUser(Long candidateId) {
        OfficialUser officialUser = officialUserRepository.getById(candidateId);
        officialUser.setVerified(true);
        officialUserRepository.save(officialUser);
    }

    public List<OfficialUser> getVerifiedUsers() {
        return officialUserRepository.findByIsVerifiedTrue();
    }

    public List<OfficialUser> getUnverifiedUsers() {
        return officialUserRepository.findByIsVerifiedFalse();
    }

}
