package com.shotskiy.airsystem.service;

import com.shotskiy.airsystem.entity.AirCompany;
import com.shotskiy.airsystem.entity.Airplane;

public interface AirplaneService extends AbstractEntityService<Airplane> {
    Airplane changeCompany(AirCompany airCompany, Long id);
}
