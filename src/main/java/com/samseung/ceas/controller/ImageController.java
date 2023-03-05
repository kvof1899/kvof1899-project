package com.samseung.ceas.controller;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samseung.ceas.model.ImageEntity;
import com.samseung.ceas.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImageController {
	
    private final ImageService imageService;

    @PostMapping("/image")
    public ResponseEntity<?> createBoard(@AuthenticationPrincipal String userId, @Validated @RequestParam("image") List<MultipartFile> files) throws Exception {
    	imageService.addImage(ImageEntity.builder()
                .build(), files);
    	
    	
        return ResponseEntity.ok().build();
    }
    
    @ResponseBody
    @GetMapping("/image/{id}")
    public UrlResource getImage(@AuthenticationPrincipal String userId, @PathVariable("id") long id)throws MalformedURLException {
    	
    	ImageEntity imageEntity = imageService.findImage(id).orElseThrow(RuntimeException::new);
    	
    	
        String imgPath = imageEntity.getStoredFileName();
        
        return new UrlResource("file:" + imgPath);
    }
}