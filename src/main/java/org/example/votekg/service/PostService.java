package org.example.votekg.service;

import org.example.votekg.dto.PostDTO;
import org.example.votekg.model.Post;
import org.example.votekg.model.PostLike;
import org.example.votekg.model.ReactionType;
import org.example.votekg.model.User;
import org.example.votekg.repository.PostLIkeRepository;
import org.example.votekg.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostLIkeRepository postLIkeRepository;

    public void createPost(PostDTO postDTO, User currentUser) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUser(currentUser);
        try {
            post.setImage(postDTO.getImage().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        postRepository.save(post);
    }

    public void deletePost(Long postId, User currentUser) {
        postRepository.deleteByIdAndUser(postId, currentUser);
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public void likePost(Long postId, User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Пост не найден"));

        PostLike existingLike = postLIkeRepository.findByUserAndPost(currentUser, post);

        if (existingLike != null && existingLike.getReactionType() == ReactionType.LIKE) {
            postLIkeRepository.delete(existingLike);
        } else if (existingLike != null && existingLike.getReactionType() == ReactionType.DISLIKE) {
            existingLike.setReactionType(ReactionType.LIKE);
        } else {
            PostLike postLike = new PostLike();
            postLike.setUser(currentUser);
            postLike.setReactionType(ReactionType.LIKE);
            postLike.setPost(post);
            postLIkeRepository.save(postLike);
        }
    }

    public void dislikePost(Long postId, User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Пост не найден"));

        PostLike existingReaction = postLIkeRepository.findByUserAndPost(currentUser, post);

        if (existingReaction != null && existingReaction.getReactionType() == ReactionType.DISLIKE) {
            postLIkeRepository.delete(existingReaction);
        }  else if (existingReaction != null && existingReaction.getReactionType() == ReactionType.LIKE) {
            existingReaction.setReactionType(ReactionType.DISLIKE);
        } else {
            PostLike postDislike = new PostLike();
            postDislike.setUser(currentUser);
            postDislike.setReactionType(ReactionType.DISLIKE);
            postDislike.setPost(post);
            postLIkeRepository.save(postDislike);
        }
    }


}
