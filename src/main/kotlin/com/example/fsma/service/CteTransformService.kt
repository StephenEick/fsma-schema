package com.example.fsma.service

import com.example.fsma.model.cte.CteTrans
import com.example.fsma.repository.CteTransformRepository
import org.springframework.stereotype.Service

@Service
class CteTransformService(
    cteTransformRepository: CteTransformRepository
) : BaseService<CteTrans>(cteTransformRepository, "CteTransform")
