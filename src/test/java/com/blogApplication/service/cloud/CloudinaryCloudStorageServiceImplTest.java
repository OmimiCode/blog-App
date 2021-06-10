package com.blogApplication.service.cloud;

import com.blogApplication.web.dto.PostDto;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class CloudinaryCloudStorageServiceImplTest {
    @Autowired @Qualifier("cloudinary")
    CloudStorageService cloudStorageServiceImpl;


    @BeforeEach
    void setUp() {

    }


    @Test
    void uploadImageTest(){
        //define a file
        File file = new File("/home/oluwanishola/IdeaProjects/blogApplication/src/main/resources/static/asset/images/blog-image1.jpg");

        assertThat(file.exists()) .isTrue();

        Map<Object, Object> params = new HashMap<>();
        params.put("public_id", "blogApp/blogapp");
        params.put("overwrite", "true");

        try{
            cloudStorageServiceImpl.uploadImage(file, params);
        }catch (IOException e){
            log.info("error occurred --> {}", e.getMessage());
        }

    }


    @Test
    void uploadMultipartImageFileTest() throws IOException {
        PostDto postDto = new PostDto();
        postDto.setTitle("Test");
        postDto.setContent("Test");

        Path path = Paths.get("/home/oluwanishola/IdeaProjects/blogApplication/src/main/resources/static/asset/images/blog-image1.jpg");
        assertThat(path.toFile().exists()).isTrue();
        MultipartFile multipartFile = new MockMultipartFile("blog-image1.jpg","blog-image1.jpg" , "img/jpg",Files.readAllBytes(path));
        log.info("multipart Object created --> {}", multipartFile);
        assertThat(multipartFile).isNotNull();
        assertThat(multipartFile.isEmpty()).isFalse();
        postDto.setImageFile(multipartFile);

        log.info("File name --> {}", postDto.getImageFile().getOriginalFilename());

        cloudStorageServiceImpl.uploadImage(multipartFile, ObjectUtils.asMap(
                "public_id", "blogApp/"+extractFileName(Objects.requireNonNull(postDto.getImageFile().getOriginalFilename()))
        ));
        assertThat(postDto.getImageFile().getOriginalFilename()).isEqualTo("blog-image1.jpg");
    }
    private String extractFileName(String fileName){
        return fileName.split("\\.")[0];
    }

}