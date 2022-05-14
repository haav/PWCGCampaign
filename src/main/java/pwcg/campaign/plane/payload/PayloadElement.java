package pwcg.campaign.plane.payload;

public enum PayloadElement 
{
	//WW I
    LB20_X1(PayloadElementCategory.ORDNANCE_BOMB, "20 lb Bomb x1", 10),
    LB20_X2(PayloadElementCategory.ORDNANCE_BOMB, "20 lb Bomb x2", 18),
    LB20_X4(PayloadElementCategory.ORDNANCE_BOMB, "20 lb Bomb x4", 35),
    LB20_X8(PayloadElementCategory.ORDNANCE_BOMB, "20 lb Bomb x8", 150),
    LB20_X12(PayloadElementCategory.ORDNANCE_BOMB, "20 lb Bomb x12", 110),
    LB20_X16(PayloadElementCategory.ORDNANCE_BOMB, "20 lb Bomb x16", 150),
    LB40_X8(PayloadElementCategory.ORDNANCE_BOMB, "40 lb Bomb x8", 150),
    LB65_X2(PayloadElementCategory.ORDNANCE_BOMB, "65 lb Bomb x2", 60),
    LB65_X4(PayloadElementCategory.ORDNANCE_BOMB, "65 lb Bomb x4", 120),
    LB112_X1(PayloadElementCategory.ORDNANCE_BOMB, "112 lb Bomb x1", 50),
    LB112_X2(PayloadElementCategory.ORDNANCE_BOMB, "112 lb Bomb x2", 110),
    LB112_X3(PayloadElementCategory.ORDNANCE_BOMB, "112 lb Bomb x3", 180),
    LB112_X4(PayloadElementCategory.ORDNANCE_BOMB, "112 lb Bomb x4", 220),
    LB112_X8(PayloadElementCategory.ORDNANCE_BOMB, "112 lb Bomb x8", 440),
    LB112_X16(PayloadElementCategory.ORDNANCE_BOMB, "112 lb Bomb x16", 880),
    LB230_X1(PayloadElementCategory.ORDNANCE_BOMB, "230 lb Bomb x1", 115),
    LB230_X2(PayloadElementCategory.ORDNANCE_BOMB, "230 lb Bomb x2", 230),
    LB112x8(PayloadElementCategory.ORDNANCE_BOMB, "112 lb Bomb x8", 480),
    LB112x16(PayloadElementCategory.ORDNANCE_BOMB, "112 lb Bomb x16", 960),
    LB250x1(PayloadElementCategory.ORDNANCE_BOMB, "250 lb Bomb x1", 240),
    LB250x2(PayloadElementCategory.ORDNANCE_BOMB, "250 lb Bomb x2", 240),
    LB250x3(PayloadElementCategory.ORDNANCE_BOMB, "250 lb Bomb x3", 360),
    LB250x4(PayloadElementCategory.ORDNANCE_BOMB, "250 lb Bomb x4", 480),
    LB250x8(PayloadElementCategory.ORDNANCE_BOMB, "250 lb Bomb x8", 960),
    LB500x1(PayloadElementCategory.ORDNANCE_BOMB, "500 lb Bomb x1", 240),
    LB500x2(PayloadElementCategory.ORDNANCE_BOMB, "500 lb Bomb x2", 480),
    LB500x3(PayloadElementCategory.ORDNANCE_BOMB, "500 lb Bomb x3", 720),
    LB500x4(PayloadElementCategory.ORDNANCE_BOMB, "500 lb Bomb x4", 960),
    LB500x6(PayloadElementCategory.ORDNANCE_BOMB, "500 lb Bomb x6", 1440),
    LB1000x2(PayloadElementCategory.ORDNANCE_BOMB, "1000 lb Bomb x2", 1820),
    LB2000x2(PayloadElementCategory.ORDNANCE_BOMB, "2000 lb Bomb x2", 3640),
    M8X6(PayloadElementCategory.ORDNANCE_BOMB, "6x M8 Rocket", 240),
    MKI_HE_ROCKETS_X8(PayloadElementCategory.ORDNANCE_BOMB, "8x MK I HE Rocket", 240),
    MKI_AP_ROCKETS_X8(PayloadElementCategory.ORDNANCE_BOMB, "8x MK I AP Rocket", 240),
    MKI_ROCKETS_DOUBLE(PayloadElementCategory.ORDNANCE_BOMB, "MK I HE Rocket Double Payload", 580),
    MKIII_HE_ROCKETS_X8(PayloadElementCategory.ORDNANCE_BOMB, "8x MK III HE Rocket", 240),
    MKIII_AP_ROCKETS_X8(PayloadElementCategory.ORDNANCE_BOMB, "8x MK III AP Rocket", 240),
    P38_BOMBS_AND_ROCKETS(PayloadElementCategory.ORDNANCE_BOMB, "500 lb Bomb x2 and 6x M8 Rocket", 240),
    P47_BOMBS_AND_ROCKETS(PayloadElementCategory.ORDNANCE_BOMB, "500 lb Bomb x2 and 6x M8 Rocket", 240),
    LB1650x1(PayloadElementCategory.ORDNANCE_BOMB, "1650 lb Bomb x1`", 800),
    POOD_1_10X(PayloadElementCategory.ORDNANCE_BOMB, "10x1 pood 1", 100),
    POOD_2_10X(PayloadElementCategory.ORDNANCE_BOMB, "10x2 pood 2", 200),
    POOD_2_5X(PayloadElementCategory.ORDNANCE_BOMB, "2x5 pood 2", 100),
    KG8_X32(PayloadElementCategory.ORDNANCE_BOMB, "8 kg Bomb x32", 260),
    KG10_X4(PayloadElementCategory.ORDNANCE_BOMB, "10 kg Bomb x4", 40),
    KG10_X8(PayloadElementCategory.ORDNANCE_BOMB, "10 kg Bomb x8", 80),
    KG12_X4(PayloadElementCategory.ORDNANCE_BOMB, "12.5 kg Bomb x4", 50),
    KG12_X8(PayloadElementCategory.ORDNANCE_BOMB, "12.5 kg Bomb x8", 100),
    KG12_X10(PayloadElementCategory.ORDNANCE_BOMB, "12 kg Bomb x10", 120),
    KG12_X12(PayloadElementCategory.ORDNANCE_BOMB, "12.5 kg Bomb x12", 150),
    KG12_X16(PayloadElementCategory.ORDNANCE_BOMB, "12.5 kg Bomb x16", 200),
    KG12_X20(PayloadElementCategory.ORDNANCE_BOMB, "12 kg Bomb x20", 360),
    KG20_X16(PayloadElementCategory.ORDNANCE_BOMB, "20 kg Bomb x16", 360),
    KG40_X9(PayloadElementCategory.ORDNANCE_BOMB, "40 kg Bomb x8", 360),
    KG50x1(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x1", 50),
    KG50x3(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x3", 150),
    KG50x7(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x7", 350),
    KG100x7(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x7", 700),
    KG100x4(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x4", 400),
    KG250x2(PayloadElementCategory.ORDNANCE_BOMB, "250 kg Bomb x2", 500),
    KG300x1(PayloadElementCategory.ORDNANCE_BOMB, "300 kg Bomb x1", 300),
    KG500x1(PayloadElementCategory.ORDNANCE_BOMB, "500 kg Bomb x1", 300),
    LE_PRIEUR_ROCKETS(PayloadElementCategory.ORDNANCE_BOMB, "Le Prieur rockets", 20),
    P51_ROCKETS(PayloadElementCategory.ORDNANCE_BOMB, "P51 rockets", 80),
    INTERNAL_BOMBS(PayloadElementCategory.ORDNANCE_BOMB, "Internal Bombs", 60),
    BOMBS(PayloadElementCategory.ORDNANCE_BOMB, "Bombs", 60),
    COOPER_BOMBS(PayloadElementCategory.ORDNANCE_BOMB, "Cooper Bombs", 60),

    
    FW190A6_REM_GUNS(PayloadElementCategory.WEAPON, "Remove Outboard Guns", 0),
    FW190A6_STURMJAGER(PayloadElementCategory.WEAPON, "Sturm Jager", 500),
    FW190F8(PayloadElementCategory.ORDNANCE_BOMB, "FW190 F8", 100),
    FW190F8_SC70_X4(PayloadElementCategory.ORDNANCE_BOMB, "FW190 F8 70 KB Bomb x4", 100),
    FW190F8_PB1_X12(PayloadElementCategory.ORDNANCE_BOMB, "FW190 F8 PB1 x12", 100),
    FW190F8_PB1M8_X12(PayloadElementCategory.ORDNANCE_BOMB, "FW190 F8 PB1 M8 x12", 100),
    FW190G3(PayloadElementCategory.ORDNANCE_BOMB, "FW190 G3", 100),

    //WW II
    MALCOLM_CANOPY(PayloadElementCategory.MODIFICATION, "Malcolm Canopy", 0),
    ERLA_CANOPY(PayloadElementCategory.MODIFICATION, "Erla Canopy", 0),
    BUBBLE_CANOPY(PayloadElementCategory.MODIFICATION, "Bubble Canopy", 0),
    REMOVE_HEADREST(PayloadElementCategory.MODIFICATION, "Remove Headrest", 0),
    GLASS_HEADREST(PayloadElementCategory.MODIFICATION, "Glass Headrest", 0),
    ARMORED_HEADREST(PayloadElementCategory.MODIFICATION, "Armored Headrest", 0),
	ARMORED_WINDSCREEN(PayloadElementCategory.MODIFICATION, "Armored Windscreen", 10),
    WINDSCREEN(PayloadElementCategory.MODIFICATION, "Windscreen", 2),
    MIRROR(PayloadElementCategory.MODIFICATION, "Mirror", 0),
    GYRO_GUNSIGHT(PayloadElementCategory.MODIFICATION, "Gyro Gunsight", 0),
    REFLECTOR_GUNSIGHT(PayloadElementCategory.MODIFICATION, "Reflector Gunsight", 0),
    P47_GUNSIGHT(PayloadElementCategory.MODIFICATION, "P-47 Gunsight", 0),
    P51_GUNSIGHT(PayloadElementCategory.MODIFICATION, "P-51 Gunsight", 0),
    LANDING_LIGHTS(PayloadElementCategory.MODIFICATION, "Landing Lights", 0),
    SIREN(PayloadElementCategory.MODIFICATION, "Siren", 5),
    SPITFIRE_CLIPPED_WINGS(PayloadElementCategory.MODIFICATION, "Clipped Wings", 0),
    SPITFIRE_XIV_E_TYPE_WINGS(PayloadElementCategory.MODIFICATION, "E-Type Wings", 0),
    EXTRA_ARMOR(PayloadElementCategory.MODIFICATION, "Armor", 300),
    EXHAUST_SUPPRESSOR(PayloadElementCategory.MODIFICATION, "Exhaust Supressor", 100),
    REMOVE_ARMOR(PayloadElementCategory.MODIFICATION, "Remove Armor", 300),
    AUTO_VALVE(PayloadElementCategory.MODIFICATION, "Auto Valve", 0),
	TURRET(PayloadElementCategory.MODIFICATION, "Turret", 250),
    BELLY_TURRET(PayloadElementCategory.MODIFICATION, "Belly Turret", 250),
    NOSE_TURRET(PayloadElementCategory.MODIFICATION, "Nose Turret", 250),
    U2_GUNNER(PayloadElementCategory.MODIFICATION, "Rear Gunner", 100),
    FW190A8_REM_GUNS(PayloadElementCategory.MODIFICATION, "Remove Outboard Guns", 0),
    SET_MG17(PayloadElementCategory.MODIFICATION, "Add MG17", 0),
    U_17(PayloadElementCategory.MODIFICATION, "U/17", 300),
    PEILG6(PayloadElementCategory.MODIFICATION, "Peil G6", 0),
    FUG16_ZY(PayloadElementCategory.MODIFICATION, "FuG 16 ZY", 10),
    RPK10(PayloadElementCategory.MODIFICATION, "RPK 10", 100),
    M82F_ENGINE(PayloadElementCategory.MODIFICATION, "M82F Engine", 100),
    PACKARD_ENGINE(PayloadElementCategory.MODIFICATION, "Packard engine", 0),
    MERLIN_ENGINE(PayloadElementCategory.MODIFICATION, "Merlin engine", 100),
    MERLIN_70_ENGINE(PayloadElementCategory.MODIFICATION, "Merlin 70 Engine", 100),
    DB605DC_ENGINE(PayloadElementCategory.MODIFICATION, "DB605DC engine", 100),
    MN28(PayloadElementCategory.MODIFICATION, "MN28", 0),
    LA5_AMMO(PayloadElementCategory.MODIFICATION, "LA5 Ammo Scheme", 0),
    HE_AMMO(PayloadElementCategory.MODIFICATION, "High Explosive Ammo", 0),
    AP_AMMO(PayloadElementCategory.MODIFICATION, "Armor Piercing Ammo", 0),
    EXTRA_AMMO(PayloadElementCategory.MODIFICATION, "Extra Ammo", 0),
    
    ETC501(PayloadElementCategory.MODIFICATION, "ETC 501 Bomb Rack", 100),
    OCTANE_150_FUEL(PayloadElementCategory.MODIFICATION, "150 Octane Fuel", 100),
    LB_11_BOOST(PayloadElementCategory.MODIFICATION, "11 lb. Boost", 100),
    LB_14_BOOST(PayloadElementCategory.MODIFICATION, "14 lb. Boost", 100),
    MW50(PayloadElementCategory.MODIFICATION, "MW50", 0),
    PBP_1A(PayloadElementCategory.MODIFICATION, "PBP 1A", 100),
    AIR_FILTER(PayloadElementCategory.MODIFICATION, "Air Filter", 10),
    FOUR_PLADE_PROP(PayloadElementCategory.MODIFICATION, "Four Blade Propellor", 10),


    GUAGE_SET(PayloadElementCategory.MODIFICATION, "Guage Set", 0),
    AMMO_COUNTER(PayloadElementCategory.MODIFICATION, "Ammo Counter", 0),
    ALTITUDE_GUAGE(PayloadElementCategory.MODIFICATION, "Altitude Guage", 0),
    ATTITUDE_GUAGE(PayloadElementCategory.MODIFICATION, "Attitude Guage", 0),
    CLOCK_GUAGE(PayloadElementCategory.MODIFICATION, "Clock Guage", 0),
    COCKPIT_LIGHT(PayloadElementCategory.MODIFICATION, "Cockpit Light", 0),
    COMPASS_GUAGE(PayloadElementCategory.MODIFICATION, "Compass Guage", 0),
    FUEL_GUAGE(PayloadElementCategory.MODIFICATION, "Fuel Guage", 0),
    SIDE_SLIP_GUAGE(PayloadElementCategory.MODIFICATION, "Side Slip Guage", 0),
    SPEED_GUAGE(PayloadElementCategory.MODIFICATION, "Speed guage", 0),
    TEMPERATURE_GUAGE(PayloadElementCategory.MODIFICATION, "Temperature Guage", 0),
    IRON_SIGHT(PayloadElementCategory.MODIFICATION, "Iron SIght", 0),
    LE_CHRETIAN_SIGHT(PayloadElementCategory.MODIFICATION, "Le Chretian SIght", 0),
    ALDIS_SIGHT(PayloadElementCategory.MODIFICATION, "Aldis SIght", 0),
    DAY_SIGHT(PayloadElementCategory.MODIFICATION, "Day SIght", 0),
    NIGHT_SIGHT(PayloadElementCategory.MODIFICATION, "Night SIght", 0),
    WING_CUTOUT(PayloadElementCategory.MODIFICATION, "Wing Cutout", 0),
    DUST_DEFLECTOR(PayloadElementCategory.MODIFICATION, "Dust Deflector", 0),

    TWIN_FRONT(PayloadElementCategory.WEAPON, "Twin Front Guns", 50),
    LEWIS_TOP(PayloadElementCategory.WEAPON, "Lewis Gun Top", 50),
    LEWIS_WING(PayloadElementCategory.WEAPON, "Lewis Gun Wing", 50),
    DAVIS_GUN(PayloadElementCategory.WEAPON, "Davis Gun", 50),
    DAVIS_GUN_TOP(PayloadElementCategory.WEAPON, "Davis Gun Top", 50),
    BALLOON_GUN(PayloadElementCategory.WEAPON, "Balloon Gun", 50),
    BECKER_GUN_TURRET(PayloadElementCategory.WEAPON, "Becker Gun Turret", 50),
    TWIN_GUN_TURRET(PayloadElementCategory.WEAPON, "Twin Gun Turret", 50),

    EMPTY(PayloadElementCategory.CARGO, "EMpty", 0),
    PARATROOPERS(PayloadElementCategory.CARGO, "Paratroopers", 1000),
    PARADROP_CARGO(PayloadElementCategory.CARGO, "Cargo", 1000),
    CARGO(PayloadElementCategory.CARGO, "Cargo", 1000),
    RADIO(PayloadElementCategory.CARGO, "Radio", 20),
    CAMERA(PayloadElementCategory.CARGO, "Camera", 10),

	STANDARD(PayloadElementCategory.WEAPON, "Standard Payload", 0),
    LESS_AMMO(PayloadElementCategory.WEAPON, "Less Ammo", 0),
    ADDITIONAL_AMMO(PayloadElementCategory.WEAPON, "Additional Ammo", 200),
    REMOVE_INNER_GUNS(PayloadElementCategory.WEAPON, "Remove Inner Guns", 0),

    MAB_250(PayloadElementCategory.ORDNANCE_BOMB, "MAB 250", 1000),
	FAB50SV_X2(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x2", 100),
	FAB50SV_X4(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x4", 200),
	FAB50SV_X6(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x6", 300),
    FAB100M_X1(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x1", 100),
    FAB100M_X2(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x2", 200),
	FAB100M_X4(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x4", 400),
    FAB100M_X6(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x6", 600),
    FAB100M_X8(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x8", 800),
    FAB100M_X10(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x10", 1000),
    FAB100M_X16(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x16", 1600),
    FAB100M_X20(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x20", 2000),
	FAB250SV_X1(PayloadElementCategory.ORDNANCE_BOMB, "250 kg Bomb x1", 250),
	FAB250SV_X2(PayloadElementCategory.ORDNANCE_BOMB, "250 kg Bomb x2", 500),
	FAB250SV_X4(PayloadElementCategory.ORDNANCE_BOMB, "250 kg Bomb x4", 1000),
	FAB500M_X1(PayloadElementCategory.ORDNANCE_BOMB, "500 kg Bomb x1", 500),
    FAB500M_X2(PayloadElementCategory.ORDNANCE_BOMB, "500 kg Bomb x2", 1000),
    FAB_U2VS(PayloadElementCategory.ORDNANCE_BOMB, "U2 Bomb x2", 1000),

	ROS82_X2(PayloadElementCategory.ORDNANCE_ROCKET, "RoS 82mm Rocket x2", 12),
	ROS82_X4(PayloadElementCategory.ORDNANCE_ROCKET, "RoS 82mm Rocket x4", 24),
	ROS82_X6(PayloadElementCategory.ORDNANCE_ROCKET, "RoS 82mm Rocket x6", 36),
	ROS82_X8(PayloadElementCategory.ORDNANCE_ROCKET, "RoS 82mm Rocket x8", 48),
	ROFS132_X4(PayloadElementCategory.ORDNANCE_ROCKET, "RoFS 132mm Rocket x4", 92),
	ROFS132_X6(PayloadElementCategory.ORDNANCE_ROCKET, "RoFS 132mm Rocket x6", 138),
	ROFS132_X8(PayloadElementCategory.ORDNANCE_ROCKET, "RoFS 132mm Rocket x8", 184),
    ROS132_X10(PayloadElementCategory.ORDNANCE_ROCKET, "RoFS 132mm Rocket x10", 230),
    RP3_X2(PayloadElementCategory.ORDNANCE_ROCKET, "RP-3 Rocket x2", 230),
    BR21_X2(PayloadElementCategory.ORDNANCE_ROCKET, "Werfer-Granate 21 x2", 100),
    R4M_X26(PayloadElementCategory.ORDNANCE_ROCKET, "Rakete, 4Kilogramm Minenkopf", 200),

    BROWNING_303_X4(PayloadElementCategory.WEAPON, "Browning 303 X4", 0),
    HISPANO_MKII_X4(PayloadElementCategory.WEAPON, "20mm Hispano Mk.II X4", 50),
    VICKERS_S40_X2(PayloadElementCategory.WEAPON, "40mm Vickers S40 X2", 100),
    SHVAK_X2(PayloadElementCategory.WEAPON, "Shvak X2", 0),
    SHVAK_GUNPOD(PayloadElementCategory.WEAPON, "Shvak Gun Pod", 50),
    SHVAK_UPGRADE(PayloadElementCategory.WEAPON, "Shvak Upgrade", 50),
	BK_GUNPOD(PayloadElementCategory.WEAPON, "BK Gun Pod", 50),
    VYA23_APHE_GUNPOD(PayloadElementCategory.WEAPON, "VYA 23mm Autocannon (AP,HE)", 50),
    VYA23_AP_GUNPOD(PayloadElementCategory.WEAPON, "VYA 23mm Autocannon (AP)", 50),
	VYA23_HE_GUNPOD(PayloadElementCategory.WEAPON, "VYA 23mm Autocannon (HE)", 50),
    SH37_APHE_GUNPOD(PayloadElementCategory.WEAPON, "SH 37mm Autocannon (AP,HE)", 50),
    SH37_AP_GUNPOD(PayloadElementCategory.WEAPON, "SH 37mm Autocannon (AP)", 50),
	SH37_HE_GUNPOD(PayloadElementCategory.WEAPON, "SH 37mm Autocannon (HE)", 50),
	NS37_AP_GUNPOD(PayloadElementCategory.WEAPON, "NS 37mm Autocannon (AP)", 50),
	NS37_HE_GUNPOD(PayloadElementCategory.WEAPON, "NS 37mm Autocannon (HE)", 50),
	NS37_APHE_GUNPOD(PayloadElementCategory.WEAPON, "NS 37mm Autocannon (AP,HE)", 50),
    MG50CAL_4x(PayloadElementCategory.WEAPON, "4x .50 Cal MG", 0),
    MG50CAL_6x(PayloadElementCategory.WEAPON, "6x .50 Cal MG", 0),
    REM_M230(PayloadElementCategory.WEAPON, "REM M230", 50),
    P3937MM_AP(PayloadElementCategory.WEAPON, "37mm AP", 50),
    YAK937MM_AP_HE(PayloadElementCategory.WEAPON, "37mm AP/HE", 0),
    YAK937MM_AP(PayloadElementCategory.WEAPON, "37mm AP", 0),
    YAK937MM_HE(PayloadElementCategory.WEAPON, "37mm HE", 0),
    
    
    M64_X1(PayloadElementCategory.ORDNANCE_BOMB, "Mk 64 500 lb Bomb x1", 250),
    M64_X2(PayloadElementCategory.ORDNANCE_BOMB, "Mk 64 500 lb Bomb x2", 500),
    M64_X3(PayloadElementCategory.ORDNANCE_BOMB, "Mk 64 500 lb Bomb x3", 750),
    M65_X2(PayloadElementCategory.ORDNANCE_BOMB, "Mk 65 1000 lb Bomb x2", 1000),
    M8_X6(PayloadElementCategory.ORDNANCE_ROCKET, "Mk 8 Rocket x6", 600),

	SC50_X2(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x2", 100),
	SC50_X4(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x4", 200),
	SC50_X6(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x6", 300),
	SC50_X8(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x8", 400),
	SC50_X12(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x12", 600),
	SC50_X16(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x16", 800),
	SC50_X18(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x18", 900),
	SC50_X28(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x28", 1400),
	SC50_X32(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x32", 1600),
	SC50_X44(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x44", 2200),
    SC70_X4(PayloadElementCategory.ORDNANCE_BOMB, "70 kg Bomb x4", 280),
	SC250_X1(PayloadElementCategory.ORDNANCE_BOMB, "250 kg Bomb x1", 250),
	SC250_X2(PayloadElementCategory.ORDNANCE_BOMB, "250 kg Bomb x2", 500),
	SC250_X3(PayloadElementCategory.ORDNANCE_BOMB, "250 kg Bomb x3", 750),
	SC250_X4(PayloadElementCategory.ORDNANCE_BOMB, "250 kg Bomb x4", 1000),
	SC250_X6(PayloadElementCategory.ORDNANCE_BOMB, "250 kg Bomb x6", 1500),
	SC250_X8(PayloadElementCategory.ORDNANCE_BOMB, "250 kg Bomb x8", 2000),
	SC500_X1(PayloadElementCategory.ORDNANCE_BOMB, "500 kg Bomb x1", 500),
	SC500_X2(PayloadElementCategory.ORDNANCE_BOMB, "500 kg Bomb x2", 1000),
	SC500_X4(PayloadElementCategory.ORDNANCE_BOMB, "500 kg Bomb x4", 2000),
	SC1000_X1(PayloadElementCategory.ORDNANCE_BOMB, "1000 kg Bomb x1", 1000),
	SC1000_X2(PayloadElementCategory.ORDNANCE_BOMB, "1000 kg Bomb x2", 2000),
	SC1800_X1(PayloadElementCategory.ORDNANCE_BOMB, "1800 kg Bomb x1", 1800),
	SC1800_X2(PayloadElementCategory.ORDNANCE_BOMB, "1800 kg Bomb x2", 3600),
	SC2500_X1(PayloadElementCategory.ORDNANCE_BOMB, "2500 kg Bomb x1", 2500),
	SD70_X4(PayloadElementCategory.ORDNANCE_BOMB, "70 kg Bomb x4", 280),
	T50_X2(PayloadElementCategory.ORDNANCE_BOMB, "50 kg Bomb x2", 100),
	T100_X2(PayloadElementCategory.ORDNANCE_BOMB, "100 kg Bomb x2", 200),

	MG151_15_GUNPOD(PayloadElementCategory.WEAPON, "MG 151 15mm Gun Pod", 50),
    MG151_20_GUNPOD(PayloadElementCategory.WEAPON, "MG 151 20mm Gun Pod", 50),
    MG17_GUNPOD(PayloadElementCategory.WEAPON, "MG 17 4x Gun Pod", 50),
    MG151_20_UPGRADE(PayloadElementCategory.WEAPON, "MG151 20mm Upgrade", 50),
    MG151_20_PODS(PayloadElementCategory.WEAPON, "MG151 20mm Gun Pods", 50),
    MGFF_WING_GUNS(PayloadElementCategory.WEAPON, "MG FF Wing Guns", 0),
    MGFF_WING_GUNS_EXTRA_AMMO(PayloadElementCategory.WEAPON, "MG FF Wing Guns Extra Ammo", 0),
	MK101_30_AP_GUNPOD(PayloadElementCategory.WEAPON, "MK 101 30mm Gun Pod (AP)", 50),
	MK101_30_HE_GUNPOD(PayloadElementCategory.WEAPON, "MK 101 30mm Gun Pod (HE)", 50),
	MK103_30_AP_GUNPOD(PayloadElementCategory.WEAPON, "MK 103 30mm Gun Pod (AP)", 50),
	MK103_30_HE_GUNPOD(PayloadElementCategory.WEAPON, "MK 103 30mm Gun Pod (HE)", 50),
    MK108_30(PayloadElementCategory.WEAPON, "MK 108 30mm", 50),
	BK37_HE_GUNPOD(PayloadElementCategory.WEAPON, "37mm Gun Pod (HE)", 100),
	BK37_AP_GUNPOD(PayloadElementCategory.WEAPON, "37mm Gun Pod (AP)", 100),
    BREDA_GUNPOD(PayloadElementCategory.WEAPON, "Breda Gun Pod", 50);

    	
	private String description;
    private int weight;
	private PayloadElementCategory category;
	 
	PayloadElement(PayloadElementCategory category, String description, int weight)
	{
        this.category = category;
        this.description = description;
		this.weight = weight;		
	}

	public String getDescription() 
	{
		return description;
	}

	public int getWeight() 
	{
		return weight;
	}

	public PayloadElementCategory getCategory() 
	{
		return category;
	}
}
