package com.typetaskpro.core.repositories.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.typetaskpro.core.domain.image.model.DeviceImage;

@Repository
public interface DeviceImageRepository extends JpaRepository<DeviceImage, String> {

  @Transactional
  @Query(value = "SELECT * FROM devices_image WHERE device_id = ?1", nativeQuery = true )
  public Optional<DeviceImage> getDeviceImageByDeviceId(String deviceId);
}
