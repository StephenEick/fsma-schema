package com.foodtraceai.service

import com.foodtraceai.model.Location
import com.foodtraceai.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(
    locationRepository: LocationRepository
) : BaseService<Location>(locationRepository, "Location")
