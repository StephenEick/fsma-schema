package com.example.fsma.service

import com.example.fsma.model.FoodBusiness
import com.example.fsma.repository.BusinessRepository
import org.springframework.stereotype.Service

@Service
class BusinessService(
    businessRepository: BusinessRepository
) : BaseService<FoodBusiness>(businessRepository, "Business")
