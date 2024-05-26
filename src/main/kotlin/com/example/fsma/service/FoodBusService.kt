package com.example.fsma.service

import com.example.fsma.model.FoodBus
import com.example.fsma.repository.FoodBusRepository
import org.springframework.stereotype.Service

@Service
class FoodBusService(
    foodBusRepository: FoodBusRepository
) : BaseService<FoodBus>(foodBusRepository, "FoodBus")
