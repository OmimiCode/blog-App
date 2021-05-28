package com.blogApplication.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Comment {
    @Id
    private Long id;

    private String commentator;

    @Column(nullable = false, length = 150)
    private String content;

    private LocalDate dataCreated;

}
