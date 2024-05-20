package com.example.fsma.service

import com.example.fsma.model.cte.CteTrans
import com.example.fsma.repository.CteTransRepository
import org.springframework.stereotype.Service

@Service
class CteTransformService(
    cteTransRepository: CteTransRepository
) : BaseService<CteTrans>(cteTransRepository, "CteTransform")
