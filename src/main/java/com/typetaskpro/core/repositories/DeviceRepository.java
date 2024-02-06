package com.typetaskpro.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.typetaskpro.core.domain.device.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

  /**
   * Is used to find a Device object
   * by its name in the application database.
   *
   * @param name of the object to be found on the database.
   * @return Optional object containing the Device object if found
   * or an empty Optional object.
   */
  public Optional<Device> findByName(String name);
}
