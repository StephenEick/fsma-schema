package com.example.fsma.repository

import com.example.fsma.model.FoodBusiness
import org.springframework.stereotype.Repository

@Repository
interface BusinessRepository : BaseRepository<FoodBusiness>
