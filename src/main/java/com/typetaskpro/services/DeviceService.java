package com.typetaskpro.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.typetaskpro.domain.device.dto.DeviceRequestDTO;
import com.typetaskpro.domain.device.model.Device;
import com.typetaskpro.repository.DeviceRepository;

@Service
public class DeviceService {
  
  private DeviceRepository deviceRepository;

  public DeviceService(DeviceRepository deviceRepository) {
    this.deviceRepository = deviceRepository;
  }

  public Device validateAndSaveDevice(DeviceRequestDTO reqDevice) {
    String requestName = reqDevice.name();

    Optional<Device> optionalDevice = deviceRepository.findByName(requestName);

    if(!optionalDevice.isPresent()) {
      Device validDevice = new Device(requestName);
      deviceRepository.save(validDevice);
      return validDevice;
    }

    return optionalDevice.get();
  }
}
