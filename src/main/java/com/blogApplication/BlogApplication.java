package com.blogApplication;

import com.blogApplication.service.util.CloudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApplication {
	@Autowired
	CloudinaryConfig cloudinaryConfig;

	public static void main(String[] args) { SpringApplication.run(BlogApplication.class, args); }
	@Bean
	public Cloudinary getCloudinary(){
		return new Cloudinary(ObjectUtils.asMap(
				"cloud_name", cloudinaryConfig.getCloudName(),
				"api_key", cloudinaryConfig.getApiKey(),
				"api_secret", cloudinaryConfig.getApiSecret()));
	}


}

