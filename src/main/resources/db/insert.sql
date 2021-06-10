SET FOREIGN_KEY_CHECKS = 0;

truncate table blog_post;
truncate table author;
truncate table comment;
truncate table author_posts;

INSERT INTO blog_post(id, title, content, date_created )
    VALUES(41, 'title post 1', 'Post content 1', "2021-06-03T11:39:29" ),
    (42, 'title post 2', 'Post content 2', "2021-06-03T12:39:29"),
    (43, 'title post 3', 'Post content 3', "2021-06-03T13:39:29"),
    (44, 'title post 4', 'Post content 4' , "2021-06-03T14:39:29"),
    (45, 'title post 5', 'Post content 5', "2021-06-03T15:39:29");

SET FOREIGN_KEY_CHECKS = 1;