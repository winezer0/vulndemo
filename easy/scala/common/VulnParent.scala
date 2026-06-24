package common

import scala.sys.process._

class VulnParent {
  def exec(cmd: String): Unit = {
    cmd.!
  }
}
