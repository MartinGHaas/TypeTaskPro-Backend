package com.typetaskpro.controllers;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.typetaskpro.application.services.ImageService;
import com.typetaskpro.core.domain.images.model.Image;

@RestController
@RequestMapping("image")
public class ImageController {
  
  private ImageService imageService;

  public ImageController(
    ImageService imageService
  ) {
    this.imageService = imageService;
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<byte[]> getImage(
    @PathVariable @NonNull String id
  ) throws IOException {

    Image image = imageService.getImageById(id);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);

    return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Void> uploadImage(
    @RequestParam("image") MultipartFile file
  ) throws IOException{

    imageService.uploadImage(file);
    return ResponseEntity.ok().build();
  }
}
