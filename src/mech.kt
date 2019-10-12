data class CritTableEntry(val name: String, val entries: Int) {
    constructor(name: String) : this(name, 1)
}

fun printCritTableEntry(item: CritTableEntry) {
    when (item.entries) {
        1 -> println("    [ ${item.name}")
        2 -> {
            println("    ⌈ ${item.name}")
            println("    ⌊ ${item.name}")
        }
        else -> {
            println("    ⌈ ${item.name}")
            for (x in 2..(item.entries - 1)) {
                println("    | ${item.name}")
            }
            println("    ⌊ ${item.name}")
        }
    }
}

// A class to allow us to add standard things that might live in any part of a 'mech.
open class LocationFactory() {
    protected var critEntries = mutableListOf<CritTableEntry>()

    var structureValue: Int? = null
        private set
    fun structure(value: Int) {
        structureValue = value
    }

    fun srm6() {
        critEntries.add(CritTableEntry("SRM 6", 2))
    }

    fun srm6Ammo() {
        critEntries.add(CritTableEntry("Ammo (SRM 6)", 1))
    }

    fun srm4() {
        critEntries.add(CritTableEntry("SRM 4", 1))
    }

    fun srm4Ammo() {
        critEntries.add(CritTableEntry("Ammo (SRM 4)", 1))
    }

    fun mediumLaser() {
        critEntries.add(CritTableEntry("Medium Laser", 1))
    }

    fun heatSink() {
        critEntries.add(CritTableEntry("Heat Sink", 1))
    }

    fun printEquipment() {
        for (item in critEntries) {
            printCritTableEntry(item)
        }
    }
}

open class TorsoLocationFactory() : LocationFactory() {
    var frontArmorValue: Int? = null
        private set
    fun frontArmor(value: Int) {
        frontArmorValue = value
    }
    
    var rearArmorValue: Int? = null
        private set
    fun rearArmor(value: Int) {
        rearArmorValue = value
    }
    
    fun printArmorStructure() {
        println("    front armor: $frontArmorValue")
        println("    rear armor: $rearArmorValue")
        println("    structure: $structureValue")
    }
}

open class NonTorsoLocationFactory() : LocationFactory() {
    var armorValue: Int? = null
        private set
    fun armor(value: Int) {
        armorValue = value
    }

    fun printArmorStructure() {
        println("    armor: $armorValue")
        println("    structure: $structureValue")
    }
}

class SideTorsoFactory() : TorsoLocationFactory() {
    fun print() {
        printArmorStructure()
        println()
        printEquipment()
    }
}

class CenterTorsoFactory() : TorsoLocationFactory() {
    fun print() {
        printArmorStructure()
        println()
        printCritTableEntry(CritTableEntry("engine", 3))
        printCritTableEntry(CritTableEntry("gyro", 4))
        printCritTableEntry(CritTableEntry("engine", 3))
        printEquipment()
    }
}

enum class LowerArmActuators {
    NONE, LOWER, LOWERANDHAND
}

class ArmFactory() : NonTorsoLocationFactory() {
    var actuatorsValue: LowerArmActuators? = null
        private set
    fun actuators(value: LowerArmActuators) {
        actuatorsValue = value
    }

    fun print() {
        printArmorStructure()
        println()
        printCritTableEntry(CritTableEntry("Shoulder"))
        printCritTableEntry(CritTableEntry("Upper Arm Actuator"))
        when (actuatorsValue) {
            LowerArmActuators.NONE -> {}
            LowerArmActuators.LOWER -> printCritTableEntry(CritTableEntry(("Lower Arm Actuator")))
            LowerArmActuators.LOWERANDHAND -> {
                printCritTableEntry(CritTableEntry(("Lower Arm Actuator")))
                printCritTableEntry(CritTableEntry(("Hand Actuator")))
            }
        }
        printEquipment()
    }
}

class LegFactory() : NonTorsoLocationFactory() {
    fun print() {
        printArmorStructure()
        println()
        printCritTableEntry(CritTableEntry("Hip"))
        printCritTableEntry(CritTableEntry("Upper Leg Actuator"))
        printCritTableEntry(CritTableEntry("Lower Leg Actuator"))
        printCritTableEntry(CritTableEntry("Foot Actuator"))
        printEquipment()
    }
}

class HeadFactory() : NonTorsoLocationFactory() {
    init {
        structure(3)
    }

    fun print() {
        printArmorStructure()
        println()
        printCritTableEntry(CritTableEntry("Life Support"))
        printCritTableEntry(CritTableEntry("Sensors"))
        printCritTableEntry(CritTableEntry("Cockpit"))
        printEquipment()
        printCritTableEntry(CritTableEntry("Sensors"))
        printCritTableEntry(CritTableEntry("Life Support"))
    }
}

