package com.shotskiy.airsystem.service;

import com.shotskiy.airsystem.entity.Airplane;
import com.shotskiy.airsystem.entity.AirCompany;

public interface AirplaneService extends AbstractEntityService<Airplane>{

    Airplane changeCompany(AirCompany airCompany, Long id);
}
