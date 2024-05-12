package com.example.fsma.service

import com.example.fsma.model.cte.CteShip
import com.example.fsma.repository.CteShipRepository
import org.springframework.stereotype.Service

@Service
class CteShipService(
    cteShipRepository: CteShipRepository
) : BaseService<CteShip>(cteShipRepository, "CteShip")