class MechBuilder() {
    var nameValue: String? = null
        private set
    fun name(value: String) {
        nameValue = value
    }

    var designationValue: String? = null
        private set
    fun designation(value: String) {
        designationValue = value
    }

    var weightValue: Int? = null
        private set
    fun weight(value: Int) {
        weightValue = value
    }

    var walkValue: Int? = null
        private set
    fun walk(value: Int) {
        walkValue = value
    }

    var runValue: Int? = null
        private set
    fun run(value: Int) {
        runValue = value
    }

    var jumpValue: Int? = null
        private set
    fun jump(value: Int) {
        jumpValue = value
    }

    var battleValueValue: Int? = null
        private set
    fun battleValue(value: Int) {
        battleValueValue = value
    }
    
    private var headFactory: HeadFactory? = null
    fun head(builder: HeadFactory.() -> Unit) {
        headFactory = HeadFactory()
        headFactory?.builder()
    }

    private var leftArmFactory: ArmFactory? = null
    fun leftArm(builder: ArmFactory.() -> Unit) {
        leftArmFactory = ArmFactory()
        leftArmFactory?.builder()
    }

    private var leftLegFactory: LegFactory? = null
    fun leftLeg(builder: LegFactory.() -> Unit) {
        leftLegFactory = LegFactory()
        leftLegFactory?.builder()
    }

    private var leftTorsoFactory: SideTorsoFactory? = null
    fun leftTorso(builder: SideTorsoFactory.() -> Unit) {
        leftTorsoFactory = SideTorsoFactory()
        leftTorsoFactory?.builder()
    }

    private var centerTorsoFactory: CenterTorsoFactory? = null
    fun centerTorso(builder: CenterTorsoFactory.() -> Unit) {
        centerTorsoFactory = CenterTorsoFactory()
        centerTorsoFactory?.builder()
    }

    private var rightArmFactory: ArmFactory? = null
    fun rightArm(builder: ArmFactory.() -> Unit) {
        rightArmFactory = ArmFactory()
        rightArmFactory?.builder()
    }

    private var rightLegFactory: LegFactory? = null
    fun rightLeg(builder: LegFactory.() -> Unit) {
        rightLegFactory = LegFactory()
        rightLegFactory?.builder()
    }

    private var rightTorsoFactory: SideTorsoFactory? = null
    fun rightTorso(builder: SideTorsoFactory.() -> Unit) {
        rightTorsoFactory = SideTorsoFactory()
        rightTorsoFactory?.builder()
    }

    fun print() {
        println("  name: $nameValue")
        println("  designation: $designationValue")
        println("  weight (tons): $weightValue")
        println("  walk: $walkValue")
        println("  run: $runValue")
        println("  jump: $jumpValue")
        println("  BV: $battleValueValue")
        println()
        println("  head <")
        headFactory?.print()
        println("  >")

        println("  left arm <")
        leftArmFactory?.print()
        println("  >")

        println("  left torso <")
        leftTorsoFactory?.print()
        println("  >")

        println("  left leg <")
        leftLegFactory?.print()
        println("  >")

        println("  center torso <")
        centerTorsoFactory?.print()
        println("  >")

        println("  right arm <")
        rightArmFactory?.print()
        println("  >")

        println("  right torso <")
        rightTorsoFactory?.print()
        println("  >")

        println("  right leg <")
        rightLegFactory?.print()
        println("  >")

    }
}

class MechList() {
    val list = mutableListOf<MechBuilder>()

    operator fun invoke(builder: MechBuilder.() -> Unit) {
        val mb = MechBuilder()
        mb.builder()
        list.add(mb)
    }

    fun print() {
        for (mech in list) {
            println("mech <")
            mech.print()
            println(">")
        }
    }
}

fun main() {
    val mech = MechList()

    mech {
        name("Commando")
        designation("COM-2D")
        weight(25)
        walk(6)
        run(9)

        head {
            armor(6)
        }
        leftArm {
            structure(4)
            armor(6)
            actuators(LowerArmActuators.LOWERANDHAND)
            mediumLaser()
        }
        leftTorso {
            structure(6)
            frontArmor(6)
            rearArmor(3)
            heatSink()
            heatSink()
            srm6Ammo()
        }
        leftLeg {
            structure(6)
            armor(8)
        }
        centerTorso {
            structure(8)
            frontArmor(8)
            rearArmor(4)
            srm6()
        }
        rightArm {
            structure(4)
            armor(6)
            actuators(LowerArmActuators.LOWERANDHAND)
            srm4()
        }
        rightTorso {
            structure(6)
            frontArmor(6)
            rearArmor(3)
            heatSink()
            heatSink()
            srm4Ammo()
        }
        rightLeg {
            structure(6)
            armor(8)
        }
    }
    mech.print()

    println("Hello World!")
}