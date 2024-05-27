package com.safefood204.service

import com.safefood204.model.cte.CteHarvest
import com.safefood204.repository.CteHarvestRepository
import org.springframework.stereotype.Service

@Service
class CteHarvestService(
    cteHarvestRepository: CteHarvestRepository
) : BaseService<CteHarvest>(cteHarvestRepository, "CteHarvest")
