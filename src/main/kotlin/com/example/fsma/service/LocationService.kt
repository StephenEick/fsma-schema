package com.example.fsma.service

import com.example.fsma.model.Business
import com.example.fsma.model.Location
import com.example.fsma.repository.BusinessRepository
import com.example.fsma.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(
    locationRepository: LocationRepository
) : BaseService<Location>(locationRepository, "Location")
