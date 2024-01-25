package com.typetaskpro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.typetaskpro.domain.device.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
  
  public Optional<Device> findByName(String name);
}
