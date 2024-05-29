package com.foodtraceai.service

import com.foodtraceai.model.FoodBus
import com.foodtraceai.repository.FoodBusRepository
import org.springframework.stereotype.Service

@Service
class FoodBusService(
    foodBusRepository: FoodBusRepository
) : BaseService<FoodBus>(foodBusRepository, "FoodBus")
