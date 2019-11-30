package com.github.lcdsmao.springx

interface UiTestScope {

  interface Runner {
    fun runUiTest(block: () -> Unit)
  }

  fun runOnMainSync(block: () -> Unit)
  fun waitForIdleSync()
}

fun runUiTest(uiTestScope: UiTestScope.() -> Unit) {
  val (scope, runner) = getUiTestDelegate()
  runner.runUiTest { uiTestScope(scope) }
}

private fun getUiTestDelegate(): Pair<UiTestScope, UiTestScope.Runner> {
  val testScopeName =
    if (System.getProperty("java.runtime.name")!!.toLowerCase().contains("android")) {
      "com.github.lcdsmao.springx.InstrumentationUiTestScope"
    } else {
      "com.github.lcdsmao.springx.UnitUiTestScope"
    }
  val runnerName = "$testScopeName\$Runner"
  return Class.forName(testScopeName).newInstance() as UiTestScope to
    Class.forName(runnerName).newInstance() as UiTestScope.Runner
}
