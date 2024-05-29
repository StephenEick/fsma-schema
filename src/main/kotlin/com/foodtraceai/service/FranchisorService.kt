package com.foodtraceai.service

import com.foodtraceai.model.Franchisor
import com.foodtraceai.repository.FranchisorRepository
import org.springframework.stereotype.Service

@Service
class FranchisorService(
    franchisorRepository: FranchisorRepository
) : BaseService<Franchisor>(franchisorRepository, "Franchisor")
