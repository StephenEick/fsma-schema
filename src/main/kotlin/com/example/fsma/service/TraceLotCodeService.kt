package com.example.fsma.service

import com.example.fsma.model.TraceLotCode
import com.example.fsma.repository.TraceLotCodeRepository
import org.springframework.stereotype.Service

@Service
class TraceLotCodeService(
    tlcRepository: TraceLotCodeRepository
) : BaseService<TraceLotCode>(tlcRepository, "TraceabilityLotCode")
