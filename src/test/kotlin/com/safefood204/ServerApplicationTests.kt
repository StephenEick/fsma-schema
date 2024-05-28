package com.safefood204

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.safefood204.auth.AuthLogin
import com.safefood204.model.AddressDto
import com.safefood204.model.FoodBusDto
import com.safefood204.model.LocationDto
import com.safefood204.model.TraceLotCodeDto
import com.safefood204.util.Country
import com.safefood204.util.FoodBusType
import com.safefood204.util.UsaCanadaState
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
class ServerApplicationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    // ------------------------------------------------------------------------
    // Test setup

    private lateinit var addressDto: AddressDto
    private lateinit var addressDtoUpdated: AddressDto
    private lateinit var foodBusDto: FoodBusDto
    private lateinit var foodBusDtoUpdated: FoodBusDto
    private lateinit var locationDto: LocationDto
    private lateinit var locationDtoUpdated: LocationDto
    private lateinit var traceLotCodeDto: TraceLotCodeDto

    val rootAuthLogin = AuthLogin(email = "User0@restaurant0.com", password = "123", refreshToken = null)

    fun authenticate(authLogin: AuthLogin): Pair<String, String> {
        val mvcResult = mockMvc.post("/api/v1/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(authLogin)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()

        return Pair(
            JsonPath.read(mvcResult.response.contentAsString, "$.accessToken"),
            JsonPath.read(mvcResult.response.contentAsString, "$.refreshToken"),
        )
    }

    @BeforeEach
    fun setup() {

        // -- Address
        addressDto = AddressDto(
            id = 0,
            street = "1413 Durness Court",
            street2 = "Apt-101",
            city = "Naperville",
            state = UsaCanadaState.IL,
            postalCode = "60565",
            country = Country.USA,
            lat = 41.742220,
            lng = -88.162270
        )
        addressDtoUpdated = AddressDto(
            id = 9,
            street = "1413 Durness Court",
            street2 = "Changed",
            city = "Naperville",
            state = UsaCanadaState.IL,
            postalCode = "60565",
            country = Country.USA,
            lat = 41.742220,
            lng = -88.162270
        )

        foodBusDto = FoodBusDto(
            id = 0,
            mainAddressId = 1,
            contactName = "Steve",
            contactPhone = "1-800-555-1212",
            foodBusName = "Fred's Restaurant",
            foodBusType = FoodBusType.RfeRestaurant,
            franchisorId = null,
        )

        foodBusDtoUpdated = FoodBusDto(
            id = 0,
            mainAddressId = 1,
            contactName = "NewContact",
            contactPhone = "1-800-555-1212",
            foodBusName = "Fred's Restaurant",
            foodBusType = FoodBusType.RfeGrocer,
            franchisorId = null,
        )

        locationDto = LocationDto(
            id = 0,
            foodBusId = 1,
            contactName = "Steve",
            contactPhone = "1-800-555-1212",
            serviceAddressId = 1,
        )

        locationDtoUpdated = LocationDto(
            id = 0,
            foodBusId = 1,
            contactName = "NewContact",
            contactPhone = "0-000-000-0000",
            serviceAddressId = 1,
        )

        traceLotCodeDto = TraceLotCodeDto(
            tlc = "trace lot code 1",
            desc = "desc trace lot code 1",
        )

//        val pair = authenticate(rootAuthLogin)
//        accessToken = pair[0]
//        refreshToken = pair[1]
    }


    // ------------------------------------------------------------------------
    // -- Address tests

    fun addAddress(): Long {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val mvcResult = mockMvc.post("/api/v1/address") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(addressDto)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()
        return JsonPath.read(mvcResult.response.contentAsString, "$.id")
    }

    @Test
    fun `add address`() {
        val (accessToken, _) = authenticate(rootAuthLogin)
        mockMvc.post("/api/v1/address") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(addressDto)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.street") { value(addressDto.street) }
            jsonPath("$.street2") { value(addressDto.street2) }
            jsonPath("$.city") { value(addressDto.city) }
            jsonPath("$.state") { value(addressDto.state.name) }
            jsonPath("$.postalCode") { value(addressDto.postalCode) }
            jsonPath("$.country") { value(addressDto.country.name) }
            jsonPath("$.lat") { value(addressDto.lat) }
            jsonPath("$.lng") { value(addressDto.lng) }
        }
    }

    @Test
    fun `get address`() {
        val addressId: Long = addAddress()
        val (accessToken, _) = authenticate(rootAuthLogin)
        mockMvc.get("/api/v1/address/$addressId") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(addressId) }
            jsonPath("$.street") { value("1413 Durness Court") }
            jsonPath("$.street2") { value("Apt-101") }
            jsonPath("$.city") { value("Naperville") }
            jsonPath("$.state") { value("IL") }
            jsonPath("$.postalCode") { value("60565") }
            jsonPath("$.country") { value("USA") }
            jsonPath("$.lat") { value(41.742220) }
            jsonPath("$.lng") { value(-88.162270) }
        }
    }

    @Test
    fun `update address`() {
//        addFsaUsers()
        val (accessToken, _) = authenticate(rootAuthLogin)
        val addressId: Long = addAddress()
        addressDtoUpdated = addressDtoUpdated.copy(id = addressId)
        mockMvc.put("/api/v1/address/$addressId") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(addressDtoUpdated)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(addressId) }
            jsonPath("$.street") { value(addressDtoUpdated.street) }
            jsonPath("$.street2") { value(addressDtoUpdated.street2) }
            jsonPath("$.city") { value(addressDtoUpdated.city) }
            jsonPath("$.state") { value(addressDtoUpdated.state.name) }
            jsonPath("$.postalCode") { value(addressDtoUpdated.postalCode) }
            jsonPath("$.country") { value(addressDtoUpdated.country.name) }
            jsonPath("$.lat") { value(addressDtoUpdated.lat) }
            jsonPath("$.lng") { value(addressDtoUpdated.lng) }
        }
    }

    @Test
    fun `delete address`() {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val addressId: Long = addAddress()
        mockMvc.delete("/api/v1/address/$addressId") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isNoContent() }
        }
    }

    // ------------------------------------------------------------------------
    // -- FoodBus tests

    fun addFoodBus(): Long {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val mainAddressId = addAddress()
        val mvcResult = mockMvc.post("/api/v1/foodbus") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(foodBusDto.copy(mainAddressId = mainAddressId))
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()
        return JsonPath.read(mvcResult.response.contentAsString, "$.id")
    }

    @Test
    fun `get foodBus`() {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val foodBusId = addFoodBus()
        mockMvc.get("/api/v1/foodbus/$foodBusId") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(foodBusId) }
            jsonPath("$.contactName") { value("Steve") }
            jsonPath("$.contactPhone") { value("1-800-555-1212") }
            jsonPath("$.foodBusName") { value("Fred's Restaurant") }
            jsonPath("$.foodBusType") { value("RfeRestaurant") }
        }
    }

    @Test
    fun `update foodBusiness`() {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val foodBusId: Long = addFoodBus()
        foodBusDtoUpdated = foodBusDtoUpdated.copy(id = foodBusId)
        mockMvc.put("/api/v1/foodbus/$foodBusId") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(foodBusDtoUpdated)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(foodBusId) }
            jsonPath("$.contactName") { value("NewContact") }
            jsonPath("$.contactPhone") { value("1-800-555-1212") }
            jsonPath("$.foodBusName") { value("Fred's Restaurant") }
            jsonPath("$.foodBusType") { value("RfeGrocer") }
        }
    }

    @Test
    fun `delete business`() {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val foodBusId: Long = addFoodBus()
        mockMvc.delete("/api/v1/foodbus/$foodBusId") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isNoContent() }
        }
    }

    // ------------------------------------------------------------------------
    // -- Location tests

    fun addLocation(): Pair<Long, Long> {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val serviceAddressId = addAddress()
        val foodBusId = addFoodBus()
        val mvcResult = mockMvc.post("/api/v1/location") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(
                locationDto.copy(
                    foodBusId = foodBusId,
                    serviceAddressId = serviceAddressId
                )
            )
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()
        val locationId: Long = JsonPath.read(mvcResult.response.contentAsString, "$.id")
        return Pair(locationId, serviceAddressId)
    }

    @Test
    fun `get location`() {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val (locationId, serviceAddressId) = addLocation()
        mockMvc.get("/api/v1/location/$locationId") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(locationId) }
            jsonPath("$.contactName") { value("Steve") }
            jsonPath("$.contactPhone") { value("1-800-555-1212") }
            jsonPath("$.serviceAddressId") { value(serviceAddressId) }
        }
    }

    @Test
    fun `update location`() {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val (locationId, serviceAddressId) = addLocation()
        locationDtoUpdated = locationDtoUpdated.copy(id = locationId)
        mockMvc.put("/api/v1/location/$locationId") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(locationDtoUpdated)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(locationId) }
            jsonPath("$.contactName") { value("NewContact") }
            jsonPath("$.contactPhone") { value("0-000-000-0000") }
        }
    }

    @Test
    fun `delete location`() {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val (locationId, serviceAddressId) = addLocation()
        mockMvc.delete("/api/v1/location/$locationId") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isNoContent() }
        }
    }

    // ------------------------------------------------------------------------
    // -- TraceLotCodes tests

    fun addTraceLotCode(): Long {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val mvcResult = mockMvc.post("/api/v1/tlc") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(traceLotCodeDto)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()
        return JsonPath.read(mvcResult.response.contentAsString, "$.id")
    }

    @Test
    fun `get trace lot code`() {
        val (accessToken, _) = authenticate(rootAuthLogin)
        val traceLotCodeId = addTraceLotCode()
        mockMvc.get("/api/v1/tlc/$traceLotCodeId") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(traceLotCodeId) }
            jsonPath("$.tlc") { value("trace lot code 1") }
            jsonPath("$.desc") { value("desc trace lot code 1") }
        }
    }
}
