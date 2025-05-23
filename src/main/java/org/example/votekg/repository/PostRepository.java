package org.example.votekg.repository;

import org.example.votekg.model.Post;
import org.example.votekg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteByIdAndUser(Long id, User user);

    List<Post> findAllByUser(User user);
}
