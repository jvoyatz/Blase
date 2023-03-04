import timber.log.Timber

class TimberLogger: Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        element
        return "(${element.fileName}:${element.lineNumber}) on ${element.methodName}"
    }
}