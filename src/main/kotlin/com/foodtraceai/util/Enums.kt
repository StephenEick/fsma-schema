// ----------------------------------------------------------------------------
// Copyright 2024 FoodTraceAI LLC or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.foodtraceai.util

// -- ISO-3166 3-Letter Country codes
enum class Country {
    USA,
    CANADA,
    MEX,
    OTHER // TODO: Fill in later
}

enum class CteType {
    // For the CTEs that we create
    Cool,
    Harvest,
    InitPackExempt,
    InitPackProduce,
    InitPackSprouts,
    FirstLandReceive,
    Ship,
    Receive,
    Transform,

    // For the CTEs coming from suppliers
    SupShip,    // Supplier shipped to one of our customer locations
}

enum class FoodBusType(val myCteTypes: List<CteType>) {
    Farm(listOf(CteType.Harvest)),
    Cooler(listOf(CteType.Cool)),
    Packer(listOf(CteType.InitPackProduce, CteType.Ship)),
    Distributor(listOf(CteType.Receive, CteType.Ship)),
    RFE(listOf(CteType.Receive, CteType.Transform, CteType.Ship)),  // grocery, convenience, club stores, etc.
    Processor(listOf(CteType.Receive, CteType.Transform, CteType.Ship)),
    Restaurant(listOf(CteType.Receive, CteType.Transform, CteType.Ship)),
    Other(emptyList()), // For administrative accounts
}

enum class FtlItem {
    Bivalves,
    Cheeses,
    Cucumbers,
    DeliSalads,
    Finfish,
    Fruits,
    Herbs,
    LeafyGreens,
    Melons,
    MolluscanShellfish,
    NutButters,
    Peppers,
    ShellEggs,
    SmokedFinfish,
    Sprouts,
    Tomatoes,
    TropicalTreeFruits,
    Vegetables,
}

enum class ReferenceDocumentType {
    ASN,
    BOL,
    CTE,
    PO,
    WO,
    OTHER, // TODO: fill in later
}

// SGE: The order of the Roles is important - highest permissions last
enum class Role {
    Mobile,
    FoodBusinessUser,
    FoodBusinessAdmin,
    FranchisorAdmin, // GPS Tracking
    RootAdmin, // FoodTrace204
}

fun maxRole(roles: List<Role>) = roles.maxBy { it.ordinal }

// The quantity and unit of measure of the food (e.g., 75 bins, 200 pounds);
enum class UnitOfMeasure {
    Bin,
    Carton,
    Case,
    FieldBin,
    Kilo,
    Pound,
}

enum class UsaCanadaState(val stateName: String) {
    AL("Alabama"),
    AK("Alaska"),
    AR("Arkansas"),
    AZ("Arizona"),
    CA("California"),
    CO("Colorado"),
    CT("Connecticut"),
    DE("Delaware"),
    FL("Florida"),
    GA("Georgia"),
    HI("Hawaii"),
    IA("Iowa"),
    ID("Idaho"),
    IL("Illinois"),
    IN("Indiana"),
    KS("Kansas"),
    KY("Kentucky"),
    LA("Louisiana"),
    MA("Massachusetts"),
    MD("Maryland"),
    ME("Maine"),
    MI("Michigan"),
    MN("Minnesota"),
    MO("Missouri"),
    MS("Mississippi"),
    MT("Montana"),
    NC("NorthCarolina"),
    ND("NorthDakota"),
    NE("Nebraska"),
    NH("NewHampshire"),
    NJ("NewJersey"),
    NM("NewMexico"),
    NV("Nevada"),
    NY("NewYork"),
    OH("Ohio"),
    OK("Oklahoma"),
    OR("Oregon"),
    PA("Pennsylvania"),
    RI("RhodeIsland"),
    SC("SouthCarolina"),
    SD("SouthDakota"),
    TN("Tennessee"),
    TX("Texas"),
    UT("Utah"),
    VA("Virginia"),
    VT("Vermont"),
    WA("Washington"),
    WI("Wisconsin"),
    WV("WestVirginia"),
    WY("Wyoming"),

    // ****** Canada *******
    AB("Alberta"),
    BC("British Columbia"),
    MB("Manitoba"),
    NB("New Brunswick"),
    NL("Newfoundland and Labrador"),
    NS("Nova Scotia"),
    NT("Northwest Territories"),
    NU("Nunavut"),
    ON("Ontario"),
    PE("Prince Edward Island"),
    QC("Quebec"),
    SK("Saskatchewan"),
    YT("Yukon"),
}

inline fun <reified T : Enum<*>> enumValueOrNull(name: String?): T? =
    if (name != null) T::class.java.enumConstants.firstOrNull { it.name == name }
    else null

