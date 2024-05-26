package com.example.fsma

import com.example.fsma.auth.AuthService
import com.example.fsma.model.*
import com.example.fsma.service.AddressService
import com.example.fsma.service.FoodBusService
import com.example.fsma.service.FsmaUserService
import com.example.fsma.util.Country
import com.example.fsma.util.FoodBusType
import com.example.fsma.util.Role
import com.example.fsma.util.UsaCanadaState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate

class SetupFsmaTests {
    companion object {
        @Autowired
        private lateinit var jdbcTemplate: JdbcTemplate

        @Autowired
        private lateinit var authService: AuthService

//    @Autowired
//    private lateinit var tenantIdentifierResolver: TenantIdentifierResolver

        @Autowired
         lateinit var addressService: AddressService
        val addressList: MutableList<Address> = mutableListOf()

        @Autowired
         lateinit var foodBusService: FoodBusService
         val foodBusList: MutableList<FoodBus> = mutableListOf()

        @Autowired
         lateinit var fsmaUserService: FsmaUserService
         val fsmaUserList: MutableList<FsmaUser> = mutableListOf()

        private fun addAddresses() {
            var addressDto = AddressDto(
                street = "1622 Central Ave",
                city = "Memphis",
                state = UsaCanadaState.TN,
                postalCode = "38104-5064",
                country = Country.USA,
                lat = 35.1268133,
                lng = -90.0087413
            )

            var address = addressDto.toAddress()
            addressList.add(addressService.insert(address))


            addressDto = AddressDto(
                street = "1413 Durness Ct.",
                city = "Naperville",
                state = UsaCanadaState.IL,
                postalCode = "60565",
                country = Country.USA,
                lat = 35.1268133,
                lng = -90.0087413
            )

            address = addressDto.toAddress()
            addressList.add(addressService.insert(address))

            addressDto = AddressDto(
                street = "630 N. Main",
                city = "Naperville",
                state = UsaCanadaState.IL,
                postalCode = "60563",
                country = Country.USA,
                lat = 35.1268133,
                lng = -90.0087413
            )

            address = addressDto.toAddress()
            addressList.add(addressService.insert(address))
        }

        private  fun addFoodBusinesses() {

            var foodBus = FoodBus(
                mainAddress = addressList[0],
                foodBusName = "KaleidoscopeInc",
                contactName = "Joe Smith",
                contactPhone = "800-555-1212",
                foodBusType = FoodBusType.RfeRestaurant
            )
            foodBusList.add(foodBusService.insert(foodBus))

            foodBus = FoodBus(
                mainAddress = addressList[0],
                foodBusName = "630 N. Main",
                contactName = "Ted Podolak",
                contactPhone = "800-555-1212",
                foodBusType = FoodBusType.RfeRestaurant
            )
            foodBusList.add(foodBusService.insert(foodBus))
        }

        private fun addFsmaUsers() {
            var fsmaUserDto = FsmaUserRequestDto(
                foodBusinessId = foodBusList[0].id,
                email = "User0@restaurant0.com",
                password = "123",
                roles = listOf(Role.RootAdmin),
                firstname = "Root",
                lastname = "User0",
            )
            var response = authService.createNewFsmaUser(fsmaUserDto)
            fsmaUserList.add(
                fsmaUserService.findById(response.fsmaUserId)
                    ?: throw Exception("Failed to create FsmaUser: ${fsmaUserDto.email}")
            )

            fsmaUserDto = FsmaUserRequestDto(
                foodBusinessId = foodBusList[0].id,
                email = "User1@Restaurant0.com",
                password = "123",
                roles = listOf(Role.FranchisorAdmin, Role.FoodBusinessUser),
                firstname = "Steve",
                lastname = "User1",
            )
            response = authService.createNewFsmaUser(fsmaUserDto)
            fsmaUserList.add(
                fsmaUserService.findById(response.fsmaUserId)
                    ?: throw Exception("Failed to create FsmaUser: ${fsmaUserDto.email}")
            )
        }

        fun setup() {
            addAddresses()
            addFoodBusinesses()
            addFsmaUsers()
        }
    }
}