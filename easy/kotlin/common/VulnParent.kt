package common

open class VulnParent {
    fun exec(cmd: String) {
        Runtime.getRuntime().exec(cmd)
    }
}
