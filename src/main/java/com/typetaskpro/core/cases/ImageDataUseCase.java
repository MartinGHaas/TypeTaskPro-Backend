package com.typetaskpro.core.cases;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import com.typetaskpro.core.domain.image.model.DeviceImage;
import com.typetaskpro.core.domain.image.model.ProfilePictureImage;
import com.typetaskpro.core.domain.image.model.TaskImage;

public interface ImageDataUseCase {

  void saveProfilePicture(MultipartFile file, String username);

  void saveDeviceImage(MultipartFile file, @NonNull String deviceId);

  void saveTaskImage(MultipartFile file, @NonNull String taskId);

  ProfilePictureImage getProfilePicture(@NonNull String id);

  DeviceImage getDeviceImage(@NonNull String id);

  TaskImage getTaskImage(@NonNull String id);
}
