package com.example.fsma.repository

import com.example.fsma.model.Address
import com.example.fsma.model.Business
import com.example.fsma.model.Location
import com.example.fsma.model.TraceabilityLotCode
import com.example.fsma.model.cte.CteCool
import com.example.fsma.model.cte.CteHarvest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Repository

@NoRepositoryBean
interface BaseRepository<T> : JpaRepository<T, Long> {
    fun dateDeletedIsNull(): List<T>
    override fun findAll(): List<T> {
        return dateDeletedIsNull()
    }
}

@Repository
interface AddressRepository : BaseRepository<Address>

@Repository
interface BusinessRepository : BaseRepository<Business>

@Repository
interface CteCoolRepository : BaseRepository<CteCool>

@Repository
interface CteHarvestRepository : BaseRepository<CteHarvest>

@Repository
interface LocationRepository : BaseRepository<Location>

@Repository
interface TraceabilityLotCodeRepository: BaseRepository<TraceabilityLotCode>