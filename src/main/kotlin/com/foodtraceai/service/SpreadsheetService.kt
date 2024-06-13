// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.service

import com.foodtraceai.service.cte.CteReceiveService
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

// Example taken from
// https://www.baeldung.com/java-microsoft-excel

// See also
// https://stackoverflow.com/questions/51684550/how-to-download-an-excel-file-in-spring-restcontroller

// To Run it  http://localhost:8080/api/v1/sheet/cte?which=receive
@Service
class SpreadsheetService(
    private val cteReceiveService: CteReceiveService,
) {
    private val receiveColHeaders = listOf(
        "(a)(1) TLC - GTIN",
        "(a)(1) TLC - Batch",
        "(a)(1) TLC - Date**",
        "(a)(1) TLC - Date Type**",
        "(a)(1) TLC - SSCC**",
        "(b)(1) TLC - Assigned By",
        "(a)(2) Qty & UOM",
        "(a)(3) Product Description",
        "(a)(4) Immediate Previous Source (IPS) Location - (Shipped from Location)",
        "(a)(5) Receive Location",
        "(a)(6) Receive Date",
        "(a)(7) TLC Source ReferenceGLN",
        "(a)(7) TLC Source ReferenceFFRN",
        "(a)(7) TLC Source ReferenceURL",
        "(a)(7) TLC Source ReferenceGGN",
        "(b)(5) TLC Source Reference - Assigned By",
        "(a)(8) Ref Doc",
        "(a)(7) TLC Source Name",
        "(a)(7) TLC Source Street",
        "(a)(7) TLC Source City",
        "(a)(7) TLC Source State",
        "(a)(7) TLC Source Country",
        "(a)(7) TLC Source Zip Code",
        "(a)(7) TLC Source Phone Number",
    )

    private fun makeReceivingTab(workbook: Workbook) {
        val receiving: Sheet = workbook.createSheet("Receiving")
        receiveColHeaders.forEachIndexed { idx, title ->
            receiving.setColumnWidth(idx, 5000)
        }

        val header: Row = receiving.createRow(0)

        val headerStyle: CellStyle = workbook.createCellStyle()
//        headerStyle.fillForegroundColor = IndexedColors.LIGHT_BLUE.getIndex()
//        headerStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

        val font = (workbook as XSSFWorkbook).createFont()
        font.fontName = "Arial"
        font.fontHeightInPoints = 11.toShort()
        font.bold = true
        headerStyle.setFont(font)

        receiveColHeaders.forEachIndexed { idx, title ->
            val headerCell = header.createCell(idx)
            headerCell.setCellValue(title)
            headerCell.setCellStyle(headerStyle)
        }

        // ***************  Content Rows **********
        val style = workbook.createCellStyle()
        style.wrapText = false
//        style.shrinkToFit = true
        style.alignment = HorizontalAlignment.CENTER

        for (i in 1..3) {
            val cte = cteReceiveService.findById(i.toLong())
                ?: throw Exception("cteReceive not found row: $i")

            val row = receiving.createRow(i)
            receiveColHeaders.forEachIndexed { col, title ->
                val cell = row.createCell(col)
                cell.cellStyle = style
                val letter = 'A' + col
                when (letter) {
                    'A' -> cell.setCellValue(cte.tlc.tlc)
                    'B' -> cell.setCellValue(cte.tlc.batch)
                    'C' -> cell.setCellValue(cte.tlc.tlcDate)
                    'D' -> cell.setCellValue(cte.tlc.tlcDateType?.name)
                    'E' -> cell.setCellValue(cte.tlc.sscc)
                    'F' -> {}
                    'G' -> cell.setCellValue("${cte.quantity} ${cte.unitOfMeasure.name}")
                    'H' -> cell.setCellValue(cte.foodDesc)
                    'I' -> cell.setCellValue(cte.prevSourceLocation.foodBus.foodBusName)
                    'J' -> cell.setCellValue(cte.receiveLocation.foodBus.foodBusName)
                    'K' -> cell.setCellValue(cte.receiveDate)
                    'M' -> cell.setCellValue(cte.tlcSourceReference)
                    'Q' -> cell.setCellValue(cte.referenceDocumentNum)
                    else -> {}
                }
            }
        }

//        val row = receiving.createRow(2)
//        var cell = row.createCell(0)
//        cell.setCellValue("John Smith")
//        cell.cellStyle = style
//
//        cell = row.createCell(1)
//        cell.setCellValue(20.0)
//        cell.cellStyle = style
        // *****************
    }

    private fun makeLocationsTab(workbook: Workbook) {
        val locations = workbook.createSheet("Locations")
    }

    private fun makeProductsTab(workbook: Workbook) {
        val products = workbook.createSheet("Products")
    }

    fun makeSortableSpreadsheet() {
        val workbook: Workbook = XSSFWorkbook()

        makeReceivingTab(workbook)
        makeLocationsTab(workbook)
        makeProductsTab(workbook)

        val currDir: File = File(".")
        val path: String = currDir.getAbsolutePath()
        val fileLocation = path.substring(0, path.length - 1) + "temp.xlsx"

        val outputStream = FileOutputStream(fileLocation)
//        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()
    }

    fun makeDownloadSpreadsheet(): ByteArrayResource {
        val workbook: Workbook = XSSFWorkbook()
        makeReceivingTab(workbook)
        makeLocationsTab(workbook)
        makeProductsTab(workbook)

        val currDir: File = File(".")
        val path: String = currDir.getAbsolutePath()
        val fileLocation = path.substring(0, path.length - 1) + "temp.xlsx"

//        val outputStream = FileOutputStream(fileLocation)
        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()
        return ByteArrayResource(outputStream.toByteArray())
    }
}
