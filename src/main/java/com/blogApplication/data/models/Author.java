package com.blogApplication.data.models;

import com.sun.istack.NotNull;
import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)

    private String lastname;

    private String firstname;

    private String profession;
    @Column(unique = true)

    private String email;

    private String phoneNumber;
    @OneToMany
    @ToString.Exclude
    private List<Post> posts;

    public void addPost(Post post){
        if(posts == null ){
            posts = new ArrayList<>();
        }else{
            posts.add(post);
        }
    }

}
