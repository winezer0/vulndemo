import kotlin.arrayOf
import kotlin.intArrayOf

// factory_integer consumes an exact primitive-array factory contract.
fun factory_integer(factory_integerInput: Int): Int { val holder = intArrayOf(factory_integerInput, 0); val factory_integerOut = holder[0]; return factory_integerOut }
// factory_isolation separates two invocations of the same catalog factory.
fun factory_isolation(factory_isolationInput: String): String { arrayOf(factory_isolationInput); val holder = arrayOf("safe"); val factory_isolationOut = holder[0]; return factory_isolationOut }

// factory_same consumes an actual standard-library factory argument.
fun factory_same(factory_sameInput: String): String { val holder = arrayOf(factory_sameInput, "safe"); val factory_sameOut = holder[0]; return factory_sameOut }
// factory_uncertain retains a candidate when the indexed protocol is not confirmed.
fun factory_uncertain(factory_uncertainInput: String): String { val holder = arrayOf(factory_uncertainInput, "safe"); val factory_uncertainOut = holder[1]; return factory_uncertainOut }
// factory_shadow uses a local callable whose spelling cannot grant a catalog contract.
fun factory_shadow(factory_shadowInput: String): String { val arrayOf: (String) -> Array<String> = { ignored -> kotlin.arrayOf("safe") }; val holder = arrayOf(factory_shadowInput); val factory_shadowOut = holder[0]; return factory_shadowOut }
