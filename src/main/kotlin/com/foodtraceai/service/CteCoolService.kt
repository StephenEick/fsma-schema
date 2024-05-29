// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.service

import com.foodtraceai.model.cte.CteCool
import com.foodtraceai.repository.CteCoolRepository
import org.springframework.stereotype.Service

@Service
class CteCoolService(
    cteCoolRepository: CteCoolRepository
) : BaseService<CteCool>(cteCoolRepository, "CteCool")
