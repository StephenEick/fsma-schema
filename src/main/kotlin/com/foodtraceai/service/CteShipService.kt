// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.service

import com.foodtraceai.model.cte.CteShip
import com.foodtraceai.repository.CteShipRepository
import org.springframework.stereotype.Service

@Service
class CteShipService(
    cteShipRepository: CteShipRepository
) : BaseService<CteShip>(cteShipRepository, "CteShip")
