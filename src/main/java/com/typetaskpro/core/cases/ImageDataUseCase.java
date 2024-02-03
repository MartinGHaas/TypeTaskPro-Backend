package com.typetaskpro.core.cases;

import org.springframework.web.multipart.MultipartFile;

import com.typetaskpro.core.domain.images.model.Image;

public interface ImageDataUseCase {
  
  void uploadImage(MultipartFile file);

  Image getImageById(String id);
}
