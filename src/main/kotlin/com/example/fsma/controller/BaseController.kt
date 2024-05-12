// ----------------------------------------------------------------------------
// Copyright Kaleidoscope, Inc. or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.example.fsma.controller

//import com.example.fsma.service.CteCoolService
import com.example.fsma.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

// -- @RequestParam date format (ISO8601)
const val REQ_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

private const val HEADER_Page = "Page"
private const val HEADER_PageSize = "Page-Size"
private const val HEADER_TotalPages = "Total-Pages"
private const val HEADER_TotalElements = "Total-Elements"

@Component
class BaseController {

//    internal val logger by LoggerDelegate()

    @Autowired protected lateinit var addressService: AddressService
    @Autowired protected lateinit var businessService: BusinessService
    @Autowired protected lateinit var locationService: LocationService
    @Autowired protected lateinit var traceLotCodeService: TraceLotCodeService

    @Autowired protected lateinit var cteCoolService: CteCoolService
    @Autowired protected lateinit var cteHarvestService: CteHarvestService
    @Autowired protected lateinit var cteReceiveService: CteReceiveService

//    fun getFsaUser(id: Long, authPrincipal: FsaUser): FsaUser {
//        val fsaUser = fsaUserService.findById(id)
//            ?: throw EntityNotFoundException("FsaUser not found: $id")
//
//        if (authPrincipal.isParentResellerAdminOnly() &&
//            (
//                authPrincipal.resellerId == fsaUser.resellerId ||
//                    authPrincipal.resellerId == fsaUser.reseller.parentReseller?.id
//                )
//        ) {
//            return fsaUser
//        } else if (!authPrincipal.isParentResellerAdminOnly()) {
//            assertResellerClientMatchesToken(authPrincipal, fsaUser.resellerId, fsaUser.client.id)
//        }
//
//        return fsaUser
//    }
//
//    fun getAddress(id: Long, authPrincipal: FsaUser): Address {
//        val address = addressService.findById(id)
//            ?: throw EntityNotFoundException("Address not found: $id")
//        assertResellerClientMatchesToken(authPrincipal, address.resellerId)
//        return address
//    }
//
//    fun getCustomer(id: Long, authPrincipal: FsaUser): Customer {
//        val customer = customerService.findById(id)
//            ?: throw EntityNotFoundException("Customer not found: $id")
//        assertResellerClientMatchesToken(authPrincipal, customer.resellerId, customer.client.id)
//        return customer
//    }
//
//    fun getScheduleItem(id: Long, authPrincipal: FsaUser): ScheduleItem {
//        val scheduleItem = scheduleItemService.findById(id)
//            ?: throw EntityNotFoundException("ScheduleItem not found: $id")
//        assertResellerClientMatchesToken(
//            authPrincipal,
//            scheduleItem.workRequest.reseller.id,
//            scheduleItem.workRequest.client.id
//        )
//        return scheduleItem
//    }
//
//    fun getServLoc(id: Long, authPrincipal: FsaUser): ServLoc {
//        val servLoc = servLocService.findById(id)
//            ?: throw EntityNotFoundException("ServLoc not found: $id")
//        assertResellerClientMatchesToken(authPrincipal, servLoc.reseller.id, servLoc.client.id)
//        return servLoc
//    }
//
//    fun getWorkRequest(id: Long, authPrincipal: FsaUser): WorkRequest {
//        val workRequest = workRequestService.findById(id)
//            ?: throw EntityNotFoundException("WorkRequest not found: $id")
//        assertResellerClientMatchesToken(authPrincipal, workRequest.reseller.id, workRequest.client.id)
//        return workRequest
//    }
//
//    fun getWorkTypeItem(id: Long, authPrincipal: FsaUser): WorkTypeItem {
//        val workTypeItem = workTypeItemService.findById(id)
//            ?: throw EntityNotFoundException("WorkTypeItem not found: $id")
//        assertResellerClientMatchesToken(authPrincipal, workTypeItem.reseller.id, workTypeItem.client.id)
//        return workTypeItem
//    }
//
//    fun getWorkRequestHistory(id: Long, authPrincipal: FsaUser): WorkRequestHistory {
//        val workRequestHistory = workRequestHistoryService.findById(id)
//            ?: throw EntityNotFoundException("WorkType not found: $id")
//        assertResellerClientMatchesToken(authPrincipal, workRequestHistory.reseller.id, workRequestHistory.client.id)
//        return workRequestHistory
//    }
//
//    fun getWorkType(id: Long, authPrincipal: FsaUser): WorkType {
//        val workType = workTypeService.findById(id)
//            ?: throw EntityNotFoundException("WorkType not found: $id")
//        assertResellerClientMatchesToken(authPrincipal, workType.reseller.id, workType.client.id)
//        return workType
//    }
//
//    fun getInvoice(id: Long, authPrincipal: FsaUser): Invoice {
//        val invoice = invoiceService.findById(id)
//            ?: throw EntityNotFoundException("Invoice not found: $id")
//        assertResellerClientMatchesToken(authPrincipal, invoice.reseller.id, invoice.client.id)
//        return invoice
//    }
//
//    fun getEstimate(id: Long, authPrincipal: FsaUser): Estimate {
//        val estimate = estimateService.findById(id)
//            ?: throw EntityNotFoundException("Estimate not found: $id")
//        assertResellerClientMatchesToken(authPrincipal, estimate.reseller.id, estimate.client.id)
//        return estimate
//    }
//
//    // ----------------------------
//
//    fun getDatastore(id: Long, authPrincipal: FsaUser): Datastore {
//        val datastore = datastoreService.findById(id)
//            ?: throw EntityNotFoundException("Datastore not found: $id")
//        if (!authPrincipal.isRootAdmin()) {
//            if (datastore.clientId != null) {
//                assertResellerClientMatchesToken(authPrincipal, datastore.resellerId, datastore.clientId)
//            } else {
//                assertResellerMatchesToken(authPrincipal, datastore.resellerId)
//            }
//        }
//
//        return datastore
//    }
//
//    // ----------------------------
//
//    protected fun assertResellerClientMatchesToken(
//        authPrincipal: FsaUser,
//        modelResellerId: Long,
//        modelClientId: Long? = null
//    ) {
//        if (
//            authPrincipal.isRootAdmin() ||
//            isResellerCheck(authPrincipal, modelResellerId) ||
//            isClientCheck(authPrincipal, modelResellerId, modelClientId)
//        ) return
//
//        // Permissions are wrong
//        throw BadRequestException("Invalid request")
//    }
//
//    private fun isResellerCheck(authPrincipal: FsaUser, modelResellerId: Long): Boolean {
//        return authPrincipal.isResellerAdmin() && modelResellerId == authPrincipal.reseller.id
//    }
//
//    private fun isClientCheck(authPrincipal: FsaUser, modelResellerId: Long, modelClientId: Long?): Boolean {
//        return (authPrincipal.isClientAdmin() || authPrincipal.isCoordinator() || authPrincipal.isMobile()) &&
//            modelResellerId == authPrincipal.reseller.id && modelClientId != null && modelClientId == authPrincipal.client.id
//    }
//
//    protected fun assertResellerMatchesToken(
//        authPrincipal: FsaUser,
//        modelResellerId: Long
//    ) {
//        if (
//            authPrincipal.isRootAdmin() ||
//            modelResellerId == authPrincipal.reseller.id
//        ) return
//
//        // Permissions are wrong
//        throw BadRequestException("Invalid request")
//    }
//
//    protected fun generatePaginationHeaders(
//        page: Int,
//        pageSize: Int,
//        totalPages: Int,
//        totalElements: Long
//    ): HttpHeaders {
//        return HttpHeaders().apply {
//            add(
//                HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
//                "$HEADER_Page, $HEADER_PageSize, $HEADER_TotalPages, $HEADER_TotalElements"
//            )
//            add(HEADER_Page, page.toString())
//            add(HEADER_PageSize, pageSize.toString())
//            add(HEADER_TotalPages, totalPages.toString())
//            add(HEADER_TotalElements, totalElements.toString())
//        }
//    }
}
