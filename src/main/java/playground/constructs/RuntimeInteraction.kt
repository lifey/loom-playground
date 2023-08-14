package playground.constructs

object RuntimeInteraction {
  private val currentPID = ProcessHandle.current().pid()
  private val javaHome = System.getenv("JAVA_HOME")
  public enum class NMTCommands(val command: String) {
    SUMMARY("summary"),
    DETAIL("detail"),
    BASELINE("baseline"),
    DETAIL_DIFF("detail.diff"),
    SUMMARY_DIFF("summary.diff")

  }
  public fun getProcessPID(): Long {
    return return currentPID
  }
  public fun rumNMT(cmd: NMTCommands) {
    val pid = getProcessPID()

    val processBuilder = ProcessBuilder(
      "$javaHome/bin/jcmd",
      "$pid",
      "VM.native_memory",
      cmd.command,
      "scale=MB"
    )
    val process = processBuilder.start()
    val input = String(process.inputStream.readAllBytes())
    val error = String(process.errorStream.readAllBytes())
    println(input)
    println(error)
  }
}
