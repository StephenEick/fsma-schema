package com.safefood204.service

import com.safefood204.model.cte.CteIPackProd
import com.safefood204.repository.CteIPackProdRepository
import org.springframework.stereotype.Service

@Service
class CteIPackProdService(
    cteIPackProdRepository: CteIPackProdRepository
) : BaseService<CteIPackProd>(cteIPackProdRepository, "CteIPack")
