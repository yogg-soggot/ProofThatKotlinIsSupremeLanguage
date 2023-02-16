package switch_operator

import kotlin.reflect.KClass

inline fun <reified ARG, reified RESULT> switch(
    arg: ARG,
    expr: SwitchScope<ARG>.() -> RESULT,
): RESULT {
    val scope = SwitchScope(arg)
    return scope.expr()
}

class SwitchScope<ARG>(private val arg: ARG) {
    var caseTriggered = false
    var result: Any? = null

    fun <CASE_RESULT> case(
        caseVal: ARG,
        doInCase: (ARG) -> CASE_RESULT
    ) {
        if(arg == caseVal && !caseTriggered) {
            result = doInCase(arg)
            caseTriggered = true
        }
    }

    fun <SUBTYPE: Any, CASE_RESULT> case(
        caseVal: KClass<SUBTYPE>,
        doInCase: (SUBTYPE) -> CASE_RESULT
    ) {
        if (caseVal.isInstance(arg) && !caseTriggered) {
            result = doInCase(arg as SUBTYPE) as Any
            caseTriggered = true
        }
    }

    inline fun <RESULT> default(func: () -> RESULT): RESULT {
        return if(!caseTriggered) {
            func()
        } else result as RESULT
    }
}
