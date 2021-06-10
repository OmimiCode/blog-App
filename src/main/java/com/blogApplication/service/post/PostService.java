package com.blogApplication.service.post;

import com.blogApplication.data.models.Comment;
import com.blogApplication.data.models.Post;
import com.blogApplication.web.dto.PostDto;
import com.blogApplication.web.exceptions.PostNotFoundException;
import com.blogApplication.web.exceptions.PostObjectIsNullException;

import java.util.List;

public interface PostService {
    Post savePost(PostDto postDto) throws PostObjectIsNullException;
    List<Post> findAllPost();
    Post updatePost(PostDto postDto);
    Post findById(Integer id) throws PostNotFoundException;
    List<Post> findPostInDescOrder();
    void deletePostById(Integer id);
    Post addCommentToPost(Integer id, Comment comment);

}
