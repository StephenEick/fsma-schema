package com.foodtraceai.service

import com.foodtraceai.model.cte.CteIPackProd
import com.foodtraceai.repository.CteIPackProdRepository
import org.springframework.stereotype.Service

@Service
class CteIPackProdService(
    cteIPackProdRepository: CteIPackProdRepository
) : BaseService<CteIPackProd>(cteIPackProdRepository, "CteIPack")
