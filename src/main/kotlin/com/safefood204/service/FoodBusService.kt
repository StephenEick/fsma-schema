package com.safefood204.service

import com.safefood204.model.FoodBus
import com.safefood204.repository.FoodBusRepository
import org.springframework.stereotype.Service

@Service
class FoodBusService(
    foodBusRepository: FoodBusRepository
) : BaseService<FoodBus>(foodBusRepository, "FoodBus")
