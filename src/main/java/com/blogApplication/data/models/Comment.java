package com.blogApplication.data.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.type.UUIDBinaryType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private Integer id;

    private String commentator;

    @Column(nullable = false, length = 150)
    private String content;

    private LocalDate dataCreated;

    public Comment(String commentator, String content) {
        this.commentator = commentator;
        this.content= content;
    }
}
