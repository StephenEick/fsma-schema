package com.example.fsma.service

import com.example.fsma.model.cte.CteHarvest
import com.example.fsma.repository.CteHarvestRepository
import org.springframework.stereotype.Service

@Service
class CteHarvestService(
    cteHarvestRepository: CteHarvestRepository
) : BaseService<CteHarvest>(cteHarvestRepository, "CteHarvest")
