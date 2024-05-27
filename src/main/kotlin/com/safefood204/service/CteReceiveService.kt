package com.safefood204.service

import com.safefood204.model.cte.CteReceive
import com.safefood204.repository.CteReceiveRepository
import org.springframework.stereotype.Service

@Service
class CteReceiveService(
    cteReceiveRepository: CteReceiveRepository
) : BaseService<CteReceive>(cteReceiveRepository, "CteReceive")
