import timber.log.Timber

class TimberLogger: Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "(${element.fileName}:${element.lineNumber}) on ${element.methodName}"
    }
}