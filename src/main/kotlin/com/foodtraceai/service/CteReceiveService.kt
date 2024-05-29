package com.foodtraceai.service

import com.foodtraceai.model.cte.CteReceive
import com.foodtraceai.repository.CteReceiveRepository
import org.springframework.stereotype.Service

@Service
class CteReceiveService(
    cteReceiveRepository: CteReceiveRepository
) : BaseService<CteReceive>(cteReceiveRepository, "CteReceive")
