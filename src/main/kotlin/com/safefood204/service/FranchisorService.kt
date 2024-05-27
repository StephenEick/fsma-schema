package com.safefood204.service

import com.safefood204.model.Franchisor
import com.safefood204.repository.FranchisorRepository
import org.springframework.stereotype.Service

@Service
class FranchisorService(
    franchisorRepository: FranchisorRepository
) : BaseService<Franchisor>(franchisorRepository, "Franchisor")
