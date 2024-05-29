package com.foodtraceai

import com.fasterxml.jackson.databind.ObjectMapper
import com.foodtraceai.auth.AuthLogin
import com.foodtraceai.auth.AuthService
import com.foodtraceai.model.FsmaUserDto
import com.foodtraceai.service.FsmaUserService
import com.foodtraceai.util.Role
import com.jayway.jsonpath.JsonPath
import org.hamcrest.Matchers.equalToIgnoringCase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class TestsAuthController {
    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var fsaUserService: FsmaUserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    // TODO: add passwordreset token logic - we will need this
//    @Autowired
//    private lateinit var passwordResetTokenRepository: PasswordResetTokenRepository

//    @BeforeEach
//    fun before() = SetupFsmaTests.setup()

    fun authenticate(authLogin: AuthLogin): List<String> {
        val mvcResult = mockMvc.post("/api/v1/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(authLogin)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()

        return listOf(
            JsonPath.read(mvcResult.response.contentAsString, "$.accessToken"),
            JsonPath.read(mvcResult.response.contentAsString, "$.refreshToken"),
        )
    }

    @Test
    fun `get root admin`() {
        val root = FsmaUserDto(
            id = 1,
            foodBusinessId = 1,//foodBusinessList[0].id,
            email = "root@foodtraceai.com",
            password = "123",
            roles = listOf(Role.RootAdmin),
            firstname = "Root",
            lastname = "Root",
        )
        val rootAuthLogin = AuthLogin(email = root.email, password = root.password, refreshToken = null)
        val accessToken: String = authenticate(rootAuthLogin)[0]
        val rootId: Long = root.id
        mockMvc.get("/api/v1/fsausers/$rootId") {
            header("Authorization", "Bearer $accessToken")
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { value(rootId) }
            jsonPath("$.firstname") { value(root.firstname) }
            jsonPath("$.lastname") { value(root.lastname) }
            jsonPath("$.email") { equalToIgnoringCase(root.email) }
            jsonPath("$.notes") { value(root.notes) }
            // TODO: Ask Milo how ot fix this comparison
            // jsonPath("$.roles") { value(rootAdminDto.roles) }
            jsonPath("$.phone") { value(root.phone) }
        }
    }
}