package com.blogApplication.service.post;

import com.blogApplication.data.models.Comment;
import com.blogApplication.data.models.Post;
import com.blogApplication.data.repository.PostRepository;
import com.blogApplication.service.cloud.CloudStorageService;
import com.blogApplication.web.dto.PostDto;
import com.blogApplication.web.exceptions.PostNotFoundException;
import com.blogApplication.web.exceptions.PostObjectIsNullException;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Autowired
    CloudStorageService cloudStorageService;



    @Override
    public Post savePost(PostDto postDto) throws PostObjectIsNullException {
        if(postDto == null){
            throw new PostObjectIsNullException("post cannot be null");
        }
        Post post = new Post();
        if (postDto.getImageFile() != null && !postDto.getImageFile().isEmpty()){
            Map<Object, Object> params = new HashMap<>();
            params.put("public_id", "blogApp/"+postDto.getImageFile().getName());
            params.put("overwrite", true);
            log.info("parameters --> {}", params );
            try{
             Map<?,?> uploadResult =   cloudStorageService.uploadImage(postDto.getImageFile(), ObjectUtils.asMap(
                        "public_id", "blogApp/" +extractFileName(Objects.requireNonNull(postDto.getImageFile().getName()))
             ));
             post.setCoverImageUrl(String.valueOf(uploadResult.get("url")));
             log.info("image url --> {}", uploadResult.get("url"));
            }catch (IOException e){
               e.printStackTrace();
            }
        }

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        log.info("object before saving --> {}", post);

//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.map(postDto, post);
//        log.info("post object after mapping --> {}", post);

        try{
            return postRepository.save(post);
        }catch (DataIntegrityViolationException dIVE){
            log.info("Exception occurred --> {}", dIVE.getMessage());
            throw  dIVE;
        }


    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public Post findById(Integer id) throws PostNotFoundException{

        if(id == null) {
            throw new NullPointerException("post Id cannot be null");
        }
            Optional<Post> post = postRepository.findById(id);
            if( post.isPresent()){
                return post.get();
            }else{
                throw new PostNotFoundException("Post with id --> {}");
            }

    }

    @Override
    public List<Post> findPostInDescOrder() {
        return postRepository.findByOrderByDateCreatedDesc();
    }

    @Override
    public void deletePostById(Integer id) {

    }

    @Override
    public Post addCommentToPost(Integer id, Comment comment) {
        return null;
    }


    private String extractFileName(String fileName){
        return fileName.split("\\.")[0];
    }
}
