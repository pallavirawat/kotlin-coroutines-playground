import kotlinx.coroutines.*
import java.lang.RuntimeException
import kotlin.system.measureTimeMillis


//fun main() =
//    runBlocking {
//    // runblcoking will wait for all the children coroutines to finish
//        val launch = launch { // launch a new coroutine in background and continue
//        delay(5000L)
//        println("World!")
//    }
//    println("i am at the end")
//}

//fun main() {
//    try {
//        runBlocking {
//        // runblcoking will wait for all the children coroutines to finish
//        val launch1 = launch { // launch a new coroutine in background and continue
//
////            try{
//                println("launch1 started!")
//                delay(5000L)
//                println("launch1 finished!")
////            }
////            catch (e: Exception){
////                println("got an exception launch 1: ${e.localizedMessage} supressed: ${e.suppressed.toString()}")
////            }
//
//        }
//
//        val launch2 = launch { // launch a new coroutine in background and continue
//            println("launch2 started!")
////            try {
//                delay(2000L)
//                println("launch2 finished!")
//                throw RuntimeException("launch 2 failed. Failed. failed.")
////            }
////            catch (e: Exception){
////                println("got an exception launch 2: ${e.localizedMessage} supressed: ${e.suppressed.toString()}")
////            }
//
//        }
////        delay(1000L)
////        launch2.cancel()
//        println("i am at the end")
//    }}
//    catch (e: Throwable){
//        // even if i am not catching the cancelled exception i am still getting the exception i threw
//        println("got an exception: ${e.localizedMessage} cause: ${e.cause} ${e.suppressed.toString()}")
//    }
//}

//
//fun main() {
//    try {
//        runBlocking {
//            // runblcoking will wait for all the children coroutines to finish
//            val launch1 = launch { // launch a new coroutine in background and continue
//
////            try{
//                println("launch1 started!")
//                delay(1000L)
//                println("launch1 finished!")
////            }
////            catch (e: Exception){
////                println("got an exception launch 1: ${e.localizedMessage} supressed: ${e.suppressed.toString()}")
////            }
//
//            }
//
//            runBlocking {
//                val launch2 = launch { // launch a new coroutine in background and continue
//                    println("launch2 started!")
//                    delay(2000L)
//                    println("launch2 finished!")
//                    throw RuntimeException("launch 2 failed. Failed. failed.")
//                }
//            }
//
////        delay(1000L)
////        launch2.cancel()
//            println("i am at the end")
//        }}
//    catch (e: Throwable){
//        // even if i am not catching the cancelled exception i am still getting the exception i threw
//        println("got an exception: ${e.localizedMessage} cause: ${e.cause} ${e.suppressed.toString()}")
//    }
//}


fun main() {
    try {
        runBlocking {
            try {
                registerFake()
            }
            catch (e: Exception){
                println("got an exception in main: ${e.localizedMessage} cause: ${e.cause} ${e.suppressed.toString()}")
            }
        }}
    catch (e: Throwable){
        // even if i am not catching the cancelled exception i am still getting the exception i threw
        println("got an exception before ending: ${e.localizedMessage} cause: ${e.cause} ${e.suppressed.toString()}")
    }
}

suspend fun registerFake(): String{
    return handleResponseFake(someLaunch(2000L, "mainlauncher"),
    handleSuccess = { someLaunchWithException(it)},
    handleFailure = { someLaunch(it, "failure")}
    )
}

suspend fun handleResponseFake(response: String,
                               handleSuccess: suspend (l: Long) -> String,
                               handleFailure: suspend (l: Long) -> String
): String{
    return try {
        handleSuccess(2000L)
        "Success"
//        throw Exception("WHopsie Hybris is down!")
    }
    catch (e: Exception) {
        println("got an exception in handler: ${e.localizedMessage} cause: ${e.cause} ${e.suppressed.toString()}")
        handleFailure(5000L)
        "Exception"
    }
}
//
//private fun handleResponse(
//    response: HttpResponse,
//    handleSuccess: (response: HttpResponse) -> Pair<HttpStatusCode,Any>,
//    handleFailure: (response: HttpResponse) -> Unit = {},
//    handleException: (response: HttpResponse) -> Unit = {}
//)
suspend fun someLaunch(l: Long, name: String = "da"): String {
    println("launching for $name")
    delay(l)
    println("launching done for $name")
    return "done"
}

suspend fun someLaunchWithException(l: Long): String {
   withTimeout(1000L){
       someLaunch(4000L, "exceptionLauncher")
   }
    return "done"
}
//
//suspend fun register(userDetails: UserDetails, locale: String): Pair<HttpStatusCode, Any> {
//    return handleResponse(oktaUsersApi.createUser(userDetails),
//        handleSuccess = { runBlocking { createUserInHybris(it, userDetails,locale) } },
//        handleException = { runBlocking { deleteUserFromOkta(it) } }
//    )
//}
