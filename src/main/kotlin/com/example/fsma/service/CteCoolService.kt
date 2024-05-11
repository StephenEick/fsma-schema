package com.example.fsma.service

import com.example.fsma.model.cte.CteCool
import com.example.fsma.repository.CteCoolRepository
import org.springframework.stereotype.Service

@Service
class CteCoolService(
    cteCoolRepository: CteCoolRepository
) : BaseService<CteCool>(cteCoolRepository, "CteCool")
