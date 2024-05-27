package com.safefood204.service

import com.safefood204.model.cte.CteShip
import com.safefood204.repository.CteShipRepository
import org.springframework.stereotype.Service

@Service
class CteShipService(
    cteShipRepository: CteShipRepository
) : BaseService<CteShip>(cteShipRepository, "CteShip")
