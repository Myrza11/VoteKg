package org.example.votekg.repository;

import org.example.votekg.model.Post;
import org.example.votekg.model.PostLike;
import org.example.votekg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLIkeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByUserAndPost(User user, Post post);

}
