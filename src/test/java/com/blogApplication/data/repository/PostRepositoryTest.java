package com.blogApplication.data.repository;

import com.blogApplication.data.models.Author;
import com.blogApplication.data.models.Comment;
import com.blogApplication.data.models.Post;
import com.blogApplication.web.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
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



    @Test
    @Transactional
    @Rollback(value = false)
    void deletePostByIdTest(){
        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        log.info("post fetch from the database --> {}", savedPost);
        //delete post
        postRepository.deleteById(savedPost.getId());
        //fetch deleted POst
        Post deletedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(deletedPost).isNull();


    }



    @Test
    @Transactional
    void updatedSavedPostTest() {
        Post postToUpdate = postRepository.findById(42).orElse(null);
        assertThat(postToUpdate).isNotNull();
        log.info("post fetch from the database --> {}", postToUpdate);



        postToUpdate.setTitle("new title");
        postRepository.save(postToUpdate);
        assertThat(postToUpdate.getTitle()).isEqualTo("new title");

        log.info("post fetch from the database has been updated to --> {}", postToUpdate);

    }


    @Test
    @Transactional
    void updatePostAuthor(){
        Post postToUpdate = postRepository.findById(43).orElse(null);
        assertThat(postToUpdate).isNotNull();
        assertThat(postToUpdate.getAuthor()).isNull();
        log.info("post fetch from the database --> {}", postToUpdate);

        Author newAuthor = new Author();
        newAuthor.setFirstname("brown");
        newAuthor.setLastname("john");
        newAuthor.setEmail("jb@mail.com");
        newAuthor.setPhoneNumber("9739373");
        newAuthor.setProfession("teacher");


        postToUpdate.setAuthor(newAuthor);
        postRepository.save(postToUpdate);

        assertThat(postToUpdate.getAuthor()).isNotNull();
        assertThat(postToUpdate.getAuthor().getLastname()).isEqualTo("john");

        log.info("post fetch from the database has been updated to --> {}", postToUpdate);

    }

    @Test
    @Rollback(value = false)
    void addCommentTest(){
        Post postToCommentTo = postRepository.findById(43).orElse(null);
        assertThat(postToCommentTo).isNotNull();
        assertThat(postToCommentTo.getComments()).hasSize(0);


        Comment comment = new Comment("billy ", "nice post");
        Comment comment2 = new Comment("james ", "great post");


        postToCommentTo.addComment(comment, comment2);
        postRepository.save(postToCommentTo);

        assertThat(postToCommentTo).isNotNull();
        assertThat(postToCommentTo.getComments()).hasSize(2);
        log.info("commented posts -->{}", postToCommentTo);

    }

    @Test
    @Transactional
    @Rollback(value = false)
    void findAllPostInDescendingOrderTest(){
        List<Post> allPosts = postRepository.findByOrderByDateCreatedDesc();
        assertTrue(allPosts.get(0).getDateCreated().isAfter(allPosts.get(1).getDateCreated()));
        allPosts.forEach(post-> {
            log.info("post Date {}", post.getDateCreated());
        });
    }


}