package com.example.fsma.util

import com.example.fsma.model.Address
import com.example.fsma.model.AddressDto
import com.example.fsma.model.Business
import com.example.fsma.model.toAddress
import com.example.fsma.service.AddressService
import com.example.fsma.service.BusinessService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
@Profile("!staging && !prod")
class DataLoader : ApplicationRunner {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

//    @Autowired
//    private lateinit var authService: AuthService

//    @Autowired
//    private lateinit var tenantIdentifierResolver: TenantIdentifierResolver

    @Autowired
    private lateinit var addressService: AddressService
    private val addressList: MutableList<Address> = mutableListOf()

    @Autowired
    private lateinit var businessService: BusinessService
    private val businessList: MutableList<Business> = mutableListOf()

    override fun run(args: ApplicationArguments?) {
        deleteAllData()
        addBusiness()
    }

    @Suppress("LongMethod")
    private fun deleteAllData() {
        jdbcTemplate.execute("DELETE FROM address CASCADE;")
        jdbcTemplate.execute("DELETE FROM business CASCADE;")
        jdbcTemplate.execute("DELETE FROM location CASCADE;")
        jdbcTemplate.execute("DELETE FROM trace_lot_code CASCADE;")

        jdbcTemplate.execute("DELETE FROM cte_cool CASCADE;")
        jdbcTemplate.execute("DELETE FROM cte_harvest CASCADE;")
        jdbcTemplate.execute("DELETE FROM cte_ipack_prod CASCADE;")
        jdbcTemplate.execute("DELETE FROM cte_receive CASCADE;")
        jdbcTemplate.execute("DELETE FROM cte_ship CASCADE;")
        jdbcTemplate.execute("DELETE FROM cte_trans CASCADE;")

        jdbcTemplate.execute("ALTER SEQUENCE IF EXISTS address_seq RESTART;")
        jdbcTemplate.execute("ALTER SEQUENCE IF EXISTS business_seq RESTART;")
        jdbcTemplate.execute("ALTER SEQUENCE IF EXISTS location_seq RESTART;")
        jdbcTemplate.execute("ALTER SEQUENCE IF EXISTS trace_lot_code_seq RESTART;")

        jdbcTemplate.execute("ALTER SEQUENCE IF EXISTS cte_cool_seq RESTART;")
        jdbcTemplate.execute("ALTER SEQUENCE IF EXISTS cte_harvest_seq RESTART;")
        jdbcTemplate.execute("ALTER SEQUENCE IF EXISTS cte_ipack_prod_seq RESTART;")
        jdbcTemplate.execute("ALTER SEQUENCE IF EXISTS cte_receive_seq RESTART;")
        jdbcTemplate.execute("ALTER SEQUENCE IF EXISTS cte_ship_seq RESTART;")
        jdbcTemplate.execute("ALTER SEQUENCE IF EXISTS cte_trans_seq RESTART;")
    }

