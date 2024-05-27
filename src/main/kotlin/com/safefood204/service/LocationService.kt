package com.safefood204.service

import com.safefood204.model.Location
import com.safefood204.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(
    locationRepository: LocationRepository
) : BaseService<Location>(locationRepository, "Location")
