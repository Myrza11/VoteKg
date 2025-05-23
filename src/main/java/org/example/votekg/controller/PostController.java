package org.example.votekg.controller;

import org.example.votekg.dto.PostDTO;
import org.example.votekg.model.Post;
import org.example.votekg.model.User;
import org.example.votekg.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@ModelAttribute PostDTO postDTO, @AuthenticationPrincipal User currentUser) {
        postService.createPost(postDTO, currentUser);
        return ResponseEntity.ok("Пост успешно создан");
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal User currentUser) {
        postService.deletePost(postId, currentUser);
        return ResponseEntity.ok("Пост успешно удалён");
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPost() {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @AuthenticationPrincipal User currentUser) {
        postService.likePost(postId, currentUser);
        return ResponseEntity.ok("Вы поставили лайк посту");
    }

    @PostMapping("/dislike/{postId}")
    public ResponseEntity<String> dislikePost(@PathVariable Long postId, @AuthenticationPrincipal User currentUser) {
        postService.dislikePost(postId, currentUser);
        return ResponseEntity.ok("");
    }

    @PostMapping("/users-post")
    public ResponseEntity<List<Post>> getPostsByUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.getAllUserPosts(currentUser));
    }
}