    fun addBusiness() {
        var addressDto = AddressDto(
            id = 0,
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

        var business = Business(
            mainAddress = address,
            businessName = "KaleidoscopeInc",
            contactName = "Joe Smith",
            contactPhone = "800-555-1212",
        )
        businessList.add(businessService.insert(business))

//        resellerId = 2L
//        addressDto = AddressRequestDto(
//            id = 0,
//            street = "1413 Durness Court",
//            street2 = "Apt-101",
//            city = "Naperville",
//            state = UsaCanadaState.IL, // UsaState.IL,
//            postalCode = "60565",
//            country = Country.USA, // Country.USA,
//            lat = 41.742220,
//            lng = -88.162270
//        )
//        address = addressDto.toAddress(resellerId)
//        billingAddress = address.copy()
//        reseller = Reseller()
//        reseller.address = address
//        reseller.businessName = "ACME GPS Tracking"
//        reseller.firstName = "Joe"
//        reseller.lastName = "Smith"
//        reseller.mainContactName = "Frank Scott"
//        reseller.mainContactPhone = "800-555-1212"
//        reseller.mainContactEmail = "fscott@example.com"
//        reseller.billingContactName = "James Scott"
//        reseller.billingContactPhone = "800-555-1212"
//        reseller.billingContactEmail = "jscott@example.com"
//        reseller.billingAddress = billingAddress
//        reseller.accountRep = "John Doe"
//
//        resellerList.add(resellerService.insert(reseller))
//
//        resellerId = 3L
//        addressDto = AddressRequestDto(
//            id = 0,
//            street = "1234 Flanders St",
//            street2 = "#245",
//            city = "Smartsville",
//            state = UsaCanadaState.ND, // UsaState.ND,
//            postalCode = "60542",
//            country = Country.USA, // Country.USA,
//            lat = 39.0,
//            lng = -87.0
//        )
//        address = addressDto.toAddress(resellerId)
//        billingAddress = address.copy()
//        reseller = Reseller()
//        reseller.address = address
//        reseller.businessName = "Trucking Telematics, LLC"
//        reseller.firstName = "Joe"
//        reseller.lastName = "Smith"
//        reseller.mainContactName = "Jake Jones"
//        reseller.mainContactPhone = "800-555-1212"
//        reseller.mainContactEmail = "jjones@example.com"
//        reseller.billingContactName = "Allen Hamel"
//        reseller.billingContactPhone = "800-555-1212"
//        reseller.billingContactEmail = "ahamel@example.com"
//        reseller.billingAddress = billingAddress
//        reseller.accountRep = "Jane Doe"
//
//        resellerList.add(resellerService.insert(reseller))
    }

//    fun addClients() {
//        // -- resellerId=1
//        var addressRequestDto = AddressRequestDto(
//            id = 0,
//            resellerId = resellerList[0].id,
//            street = "1622 Central Ave",
//            city = "Memphis",
//            state = UsaCanadaState.TN,
//            postalCode = "38104-5064",
//            country = Country.USA,
//            lat = 35.1268133,
//            lng = -90.0087413
//        )
//        var clientRequestDto = ClientRequestDto(
//            addressDto = addressRequestDto,
//            timeZone = TimeZone.getTimeZone("US/Central"),
//            businessName = "KaleidoscopeInc",
//            resellerId = resellerList[0].id,
//            firstName = "Joe",
//            lastName = "Smith",
//            mainContactName = "Willian Oxley",
//            mainContactPhone = "800-555-1212",
//            mainContactEmail = "woxley@kscopeinc.com",
//            billingContactName = "Willian Oxley",
//            billingContactPhone = "800-555-1212",
//            billingContactEmail = "woxley730@gmail.com",
//            accountRep = "Willian Oxley",
//            masterSkills = listOf(
//                SkillSet("ChainSaw-Basic", "ChainSaw-Basic description"),
//                SkillSet("ChainSaw-Master", "ChainSaw-Master description")
//            )
//        )
//        var client = clientRequestDto.toClient(resellerList[0])
//        clientList.add(clientService.insert(client))
//
//        addressRequestDto = AddressRequestDto(
//            resellerId = resellerList[0].id,
//            street = "1234 Nichol Blvd",
//            street2 = "#2994",
//            city = "Apple Tree",
//            state = UsaCanadaState.CO,
//            postalCode = "60542",
//            country = Country.USA,
//            lat = 27.0,
//            lng = -123.0
//        )
//        clientRequestDto = ClientRequestDto(
//            addressDto = addressRequestDto,
//            timeZone = TimeZone.getTimeZone("US/Central"),
//            businessName = "Acme Tree Service, Inc.",
//            resellerId = resellerList[0].id,
//            firstName = "Joe",
//            lastName = "Smith",
//            mainContactName = "Joe Malone",
//            mainContactPhone = "800-555-1212",
//            mainContactEmail = "jm@example.com",
//            billingContactName = "Bill Smith",
//            billingContactPhone = "800-555-1212",
//            billingContactEmail = "Charlie Babbs",
//            accountRep = "John Doe",
//            masterSkills = listOf(
//                SkillSet("ChainSaw-Basic", "ChainSaw-Basic description"),
//                SkillSet("ChainSaw-Master", "ChainSaw-Master description")
//            )
//        )
//        client = clientRequestDto.toClient(resellerList[0])
//        clientList.add(clientService.insert(client)) // 1
//
//        addressRequestDto = AddressRequestDto(
//            resellerId = resellerList[0].id,
//            street = "2345 Smith Hill Rd",
//            city = "Johnsonville",
//            state = UsaCanadaState.TX,
//            postalCode = "12345",
//            country = Country.USA,
//            lat = 39.0,
//            lng = -87.0
//        )
//        clientRequestDto = ClientRequestDto(
//            addressDto = addressRequestDto,
//            timeZone = TimeZone.getTimeZone("US/Central"),
//            businessName = "AAA Plumbing Service, Inc.",
//            resellerId = resellerList[0].id,
//            firstName = "Joe",
//            lastName = "Smith",
//            mainContactName = "Joseph Mason",
//            mainContactPhone = "800-555-1212",
//            mainContactEmail = "jmason@example.com",
//            billingContactName = "Diane Franklin",
//            billingContactPhone = "800-555-1212",
//            billingContactEmail = "dfrank@example.com",
//            accountRep = "Jane Doe",
//            masterSkills = listOf(
//                SkillSet("Plumbing-Basic", "Plumbing-Basic description"),
//                SkillSet("Plumbing-Master", "Plumbing-Master description")
//            )
//        )
//        client = clientRequestDto.toClient(resellerList[0])
//        clientList.add(clientService.insert(client)) // 2
//    }
//
//    fun addFsaUsers() {
//        // Kscope as Root Admin
//        val rootAdminDto = FsaUserRequestDto(
//            clientId = 1, // KscopeInc
//            resellerId = 1, // KscopeInc
//            password = "12345",
//            firstname = "Root",
//            lastname = "Admin",
//            email = "ROOTAdmin@example.com",
//            roles = listOf(Role.RootAdmin),
//            notes = "KscopeInc Root Admin",
//            phone = "630-778-4321",
//            photoId = null,
//            isTech = false,
//        )
//        var response = authService.createNewFsaUser(rootAdminDto)
//        fsaUserList.add(fsaUserService.findById(response.fsaUserId) ?: throw Exception("Not found"))
//
//        // Kscope as Reseller Admin
//        val resellerAdminDto = FsaUserRequestDto(
//            clientId = 1,
//            resellerId = 1,
//            password = "54321",
//            firstname = "Reseller",
//            lastname = "Admin",
//            email = "resellerAdmin@example.com",
//            notes = "KscopeInc as Reseller Admin",
//            roles = listOf(Role.ResellerAdmin),
//            phone = "630-777-1234",
//            photoId = null,
//            isTech = false,
//        )
//        response = authService.createNewFsaUser(resellerAdminDto)
//        fsaUserList.add(fsaUserService.findById(response.fsaUserId) ?: throw Exception("Not found"))
//
//        // Kscope as Reseller Admin
//        val clientAdminDto = FsaUserRequestDto(
//            clientId = 1,
//            resellerId = 1,
//            password = "54321",
//            firstname = "Client",
//            lastname = "Admin",
//            email = "clientAdmin@example.com",
//            notes = "KscopeInc as Client Admin",
//            roles = listOf(Role.ClientAdmin),
//            phone = "630-888-2345",
//            photoId = null,
//            isTech = false,
//        )
//        response = authService.createNewFsaUser(clientAdminDto)
//        fsaUserList.add(fsaUserService.findById(response.fsaUserId) ?: throw Exception("Not found"))
//
//        val tech1Dto = FsaUserRequestDto(
//            clientId = 1,
//            resellerId = 1,
//            password = "12345",
//            firstname = "Mobile1",
//            lastname = "User1",
//            email = "MOBILE1@example.com",
//            roles = listOf(Role.Mobile),
//            phone = "517-332-6145",
//            photoId = null,
//            notes = "Mobile 1 User for Kscope Client",
//            isTech = true,
//            skills = listOf(
//                SkillSet("ChainSaw-Basic", "ChainSaw-Basic description"),
//                SkillSet("ChainSaw-Master", "ChainSaw-Master description")
//            )
//        )
//
//        response = authService.createNewFsaUser(tech1Dto)
//        fsaUserList.add(fsaUserService.findById(response.fsaUserId) ?: throw Exception("Not found"))
//
//        val tech2Dto = FsaUserRequestDto(
//            clientId = 1,
//            resellerId = 1,
//            password = "12345",
//            firstname = "Mobile2",
//            lastname = "User2",
//            email = "Mobile2@example.com",
//            roles = listOf(Role.Mobile),
//            phone = "517-332-6145",
//            photoId = null,
//            notes = "Mobile 2 User for Kscope Client",
//            isTech = true,
//            skills = listOf(
//                SkillSet("ChainSaw-Basic", "ChainSaw-Basic description"),
//                SkillSet("ChainSaw-Master", "ChainSaw-Master description")
//            )
//        )
//
//        response = authService.createNewFsaUser(tech2Dto)
//        fsaUserList.add(fsaUserService.findById(response.fsaUserId) ?: throw Exception("Not found"))
//
//        val tech3Dto = FsaUserRequestDto(
//            clientId = 1,
//            resellerId = 1,
//            password = "Default@123",
//            firstname = "TestFlight",
//            lastname = "TestFlight",
//            email = "appletest@kscopeinc.com",
//            roles = listOf(Role.Mobile),
//            phone = "517-332-6145",
//            photoId = null,
//            notes = "TestFlight testing",
//            isTech = true
//        )
//
//        response = authService.createNewFsaUser(tech3Dto)
//        fsaUserList.add(fsaUserService.findById(response.fsaUserId) ?: throw Exception("Not found"))
//    }
//
//    fun addCustomers() {
//        tenantIdentifierResolver.setCurrentTenant("1")
//        // -- resellerId, clientId
//        var addressRequestDto = AddressRequestDto(
//            id = 0,
//            street = "1800 Copperville Rd",
//            street2 = "",
//            city = "MyCity",
//            state = UsaCanadaState.WY, // UsaState.ND,
//            postalCode = "12345",
//            country = Country.USA, // Country.USA,
//            lat = 39.0,
//            lng = -87.0
//        )
//        var customerRequestDto = CustomerRequestDto(
//            clientId = 0,
//            addressDto = addressRequestDto,
//            firstName = "Ryan",
//            lastName = "Reynolds",
//            contact = "Steve",
//            email = "ryanreynolds@example.com",
//            phone = "800-555-1212",
//            notes = "customer 1 notes go here"
//        )
//        var customer = customerRequestDto.toCustomer(clientList[0])
//        customerList.add(customerService.insert(customer))
//
//        addressRequestDto = AddressRequestDto(
//            id = 0,
//            street = "4567 Cole Rd",
//            street2 = "",
//            city = "Flagstaff",
//            state = UsaCanadaState.AZ, // UsaState.ND,
//            postalCode = "63487",
//            country = Country.USA, // Country.USA,
//            lat = 39.0,
//            lng = -87.0
//        )
//        customerRequestDto = CustomerRequestDto(
//            clientId = 0,
//            addressDto = addressRequestDto,
//            firstName = "Steve",
//            lastName = "Wozniak",
//            contact = "Joe",
//            email = "stevewozniak@example.com",
//            phone = "800-555-1212",
//            notes = "customer 2 notes go here"
//        )
//        customer = customerRequestDto.toCustomer(clientList[0])
//        customerList.add(customerService.insert(customer))
//
//        addressRequestDto = AddressRequestDto(
//            id = 0,
//            street = "4567 Cole Rd",
//            street2 = "",
//            city = "Minneapolis",
//            state = UsaCanadaState.MN, // UsaState.ND,
//            postalCode = "88887",
//            country = Country.USA, // Country.USA,
//            lat = 39.0,
//            lng = -87.0
//        )
//        customerRequestDto = CustomerRequestDto(
//            clientId = 0,
//            addressDto = addressRequestDto,
//            firstName = "Steve",
//            lastName = "Wozniak",
//            contact = "Joe",
//            email = "stevewozniak@example.com",
//            phone = "800-555-1212",
//            notes = "customer 2 notes go here"
//        )
//        customer = customerRequestDto.toCustomer(clientList[0])
//        customerList.add(customerService.insert(customer))
//    }
//
//    fun addServLocs() {
//        tenantIdentifierResolver.setCurrentTenant("1")
//        // -- resellerId, clientId, customerId
//        var addressRequestDto = AddressRequestDto(
//            id = 0,
//            resellerId = resellerList[0].id,
//            street = "1234 Jenkins Ct",
//            street2 = "#238",
//            city = "Jamestown",
//            state = UsaCanadaState.ND, // UsaState.ND,
//            postalCode = "60542",
//            country = Country.USA, // Country.USA,
//            lat = 39.0,
//            lng = -87.0
//        )
//        var servLocRequestDto = ServLocRequestDto(
//            customerId = customerList[0].id,
//            addressDto = addressRequestDto,
//            contact = "Serv Loc 1nd Contact",
//            phone = "800-555-1212",
//            notes = "servLoc 1 notes go here",
//            photoId = null
//        )
//        var servLoc = servLocRequestDto.toServLoc(customerList[0])
//        servLocList.add(servLocService.insert(servLoc))
//
//        addressRequestDto = AddressRequestDto(
//            id = 0,
//            resellerId = resellerList[0].id,
//            street = "85664 Lake Arrowhead Ln",
//            street2 = "#7433",
//            city = "Georgetown",
//            state = UsaCanadaState.FL, // UsaState.ND,
//            postalCode = "35344",
//            country = Country.USA, // Country.USA,
//            lat = 39.0,
//            lng = -87.0
//        )
//        servLocRequestDto = ServLocRequestDto(
//            customerId = customerList[1].id,
//            addressDto = addressRequestDto,
//            contact = "Serv Loc 2nd Contact",
//            phone = "800-555-1212",
//            notes = "servLoc 2 notes go here",
//            photoId = null
//        )
//        servLoc = servLocRequestDto.toServLoc(customerList[1])
//        servLocList.add(servLocService.insert(servLoc))
//    }
//
//    fun addDatastores() {
//        var datastoreRequestDto = DatastoreRequestDto(
//            resellerId = resellerList[0].id,
//            clientId = clientList[0].id,
//            workRequestId = workRequestList[0].id,
//            dataName = DUMMY_IMAGE_NAME,
//            description = "Dummy Image 0",
//            mediaType = DUMMY_IMAGE_MEDIA,
//            purpose = DatastorePurpose.valueOf(DUMMY_IMAGE_PURPOSE),
//            checksum = "",
//            length = 63736,
//            imageWidth = 250,
//            imageHeight = 167,
//        )
//        var datastore = datastoreRequestDto.toDatastore(resellerList[0], clientList[0], workRequestList[0])
//        datastoreList.add(datastoreService.save(datastore))
//
//        datastoreRequestDto = DatastoreRequestDto(
//            clientId = clientList[0].id,
//            resellerId = resellerList[0].id,
//            workRequestId = workRequestList[0].id,
//            dataName = DUMMY_IMAGE_NAME,
//            description = "Dummy Image 1",
//            mediaType = DUMMY_IMAGE_MEDIA,
//            purpose = DatastorePurpose.valueOf(DUMMY_IMAGE_PURPOSE),
//            checksum = "",
//            length = 63736,
//            imageWidth = 250,
//            imageHeight = 167,
//        )
//        datastore = datastoreRequestDto.toDatastore(resellerList[0], clientList[0], workRequestList[0])
//        datastoreList.add(datastoreService.save(datastore))
//
//        datastoreRequestDto = DatastoreRequestDto(
//            clientId = clientList[0].id,
//            resellerId = resellerList[0].id,
//            workRequestId = workRequestList[0].id,
//            dataName = DUMMY_IMAGE_NAME,
//            description = "Dummy Image 2",
//            mediaType = DUMMY_IMAGE_MEDIA,
//            purpose = DatastorePurpose.valueOf(DUMMY_IMAGE_PURPOSE),
//            checksum = "",
//            length = 63736,
//            imageWidth = 250,
//            imageHeight = 167,
//        )
//        datastore = datastoreRequestDto.toDatastore(resellerList[0], clientList[0], workRequestList[0])
//        datastoreList.add(datastoreService.save(datastore))
//
//        datastoreRequestDto = DatastoreRequestDto(
//            clientId = clientList[0].id,
//            resellerId = resellerList[0].id,
//            workRequestId = workRequestList[0].id,
//            dataName = DUMMY_IMAGE_NAME,
//            description = "Dummy Image 3",
//            mediaType = DUMMY_IMAGE_MEDIA,
//            purpose = DatastorePurpose.valueOf(DUMMY_IMAGE_PURPOSE),
//            checksum = "",
//            length = 63736,
//            imageWidth = 250,
//            imageHeight = 167,
//        )
//        datastore = datastoreRequestDto.toDatastore(resellerList[0], clientList[0], workRequestList[0])
//        datastoreList.add(datastoreService.save(datastore))
//    }
//
//    fun addWorkTypes() {
//        tenantIdentifierResolver.setCurrentTenant("1")
//        val workTypeRequestDto = WorkTypeRequestDto(
//            clientId = 0,
//            name = "annual ac cleaning/tune up",
//            description = "annual ac cleaning/tune up",
//            price = 89.00,
//            estimatedDuration = 30.minutes
//        )
//        val workType = workTypeRequestDto.toWorkType(clientList[0])
//        workTypeList.add(workTypeService.save(workType))
//    }
//
//    fun addWorkRequests() {
//        tenantIdentifierResolver.setCurrentTenant("1")
//        // -- resellerId, clientId, customerId, servlocId
//        var workRequestRequestDto = WorkRequestRequestDto(
//            id = 0, // must be zero for insert
//            servLocId = servLocList[0].id,
//            description = "WR 1 for Customer 1",
//            estimateDuration = 30.minutes,
//            estimateRequired = true,
//            notes = "Technician notes go here",
//            photoIds = listOf(1, 2, 3, 4),
//            prefTechId = fsaUserList[0].id,
//            recurring = false,
//            num = 103,
//            prevStatus = WorkRequestStatus.EstApproved,
//            status = WorkRequestStatus.EstScheduled,
//            skills = listOf(
//                SkillSet("ChainSaw-Basic", "ChainSaw-Basic description"),
//                SkillSet("FakeSkill-5", "FakeSkill-5 description"),
//                SkillSet("FakeSkill-6", "FakeSkill-6 description")
//            )
//        )
//        var workReq = workRequestRequestDto.toWorkRequest(
//            servLocList[0]
//        )
//        workRequestList.add(workRequestService.insert(workReq))
//
//        workRequestRequestDto = WorkRequestRequestDto(
//            id = 0, // must be zero for insert
//            servLocId = servLocList[1].id,
//            description = "WR 2 for Customer 1 for ServLoc 2",
//            estimateRequired = true,
//            estimateDuration = 30.minutes,
//            notes = "Technician notes go here",
//            photoIds = listOf(1, 2, 3, 4),
//            prefTechId = fsaUserList[0].id,
//            recurring = false,
//            num = 104,
//            prevStatus = WorkRequestStatus.Created,
//            status = WorkRequestStatus.Scheduled,
//            skills = listOf(
//                SkillSet("ChainSaw-Master", "ChainSaw-Master description"),
//                SkillSet("FakeSkill-7", "FakeSkill-7 description"),
//                SkillSet("FakeSkill-8", "FakeSkill-8 description")
//            )
//        )
//        workReq = workRequestRequestDto.toWorkRequest(
//            servLocList[1]
//        )
//        workRequestList.add(workRequestService.insert(workReq))
//
//        workRequestRequestDto = WorkRequestRequestDto(
//            id = 0, // must be zero for insert
//            servLocId = servLocList[1].id,
//            description = "WR 1 for Customer 2 for ServLoc 2",
//            estimateDuration = 30.minutes,
//            estimateRequired = true,
//            notes = "Technician notes go here",
//            photoIds = listOf(1, 2, 3, 4),
//            prefTechId = fsaUserList[0].id,
//            recurring = false,
//            num = 104,
//            prevStatus = WorkRequestStatus.Created,
//            status = WorkRequestStatus.EstCreated,
//            skills = listOf(
//                SkillSet("ChainSaw-Master", "ChainSaw-Master description"),
//                SkillSet("FakeSkill-7", "FakeSkill-7 description"),
//                SkillSet("FakeSkill-8", "FakeSkill-8 description")
//            )
//        )
//        workReq = workRequestRequestDto.toWorkRequest(
//            servLocList[1]
//        )
//        workRequestList.add(workRequestService.insert(workReq))
//
//        workRequestRequestDto = WorkRequestRequestDto(
//            id = 0, // must be zero for insert
//            servLocId = servLocList[1].id,
//            description = "WR 2 for Customer 2 for ServLoc 2",
//            estimateDuration = 30.minutes,
//            estimateRequired = true,
//            notes = "Technician notes go here",
//            photoIds = listOf(1, 2, 3, 4),
//            prefTechId = fsaUserList[0].id,
//            recurring = false,
//            num = 104,
//            prevStatus = WorkRequestStatus.Created,
//            status = WorkRequestStatus.EstCreated,
//            skills = listOf(
//                SkillSet("ChainSaw-Master", "ChainSaw-Master description"),
//                SkillSet("FakeSkill-7", "FakeSkill-7 description"),
//                SkillSet("FakeSkill-8", "FakeSkill-8 description")
//            )
//        )
//        workReq = workRequestRequestDto.toWorkRequest(
//            servLocList[1]
//        )
//        workRequestList.add(workRequestService.insert(workReq))
//
//        workRequestRequestDto = WorkRequestRequestDto(
//            id = 0, // must be zero for insert
//            servLocId = servLocList[1].id,
//            description = "WR 3 for Customer 2 for ServLoc 2",
//            num = 104,
//            estimateDuration = 30.minutes,
//            estimateRequired = true,
//            notes = "Technician notes go here",
//            photoIds = listOf(1, 2, 3, 4),
//            prefTechId = fsaUserList[0].id,
//            recurring = false,
//            prevStatus = WorkRequestStatus.Created,
//            status = WorkRequestStatus.InvoiceCreated,
//            skills = listOf(
//                SkillSet("ChainSaw-Master", "ChainSaw-Master description"),
//                SkillSet("FakeSkill-7", "FakeSkill-7 description"),
//                SkillSet("FakeSkill-8", "FakeSkill-8 description")
//            )
//        )
//        workReq = workRequestRequestDto.toWorkRequest(
//            servLocList[1]
//        )
//        workRequestList.add(workRequestService.insert(workReq))
//
//        workRequestRequestDto = WorkRequestRequestDto(
//            id = 0, // must be zero for insert
//            servLocId = servLocList[1].id,
//            description = "WR 4 for Customer 2 for ServLoc 2",
//            estimateDuration = 30.minutes,
//            estimateRequired = true,
//            notes = "Technician notes go here",
//            photoIds = listOf(1, 2, 3, 4),
//            prefTechId = fsaUserList[0].id,
//            recurring = false,
//            num = 104,
//            prevStatus = WorkRequestStatus.Created,
//            status = WorkRequestStatus.InvoiceCreated,
//            skills = listOf(
//                SkillSet("ChainSaw-Master", "ChainSaw-Master description"),
//                SkillSet("FakeSkill-7", "FakeSkill-7 description"),
//                SkillSet("FakeSkill-8", "FakeSkill-8 description")
//            )
//        )
//        workReq = workRequestRequestDto.toWorkRequest(
//            servLocList[1]
//        )
//        workRequestList.add(workRequestService.insert(workReq))
//
//        workRequestRequestDto = WorkRequestRequestDto(
//            id = 0, // must be zero for insert
//            servLocId = servLocList[1].id,
//            description = "WR 5 for Customer 2 for ServLoc 2",
//            estimateDuration = 30.minutes,
//            estimateRequired = true,
//            notes = "Technician notes go here",
//            photoIds = listOf(),
//            prefTechId = fsaUserList[0].id,
//            recurring = false,
//            num = 104,
//            prevStatus = WorkRequestStatus.Created,
//            status = WorkRequestStatus.InvoiceCreated,
//            skills = listOf(
//                SkillSet("ChainSaw-Master", "ChainSaw-Master description"),
//                SkillSet("FakeSkill-7", "FakeSkill-7 description"),
//                SkillSet("FakeSkill-8", "FakeSkill-8 description")
//            )
//        )
//        workReq = workRequestRequestDto.toWorkRequest(
//            servLocList[1]
//        )
//        workRequestList.add(workRequestService.insert(workReq))
//    }
//
//    fun addScheduleItems() {
//        tenantIdentifierResolver.setCurrentTenant("1")
//        val clientAdmin = fsaUserService.findByEmailIgnoreCase("clientAdmin@example.com") ?: throw Exception("bad user")
//        val tech1 = fsaUserService.findByEmailIgnoreCase("mobile1@example.com") ?: throw Exception("bad user")
//        val tech2 = fsaUserService.findByEmailIgnoreCase("mobile2@example.com") ?: throw Exception("bad user")
//        val scheduleDay = "2023-01-01"
//        val tomorrow = "2023-01-02"
//
//        var scheduleItemRequestDto = ScheduleItemRequestDto(
//            id = 0, // must be zero for insert
//            workRequestId = workRequestList[0].id,
//            techId = tech1.id,
//            scheduledById = clientAdmin.id,
//            scheduleDay = LocalDate.parse(scheduleDay),
//            beginTime = LocalTime.of(8, 0, 0),
//            endTime = LocalTime.of(9, 0, 0),
//            skillUsed = "ChainSaw-Basic",
//            scheduleType = ScheduleType.EstimateSchedule
//        )
//        var scheduleItem = scheduleItemRequestDto.toScheduleItem(
//            workRequest = workRequestList[0],
//            scheduledBy = clientAdmin,
//            tech = tech1,
//        )
//        scheduleItem = scheduleItemService.insert(scheduleItem)
//        scheduleItemList.add(scheduleItem)
//
//        scheduleItemRequestDto = ScheduleItemRequestDto(
//            id = 0, // must be zero for insert
//            workRequestId = workRequestList[1].id,
//            techId = tech1.id,
//            scheduledById = clientAdmin.id,
//            scheduleDay = LocalDate.parse(scheduleDay),
//            beginTime = LocalTime.of(9, 0, 0),
//            endTime = LocalTime.of(10, 0, 0),
//            skillUsed = "ChainSaw-Basic",
//            scheduleType = ScheduleType.JobSchedule
//        )
//        scheduleItem = scheduleItemRequestDto.toScheduleItem(
//            workRequest = workRequestList[1],
//            scheduledBy = clientAdmin,
//            tech = tech1,
//        )
//        scheduleItem = scheduleItemService.insert(scheduleItem)
//        scheduleItemList.add(scheduleItem)
//
//        scheduleItemRequestDto = ScheduleItemRequestDto(
//            id = 0, // must be zero for insert
//            workRequestId = workRequestList[2].id,
//            techId = tech1.id,
//            scheduledById = fsaUserList[2].id,
//            scheduleDay = LocalDate.parse(scheduleDay),
//            beginTime = LocalTime.of(11, 0, 0),
//            endTime = LocalTime.of(12, 0, 0),
//            skillUsed = "ChainSaw-Basic",
//            scheduleType = ScheduleType.JobSchedule
//        )
//        scheduleItem = scheduleItemRequestDto.toScheduleItem(
//            workRequestList[2],
//            clientAdmin,
//            tech1
//        )
//        scheduleItem = scheduleItemService.insert(scheduleItem)
//        scheduleItemService.deleteSoft(scheduleItem)
//
//        scheduleItemRequestDto = ScheduleItemRequestDto(
//            id = 0, // must be zero for insert
//            workRequestId = workRequestList[2].id,
//            techId = tech2.id,
//            scheduledById = fsaUserList[1].id,
//            scheduleDay = LocalDate.parse(scheduleDay),
//            beginTime = LocalTime.of(10, 0, 0),
//            endTime = LocalTime.of(11, 0, 0),
//            skillUsed = "ChainSaw-Basic",
//            scheduleType = ScheduleType.EstimateSchedule
//        )
//        scheduleItem = scheduleItemRequestDto.toScheduleItem(
//            workRequestList[2],
//            clientAdmin,
//            tech2
//        )
//        scheduleItem = scheduleItemService.insert(scheduleItem)
//        scheduleItemList.add(scheduleItem)
//
//        scheduleItemRequestDto = ScheduleItemRequestDto(
//            id = 0, // must be zero for insert
//            workRequestId = workRequestList[3].id,
//            techId = tech1.id,
//            scheduledById = fsaUserList[2].id,
//            scheduleDay = LocalDate.parse(scheduleDay),
//            beginTime = LocalTime.of(12, 0, 0),
//            endTime = LocalTime.of(13, 0, 0),
//            skillUsed = "ChainSaw-Basic",
//            scheduleType = ScheduleType.JobSchedule
//        )
//        scheduleItem = scheduleItemRequestDto.toScheduleItem(
//            workRequestList[3],
//            clientAdmin,
//            tech1
//        )
//        scheduleItem = scheduleItemService.insert(scheduleItem)
//        scheduleItemList.add(scheduleItem)
//
//        scheduleItemRequestDto = ScheduleItemRequestDto(
//            id = 0, // must be zero for insert
//            workRequestId = workRequestList[3].id,
//            techId = tech2.id,
//            scheduledById = fsaUserList[1].id,
//            scheduleDay = LocalDate.parse(scheduleDay),
//            beginTime = LocalTime.of(11, 0, 0),
//            endTime = LocalTime.of(12, 0, 0),
//            skillUsed = "ChainSaw-Basic",
//            scheduleType = ScheduleType.EstimateSchedule
//        )
//        scheduleItem = scheduleItemRequestDto.toScheduleItem(
//            workRequestList[3],
//            clientAdmin,
//            tech2
//        )
//        scheduleItem = scheduleItemService.insert(scheduleItem)
//        scheduleItemList.add(scheduleItem)
//
//        scheduleItemRequestDto = ScheduleItemRequestDto(
//            id = 0, // must be zero for insert
//            workRequestId = workRequestList[4].id,
//            techId = tech1.id,
//            scheduledById = fsaUserList[2].id,
//            scheduleDay = LocalDate.parse(scheduleDay),
//            beginTime = LocalTime.of(13, 0, 0),
//            endTime = LocalTime.of(14, 0, 0),
//            skillUsed = "ChainSaw-Basic",
//            scheduleType = ScheduleType.JobSchedule
//        )
//        scheduleItem = scheduleItemRequestDto.toScheduleItem(
//            workRequestList[4],
//            clientAdmin,
//            tech1
//        )
//        scheduleItem = scheduleItemService.insert(scheduleItem)
//        scheduleItemList.add(scheduleItem)
//
//        scheduleItemRequestDto = ScheduleItemRequestDto(
//            id = 0, // must be zero for insert
//            workRequestId = workRequestList[4].id,
//            techId = tech2.id,
//            scheduledById = fsaUserList[1].id,
//            scheduleDay = LocalDate.parse(scheduleDay),
//            beginTime = LocalTime.of(12, 0, 0),
//            endTime = LocalTime.of(13, 0, 0),
//            skillUsed = "ChainSaw-Basic",
//            scheduleType = ScheduleType.EstimateSchedule
//        )
//        scheduleItem = scheduleItemRequestDto.toScheduleItem(
//            workRequestList[4],
//            clientAdmin,
//            tech2
//        )
//        scheduleItem = scheduleItemService.insert(scheduleItem)
//        scheduleItemList.add(scheduleItem)
//
//        scheduleItemRequestDto = ScheduleItemRequestDto(
//            id = 0, // must be zero for insert
//            workRequestId = workRequestList[5].id,
//            techId = tech1.id,
//            scheduledById = fsaUserList[2].id,
//            scheduleDay = LocalDate.parse(tomorrow),
//            beginTime = LocalTime.of(14, 0, 0),
//            endTime = LocalTime.of(15, 0, 0),
//            skillUsed = "ChainSaw-Basic",
//            scheduleType = ScheduleType.JobSchedule
//        )
//        scheduleItem = scheduleItemRequestDto.toScheduleItem(
//            workRequestList[5],
//            clientAdmin,
//            tech1
//        )
//        scheduleItem = scheduleItemService.insert(scheduleItem)
//        scheduleItemList.add(scheduleItem)
//
//        scheduleItemRequestDto = ScheduleItemRequestDto(
//            id = 0, // must be zero for insert
//            workRequestId = workRequestList[5].id,
//            techId = tech2.id,
//            scheduledById = fsaUserList[1].id,
//            scheduleDay = LocalDate.parse(tomorrow),
//            beginTime = LocalTime.of(13, 0, 0),
//            endTime = LocalTime.of(14, 0, 0),
//            skillUsed = "ChainSaw-Basic",
//            scheduleType = ScheduleType.EstimateSchedule
//        )
//        scheduleItem = scheduleItemRequestDto.toScheduleItem(
//            workRequestList[5],
//            clientAdmin,
//            tech2
//        )
//        scheduleItem = scheduleItemService.insert(scheduleItem)
//        scheduleItemList.add(scheduleItem)
//    }
//
//    fun addScheduleItemWorkedHours() {
//        tenantIdentifierResolver.setCurrentTenant("1")
//        var workedHourRequestDto = ScheduleItemWorkedHourRequestDto(
//            id = 0,
//            scheduleItemId = 1,
//            startDateTime = OffsetDateTime.now().minusHours(1),
//            endDateTime = OffsetDateTime.now(),
//            startTimeLatitude = 1.0,
//            startTimeLongitude = 1.0
//        )
//        var workedHour = workedHourRequestDto.toScheduleItemWorkedHour(scheduleItemList[0])
//        workedHour = scheduleItemWorkedHourService.insert(workedHour)
//        workedHourList.add(workedHour)
//
//        workedHourRequestDto = ScheduleItemWorkedHourRequestDto(
//            id = 0,
//            scheduleItemId = 1,
//            startDateTime = OffsetDateTime.now().minusHours(1),
//            endDateTime = OffsetDateTime.now(),
//            startTimeLatitude = 1.0,
//            startTimeLongitude = 1.0
//        )
//        workedHour = workedHourRequestDto.toScheduleItemWorkedHour(scheduleItemList[0])
//        workedHour = scheduleItemWorkedHourService.insert(workedHour)
//        workedHourList.add(workedHour)
//    }
//
//    fun addEstimates() {
//        tenantIdentifierResolver.setCurrentTenant("1")
//        var estimateRequestDto = EstimateRequestDto(
//            id = 0,
//            workRequestId = 3,
//            notes = "Estimate notes",
//            dateSent = OffsetDateTime.now(),
//            dateApproved = OffsetDateTime.now(),
//            createdById = fsaUserList[0].id,
//            deposit = 20.toDouble(),
//            hours = 0.minutes,
//            itemCost = 10.toDouble(),
//            subTotal = 0.toDouble(),
//            tax = 0.toDouble(),
//            taxPercentage = 0.10,
//            taxable = 0.toDouble(),
//            total = 0.toDouble()
//        )
//        var estimate = estimateRequestDto.toEstimate(workRequestList[2], fsaUserList[0])
//        estimate = estimateService.insert(estimate)
//        estimateList.add(estimate)
//
//        estimateRequestDto = EstimateRequestDto(
//            id = 0,
//            workRequestId = 4,
//            notes = "Estimate 2 notes",
//            dateSent = OffsetDateTime.now(),
//            dateApproved = OffsetDateTime.now(),
//            createdById = fsaUserList[0].id,
//            deposit = 20.toDouble(),
//            hours = 0.minutes,
//            itemCost = 10.toDouble(),
//            subTotal = 0.toDouble(),
//            tax = 0.toDouble(),
//            taxPercentage = 0.10,
//            taxable = 0.toDouble(),
//            total = 0.toDouble()
//        )
//        estimate = estimateRequestDto.toEstimate(workRequestList[3], fsaUserList[0])
//        estimate = estimateService.insert(estimate)
//        estimateList.add(estimate)
//
//        estimateRequestDto = EstimateRequestDto(
//            id = 0,
//            workRequestId = 5,
//            notes = "Estimate 3 notes",
//            dateSent = OffsetDateTime.now(),
//            dateApproved = OffsetDateTime.now(),
//            createdById = fsaUserList[0].id,
//            deposit = 20.toDouble(),
//            hours = 0.minutes,
//            itemCost = 10.toDouble(),
//            subTotal = 0.toDouble(),
//            tax = 0.toDouble(),
//            taxPercentage = 0.10,
//            taxable = 0.toDouble(),
//            total = 0.toDouble()
//        )
//        estimate = estimateRequestDto.toEstimate(workRequestList[4], fsaUserList[0])
//        estimate = estimateService.insert(estimate)
//        estimateList.add(estimate)
//
//        estimateRequestDto = EstimateRequestDto(
//            id = 0,
//            workRequestId = 6,
//            notes = "Estimate 4 notes",
//            dateSent = OffsetDateTime.now(),
//            dateApproved = OffsetDateTime.now(),
//            createdById = fsaUserList[0].id,
//            deposit = 20.toDouble(),
//            hours = 0.minutes,
//            itemCost = 10.toDouble(),
//            subTotal = 0.toDouble(),
//            tax = 0.toDouble(),
//            taxPercentage = 0.10,
//            taxable = 0.toDouble(),
//            total = 0.toDouble()
//        )
//        estimate = estimateRequestDto.toEstimate(workRequestList[5], fsaUserList[0])
//        estimate = estimateService.insert(estimate)
//        estimateList.add(estimate)
//    }
//
//    fun addInvoices() {
//        tenantIdentifierResolver.setCurrentTenant("1")
//        var invoiceRequestDto = InvoiceRequestDto(
//            id = 0,
//            workRequestId = 3,
//            notes = "Invoice notes",
//            dateSent = OffsetDateTime.now(),
//            datePaid = OffsetDateTime.now(),
//            createdById = fsaUserList[0].id,
//            dateDue = OffsetDateTime.now(),
//            deposit = 20.toDouble(),
//            subTotal = 0.toDouble(),
//            tax = 0.toDouble(),
//            taxPercentage = 0.10,
//            taxable = 0.toDouble(),
//            total = 0.toDouble()
//        )
//        var invoice = invoiceRequestDto.toInvoice(workRequestList[2], fsaUserList[0])
//        invoice = invoiceService.insert(invoice)
//        invoiceList.add(invoice)
//
//        invoiceRequestDto = InvoiceRequestDto(
//            id = 0,
//            workRequestId = 4,
//            notes = "Invoice 2 notes",
//            dateSent = OffsetDateTime.now(),
//            datePaid = OffsetDateTime.now(),
//            createdById = fsaUserList[0].id,
//            dateDue = OffsetDateTime.now(),
//            deposit = 20.toDouble(),
//            subTotal = 0.toDouble(),
//            tax = 0.toDouble(),
//            taxPercentage = 0.10,
//            taxable = 0.toDouble(),
//            total = 0.toDouble()
//        )
//        invoice = invoiceRequestDto.toInvoice(workRequestList[3], fsaUserList[0])
//        invoice = invoiceService.insert(invoice)
//        invoiceList.add(invoice)
//    }
//
//    fun addWorkTypeItems() {
//        tenantIdentifierResolver.setCurrentTenant("1")
//        var workTypeItemRequestDto = WorkTypeItemRequestDto(
//            id = 0,
//            workRequestId = 1,
//            name = "Sample Work Name",
//            description = "Sample Work Description",
//            duration = 30.minutes,
//            quantity = 2,
//            unitPrice = 30.toDouble(),
//            totalPrice = 60.toDouble(),
//            isTaxable = true
//        )
//        var workTypeItem = workTypeItemRequestDto.toWorkTypeItem(workRequestList[0])
//        workTypeItem = workTypeItemService.insert(workTypeItem)
//        workTypeItemList.add(workTypeItem)
//
//        workTypeItemRequestDto = WorkTypeItemRequestDto(
//            id = 0,
//            workRequestId = 2,
//            name = "Sample Work Name 2",
//            description = "Sample Work Description 2",
//            duration = 1.hours,
//            quantity = 3,
//            unitPrice = 25.toDouble(),
//            totalPrice = 75.toDouble(),
//            isTaxable = true
//        )
//        workTypeItem = workTypeItemRequestDto.toWorkTypeItem(workRequestList[1])
//        workTypeItem = workTypeItemService.insert(workTypeItem)
//        workTypeItemList.add(workTypeItem)
//
//        workTypeItemRequestDto = WorkTypeItemRequestDto(
//            id = 0,
//            workRequestId = 3,
//            name = "Sample Work Name 3",
//            description = "Sample Work Description 3",
//            duration = 1.hours,
//            quantity = 3,
//            unitPrice = 25.toDouble(),
//            totalPrice = 75.toDouble(),
//            isTaxable = true
//        )
//        workTypeItem = workTypeItemRequestDto.toWorkTypeItem(workRequestList[2])
//        workTypeItem = workTypeItemService.insert(workTypeItem)
//        workTypeItemList.add(workTypeItem)
//
//        workTypeItemRequestDto = WorkTypeItemRequestDto(
//            id = 0,
//            workRequestId = 4,
//            name = "Sample Work Name 4",
//            description = "Sample Work Description 4",
//            duration = 1.hours,
//            quantity = 3,
//            unitPrice = 25.toDouble(),
//            totalPrice = 75.toDouble(),
//            isTaxable = true
//        )
//        workTypeItem = workTypeItemRequestDto.toWorkTypeItem(workRequestList[3])
//        workTypeItem = workTypeItemService.insert(workTypeItem)
//        workTypeItemList.add(workTypeItem)
//
//        workTypeItemRequestDto = WorkTypeItemRequestDto(
//            id = 0,
//            workRequestId = 5,
//            name = "Sample Work Name 5",
//            description = "Sample Work Description 5",
//            duration = 1.hours,
//            quantity = 3,
//            unitPrice = 25.toDouble(),
//            totalPrice = 75.toDouble(),
//            isTaxable = true
//        )
//        workTypeItem = workTypeItemRequestDto.toWorkTypeItem(workRequestList[4])
//        workTypeItem = workTypeItemService.insert(workTypeItem)
//        workTypeItemList.add(workTypeItem)
//
//        workTypeItemRequestDto = WorkTypeItemRequestDto(
//            id = 0,
//            workRequestId = 6,
//            name = "Sample Work Name 6",
//            description = "Sample Work Description 6",
//            duration = 1.hours,
//            quantity = 3,
//            unitPrice = 25.toDouble(),
//            totalPrice = 75.toDouble(),
//            isTaxable = true
//        )
//        workTypeItem = workTypeItemRequestDto.toWorkTypeItem(workRequestList[5])
//        workTypeItem = workTypeItemService.insert(workTypeItem)
//        workTypeItemList.add(workTypeItem)
//    }
}