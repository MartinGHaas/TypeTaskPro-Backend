package com.typetaskpro.core.cases;

import com.typetaskpro.core.domain.device.dto.RequestDeviceDTO;
import com.typetaskpro.core.domain.device.model.Device;

public interface DeviceUseCase {
  
  Device validateAndSaveDevice(RequestDeviceDTO reqDevice);
}
