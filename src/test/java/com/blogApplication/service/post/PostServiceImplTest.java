package com.blogApplication.service.post;

import com.blogApplication.data.models.Post;
import com.blogApplication.data.repository.PostRepository;
import com.blogApplication.web.dto.PostDto;
import com.blogApplication.web.exceptions.PostObjectIsNullException;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImpl postServiceImpl;

    Post testPost;

    @BeforeEach
    void setUp(){
        testPost = new Post();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenTheSaveMethodIsCalled_thenRepositoryIsCalledOnceTest() throws PostObjectIsNullException {
        when(postServiceImpl.savePost(new PostDto())).thenReturn(testPost);
        postServiceImpl.savePost(new PostDto());
        verify(postRepository, times(1) ).save(testPost);
    }


    @Test
    void whenFindAllMethodIsCalled_ReturnAListOfPosts(){
        List<Post> postList = new ArrayList<>();
        when(postServiceImpl.findAllPost()).thenReturn(postList);
        postServiceImpl.findAllPost();

        verify(postRepository, times(1)).findAll();
    }
}