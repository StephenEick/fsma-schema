// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.service

import com.foodtraceai.model.cte.CteHarvest
import com.foodtraceai.repository.CteHarvestRepository
import org.springframework.stereotype.Service

@Service
class CteHarvestService(
    cteHarvestRepository: CteHarvestRepository
) : BaseService<CteHarvest>(cteHarvestRepository, "CteHarvest")
