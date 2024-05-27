package com.safefood204.service

import com.safefood204.model.cte.CteTrans
import com.safefood204.repository.CteTransRepository
import org.springframework.stereotype.Service

@Service
class CteTransformService(
    cteTransRepository: CteTransRepository
) : BaseService<CteTrans>(cteTransRepository, "CteTransform")
