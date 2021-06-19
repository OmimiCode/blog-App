package com.blogApplication.web.controllers;

import com.blogApplication.data.models.Post;
import com.blogApplication.service.post.PostService;
import com.blogApplication.web.dto.PostDto;
import com.blogApplication.web.exceptions.PostNotFoundException;
import com.blogApplication.web.exceptions.PostObjectIsNullException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class PostController {

    @Autowired
    PostService postServiceImpl;

    @GetMapping("/posts")
    public String getIndex(Model model){
        List<Post> postList = postServiceImpl.findPostInDescOrder();
        model.addAttribute("postList", postList);
        return "index";
    }

    @GetMapping("posts/create")
    public String getPostForm( Model model){
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("error", false);
        return "create";
    }

    @PostMapping("posts/save")
    public String savePost(@ModelAttribute("postDto") @Valid PostDto postDto, BindingResult bindingResult, Model model){
        log.info("Post Dto received -->{}",postDto);
        if(bindingResult.hasErrors()){
            return "create";
        }
        try {
            postServiceImpl.savePost(postDto);
        }catch (PostObjectIsNullException POINE){
            log.info("Exception occurred --> {}", POINE.getMessage());
        }catch(DataIntegrityViolationException newPOINE){
            log.info("constraint exception occurred --> {}",newPOINE.getMessage() );
            model.addAttribute("error", true);
            model.addAttribute("errorMessage","title can not be accepted, it exists in our database");
            return "create";
        }
        return "redirect:/posts/";
    }

    @ModelAttribute
    public void createPostModel(Model model){
        model.addAttribute("postDto", new PostDto());
    }

    @RequestMapping(value = "posts/{id}", method = RequestMethod.GET)
    public String getAllPostDetailsById(@PathVariable("id") Integer id, Model model) throws  PostNotFoundException {
        log.info("Invoked method: get with ID--> {} " ,id);
        log.info("Request for a post path --> {}", id);
        log.warn("Searching for user with ID -->{} ", id);
        try{
            Post savedPost = postServiceImpl.findById(id);
            model.addAttribute("postInfo", savedPost);
        }catch(PostNotFoundException px){
            log.info("exception occurred");
        }
        return "post";
    }


}

