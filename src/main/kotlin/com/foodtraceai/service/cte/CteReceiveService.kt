// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.service.cte

import com.foodtraceai.model.cte.CteReceive
import com.foodtraceai.repository.cte.CteReceiveRepository
import com.foodtraceai.service.BaseService
import org.springframework.stereotype.Service

@Service
class CteReceiveService(
    cteReceiveRepository: CteReceiveRepository
) : BaseService<CteReceive>(cteReceiveRepository, "CteReceive")
