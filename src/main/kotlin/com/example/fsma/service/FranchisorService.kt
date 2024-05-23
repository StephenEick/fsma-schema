package com.example.fsma.service

import com.example.fsma.model.Franchisor
import com.example.fsma.repository.FranchisorRepository
import org.springframework.stereotype.Service

@Service
class FranchisorService(
    franchisorRepository: FranchisorRepository
) : BaseService<Franchisor>(franchisorRepository, "Franchisor")
