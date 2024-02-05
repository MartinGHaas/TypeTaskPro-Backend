package com.typetaskpro.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.application.services.ImageService;
import com.typetaskpro.core.domain.image.model.DeviceImage;
import com.typetaskpro.core.domain.image.model.ProfilePictureImage;
import com.typetaskpro.core.domain.image.model.TaskImage;
import com.typetaskpro.core.domain.user.model.User;
import com.typetaskpro.core.repositories.image.DeviceImageRepository;
import com.typetaskpro.core.repositories.image.ProfilePictureRepository;
import com.typetaskpro.core.repositories.image.TaskImageRepository;

@RestController
@RequestMapping("images")
public class ImageController {
  
  private ImageService imageService;
  private ProfilePictureRepository profilePictureRepository;
  private DeviceImageRepository deviceImageRepository;
  private TaskImageRepository taskImageRepository;

  public ImageController(
    ImageService imageService,
    ProfilePictureRepository profilePictureRepository,
    DeviceImageRepository deviceImageRepository,
    TaskImageRepository taskImageRepository
  ) {
    this.imageService = imageService;
    this.profilePictureRepository = profilePictureRepository;
    this.deviceImageRepository = deviceImageRepository;
    this.taskImageRepository = taskImageRepository;
  }

  @GetMapping("/profile-picture/{imageId}")
  public ResponseEntity<byte[]> getProfilePicture(
    @PathVariable @NonNull String imageId
  ) {
    ProfilePictureImage image = imageService.getProfilePicture(imageId);
    return new ResponseEntity<>(image.getData(), getImageHeaders(), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<byte[]> getProfilePicture(
    @PathVariable long userId
  ) {
    
    byte[] data = profilePictureRepository.getProfilePictureByUserId(userId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
      .getData();

    return new ResponseEntity<>(data, getImageHeaders(), HttpStatus.OK);
  }

  @GetMapping("/device-image/{imageId}")
  public ResponseEntity<byte[]> getDeviceImage(
    @PathVariable @NonNull String imageId
  ) {

    DeviceImage image = imageService.getDeviceImage(imageId);

    return new ResponseEntity<>(image.getData(), getImageHeaders(), HttpStatus.OK);
  }

  @GetMapping("/device/{deviceId}")
  public ResponseEntity<byte[]> getDeviceImageByDeviceId(
    @PathVariable @NonNull String deviceId
  ) {

    byte[] data = deviceImageRepository.getDeviceImageByDeviceId(deviceId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
      .getData();

    return new ResponseEntity<>(data, getImageHeaders(), HttpStatus.OK);
  }

  @GetMapping("/tasks-image/{imageId}")
  public ResponseEntity<byte[]> getTaskImage(
    @PathVariable @NonNull String imageId
  ) {

    TaskImage image = imageService.getTaskImage(imageId);

    return new ResponseEntity<>(image.getData(), getImageHeaders(), HttpStatus.OK);
  }

  @GetMapping("/task/{taskId}")
  public ResponseEntity<byte[]> getTaskImageByTaskId(
    @PathVariable @NonNull String taskId
  ) {

    byte[] data = taskImageRepository.getTaskImageByTaskId(taskId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
      .getData();
    
    return new ResponseEntity<>(data, getImageHeaders(), HttpStatus.OK);
  }

  @PostMapping("/profile-picture/")
  public ResponseEntity<Void> uploadProfilePicture(
    @AuthenticationPrincipal UserDetails userDetails,
    @RequestBody MultipartFile file
  ) {

    imageService.saveProfilePicture(file, (User) userDetails);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/device/")
  public ResponseEntity<Void> uploadDeviceImage(
    @RequestBody @NonNull String deviceId,
    @RequestBody MultipartFile file
  ) {

    imageService.saveDeviceImage(file, deviceId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/task/")
  public ResponseEntity<Void> uploadTaskImage(
    @RequestBody @NonNull String taskId,
    @RequestBody MultipartFile file
  ) {

    imageService.saveTaskImage(file, taskId);
    return ResponseEntity.ok().build();
  }

  private HttpHeaders getImageHeaders() {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);
    return headers;
  }
}
