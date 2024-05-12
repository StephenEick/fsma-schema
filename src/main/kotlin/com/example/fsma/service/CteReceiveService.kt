package com.example.fsma.service

import com.example.fsma.model.cte.CteReceive
import com.example.fsma.repository.CteReceiveRepository
import org.springframework.stereotype.Service

@Service
class CteReceiveService(
    cteReceiveRepository: CteReceiveRepository
) : BaseService<CteReceive>(cteReceiveRepository, "CteReceive")
