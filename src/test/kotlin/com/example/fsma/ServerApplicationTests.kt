package com.example.fsma

import com.example.fsma.model.AddressRequestDto
import com.example.fsma.model.BusinessDto
import com.example.fsma.util.Country
import com.example.fsma.util.UsaCanadaState
import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
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

    private lateinit var addressRequestDto: AddressRequestDto
    private lateinit var addressRequestDtoUpdated: AddressRequestDto
    private lateinit var businessDto: BusinessDto
    private lateinit var businessDtoUpdated: BusinessDto


    @BeforeEach
    fun setup() {

        // -- Address
        addressRequestDto = AddressRequestDto(
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
        addressRequestDtoUpdated = AddressRequestDto(
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

        businessDto = BusinessDto(
            id=0,
            mainAddressId = 1,
            name = "Fred's Restaurant",
            contactName = "Steve",
            contactPhone = "1-800-555-1212"
        )

        businessDtoUpdated = BusinessDto(
            id=0,
            mainAddressId = 1,
            name = "Fred's Restaurant",
            contactName = "Steve",
            contactPhone = "1-800-555-1212"
        )
    }

    // ------------------------------------------------------------------------
    // -- Address tests

    fun addAddress(): Long {
//        val accessToken: String? = authenticate()
        val mvcResult = mockMvc.post("/api/v1/address") {
//            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(addressRequestDto)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()
        return JsonPath.read(mvcResult.response.contentAsString, "$.id")
    }

// ---

    @Test
    fun `add address`() {
//        val accessToken: String? = authenticate()
        mockMvc.post("/api/v1/address") {
//            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(addressRequestDto)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.street") { value(addressRequestDto.street) }
            jsonPath("$.street2") { value(addressRequestDto.street2) }
            jsonPath("$.city") { value(addressRequestDto.city) }
            jsonPath("$.state") { value(addressRequestDto.state.name) }
            jsonPath("$.postalCode") { value(addressRequestDto.postalCode) }
            jsonPath("$.country") { value(addressRequestDto.country.name) }
            jsonPath("$.lat") { value(addressRequestDto.lat) }
            jsonPath("$.lng") { value(addressRequestDto.lng) }
        }
    }

    @Test
    fun `get address`() {
//        val accessToken: String? = authenticate()
        val addressId: Long = addAddress()
        mockMvc.get("/api/v1/address/$addressId") {
//            header("Authorization", "Bearer $accessToken")
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
//        val accessToken: String? = authenticate()
        val addressId: Long = addAddress()
        addressRequestDtoUpdated = addressRequestDtoUpdated.copy(id = addressId)
        mockMvc.put("/api/v1/address/$addressId") {
//            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(addressRequestDtoUpdated)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(addressId) }
            jsonPath("$.street") { value(addressRequestDtoUpdated.street) }
            jsonPath("$.street2") { value(addressRequestDtoUpdated.street2) }
            jsonPath("$.city") { value(addressRequestDtoUpdated.city) }
            jsonPath("$.state") { value(addressRequestDtoUpdated.state.name) }
            jsonPath("$.postalCode") { value(addressRequestDtoUpdated.postalCode) }
            jsonPath("$.country") { value(addressRequestDtoUpdated.country.name) }
            jsonPath("$.lat") { value(addressRequestDtoUpdated.lat) }
            jsonPath("$.lng") { value(addressRequestDtoUpdated.lng) }
        }
    }

    @Test
    fun `delete address`() {
//        val accessToken: String? = authenticate()
        val addressId: Long = addAddress()
        mockMvc.delete("/api/v1/address/$addressId") {
//            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isNoContent() }
        }
    }

    // ------------------------------------------------------------------------
    // -- BusinessName tests
}
