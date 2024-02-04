package com.typetaskpro.core.repositories.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.typetaskpro.core.domain.image.model.DeviceImage;

@Repository
public interface DeviceImageRepository extends JpaRepository<DeviceImage, String> {
}
