package com.foodtraceai.service

import com.foodtraceai.model.cte.CteTrans
import com.foodtraceai.repository.CteTransRepository
import org.springframework.stereotype.Service

@Service
class CteTransformService(
    cteTransRepository: CteTransRepository
) : BaseService<CteTrans>(cteTransRepository, "CteTransform")
