package com.typetaskpro.application.services;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.core.cases.ImageDataUseCase;
import com.typetaskpro.core.domain.device.model.Device;
import com.typetaskpro.core.domain.image.model.DeviceImage;
import com.typetaskpro.core.domain.image.model.ProfilePictureImage;
import com.typetaskpro.core.domain.image.model.TaskImage;
import com.typetaskpro.core.domain.task.model.Task;
import com.typetaskpro.core.domain.user.model.User;
import com.typetaskpro.core.exceptions.ImageServiceException;
import com.typetaskpro.core.repositories.DeviceRepository;
import com.typetaskpro.core.repositories.TaskRepository;
import com.typetaskpro.core.repositories.UserRepository;
import com.typetaskpro.core.repositories.image.DeviceImageRepository;
import com.typetaskpro.core.repositories.image.ProfilePictureRepository;
import com.typetaskpro.core.repositories.image.TaskImageRepository;

@Service
public class ImageService implements ImageDataUseCase {

  private UserRepository userRepository;
  private ProfilePictureRepository profilePictureRepository;
  private DeviceRepository deviceRepository;
  private DeviceImageRepository deviceImageRepository;
  private TaskRepository taskRepository;
  private TaskImageRepository taskImageRepository;

  public ImageService(
    UserRepository userRepository,
    ProfilePictureRepository profilePictureRepository,
    DeviceRepository deviceRepository,
    DeviceImageRepository deviceImageRepository,
    TaskRepository taskRepository,
    TaskImageRepository taskImageRepository
  ) {
    this.userRepository = userRepository;
    this.profilePictureRepository = profilePictureRepository;
    this.deviceRepository = deviceRepository;
    this.deviceImageRepository = deviceImageRepository;
    this.taskRepository = taskRepository;
    this.taskImageRepository = taskImageRepository;
  }

  public void saveProfilePicture(MultipartFile file, String username) {

    try {
      User user = userRepository.findUserByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      
      user.setProfilePicture(new ProfilePictureImage(file.getBytes()));

      userRepository.save(user);
    } catch(IOException e) {
      throw new ImageServiceException(e.getMessage());
    }
  }

  public void saveDeviceImage(MultipartFile file, @NonNull String deviceId) {

    try {

      Device device = deviceRepository.findById(deviceId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      
      device.setImage(new DeviceImage(file.getBytes()));
    } catch(IOException e) {
      throw new ImageServiceException(e.getMessage());
    }
  }

  public void saveTaskImage(MultipartFile file, @NonNull String taskId) {

    try {

      Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      
      task.setImage(new TaskImage(file.getBytes()));
    } catch(IOException e) {
      throw new ImageServiceException(e.getMessage());
    }
  }

  public ProfilePictureImage getProfilePicture(@NonNull String id) {
    return profilePictureRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
  
  public DeviceImage getDeviceImage(@NonNull String id) {
    return deviceImageRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public TaskImage getTaskImage(@NonNull String id) {
    return taskImageRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
