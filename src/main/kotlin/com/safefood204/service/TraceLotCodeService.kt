package com.safefood204.service

import com.safefood204.model.TraceLotCode
import com.safefood204.repository.TraceLotCodeRepository
import org.springframework.stereotype.Service

@Service
class TraceLotCodeService(
    tlcRepository: TraceLotCodeRepository
) : BaseService<TraceLotCode>(tlcRepository, "TraceabilityLotCode")
