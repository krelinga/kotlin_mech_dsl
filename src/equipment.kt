fun main () {
    weapon {
        name = "Medium Laser"
        techBase = TB.IS
        weight = 1
        damage = DirectDamage(5)
        heat = 5
        slots = 1
    }

    weaponFamily {
        base {
            name= "LRM "
            techBase = TB.BOTH

        }
    }
}