package com.example.fsma.service

import com.example.fsma.model.cte.CteIPackProd
import com.example.fsma.repository.CteIPackProdRepository
import org.springframework.stereotype.Service

@Service
class CteIPackProdService(
    cteIPackProdRepository: CteIPackProdRepository
) : BaseService<CteIPackProd>(cteIPackProdRepository, "CteIPack")
