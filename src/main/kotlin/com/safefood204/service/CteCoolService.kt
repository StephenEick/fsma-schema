package com.safefood204.service

import com.safefood204.model.cte.CteCool
import com.safefood204.repository.CteCoolRepository
import org.springframework.stereotype.Service

@Service
class CteCoolService(
    cteCoolRepository: CteCoolRepository
) : BaseService<CteCool>(cteCoolRepository, "CteCool")
