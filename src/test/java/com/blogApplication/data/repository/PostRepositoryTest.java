package com.blogApplication.data.repository;

import com.blogApplication.data.models.Author;
import com.blogApplication.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp(){

    }
    @Test
    void savePostToDBTest(){

        Post post = new Post();

        post.setTitle("What is FinTech");
        post.setContent("Lorem rnare id, congugestas ante vehicula luctus. Aliquam sodales tortor eget tellus gravida rhoncus. Phasellus nunc sem, pelle");
        log.info("Created a blog post --> {}", post) ;
        postRepository.save(post);
        assertThat(post.getId()).isNotNull();
    }

    @Test
    void testToThrowExceptionsWhenSavingDuplicatePostsContent(){

        Post post = new Post();

        post.setTitle("What is FinTech");
        post.setContent("Lorem rnare id, congugestas ante vehicula luctus. Aliquam sodales tortor eget tellus gravida rhoncus. Phasellus nunc sem, pelle");
        postRepository.save(post);
        assertThat(post.getId()).isNotNull();
        log.info("Created a blog post --> {}", post) ;


        Post post2 = new Post();
        post2.setTitle("What is FinTech");
        post2.setContent("Lorem rnare id, congugestas ante vehicula luctus. Aliquam sodales tortor eget tellus gravida rhoncus. Phasellus nunc sem, pelle");
        log.info("Created a blog post --> {}", post2) ;
        assertThrows(DataIntegrityViolationException.class,()-> postRepository.save(post2));


    }

    @Test
    void whenPOstIsSave_ThenSaveAuthor(){
        Post post = new Post();

        post.setTitle("What is FinTech");
        post.setContent("Lorem rnare id, congugestas ante vehicula luctus. Aliquam sodales tortor eget tellus gravida rhoncus. Phasellus nunc sem, pelle");
        log.info("Created a blog post --> {}", post) ;

        Author author = new Author();
        author.setFirstname("john");
        author.setLastname("wick");
        author.setEmail("jwick@mail.com");
        author.setPhoneNumber("0928373");

        //map relationships
        post.setAuthor(author);
        author.addPost(post);


        postRepository.save(post);
        log.info("post after saving --> {}", post);
    }


    @Test
    void findAllPostInDbTest(){
        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);

    }



}