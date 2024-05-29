package com.foodtraceai.service

import com.foodtraceai.model.TraceLotCode
import com.foodtraceai.repository.TraceLotCodeRepository
import org.springframework.stereotype.Service

@Service
class TraceLotCodeService(
    tlcRepository: TraceLotCodeRepository
) : BaseService<TraceLotCode>(tlcRepository, "TraceabilityLotCode")
