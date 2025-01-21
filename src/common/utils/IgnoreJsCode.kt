package common.utils

@Target(
    AnnotationTarget.CLASS, AnnotationTarget.FUNCTION
)
@Retention(AnnotationRetention.SOURCE)
@ExperimentalStdlibApi
annotation class IgnoreJsCode()