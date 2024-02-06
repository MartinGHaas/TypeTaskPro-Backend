package com.typetaskpro.application.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.typetaskpro.core.cases.DeviceUseCase;
import com.typetaskpro.core.domain.device.dto.RequestDeviceDTO;
import com.typetaskpro.core.domain.device.model.Device;
import com.typetaskpro.core.repositories.DeviceRepository;

@Service
public class DeviceService implements DeviceUseCase {
  
  private DeviceRepository deviceRepository;

  public DeviceService(DeviceRepository deviceRepository) {
    this.deviceRepository = deviceRepository;
  }

  /**
   * Validates the Device and saves it if
   * it already doesn't exist. If it exists,
   * it won't update the database.
   *
   * @param reqDevice a request of the Device.
   * @return an already existing Device in the
   * database, or it will create a new Device in the database and
   * return it.
   */
  public Device validateAndSaveDevice(RequestDeviceDTO reqDevice) {
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
