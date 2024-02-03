package com.typetaskpro.application.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.core.cases.ImageDataUseCase;
import com.typetaskpro.core.domain.images.model.Image;
import com.typetaskpro.core.exceptions.ImageServiceException;
import com.typetaskpro.core.repositories.ImageRepository;

@Service
public class ImageService implements ImageDataUseCase{

  private ImageRepository imageRepository;

  public ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Override
  public void uploadImage(MultipartFile file) {
    
    try {
      Image image = new Image(file.getBytes());
      imageRepository.save(image);

    } catch(Exception e) {
      throw new ImageServiceException(e.getMessage());
    }
  }

  @SuppressWarnings("null")
  @Override
  public Image getImageById(String id) {
    return imageRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
