fun main() {
    kind {
        template {
            name = "Commando"
            weight = 25
            walk = 6
            run = 9
        }

        model {
            designation = "COM-2D"

            head {
                armor = 6
            }

            armTemplate {
                structure = 6
                armor = 4
                actuators = LowerArmActuators.LOWERANDHAND
            }
            leftArm {
                mediumLaser {}
            }
            rightArm {
                srm4 {}
            }

            sideTorsoTemplate {
                structure = 6
                frontArmor = 6
                rearArmor = 3
            }
            leftTorso {
                heatSink {
                    count = 2
                }
                srm6Ammo {}
            }
            rightTorso {
                heatSink {
                    count = 2
                }
                srm4Ammo {}
            }

            legTemplate {
                structure = 6
                armor = 8
            }
        }
    }
}