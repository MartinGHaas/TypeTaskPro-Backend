package com.typetaskpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.typetaskpro.domain.device.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

  @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM devices d WHERE d.name = :name")
  boolean nameAlreadyExists(@Param("name") String name);
}
