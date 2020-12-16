import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
fun main() {
    var countL1 = 0

    var countL2 = 0
    val timeForTheProgram = measureTime {
        runBlocking {
            val l1: Job = launch {
                println("Launch 1 starting....")
                delay(2000L)
                println("Launch1 2 seconds done....")
                delay(1000L)
                println("Launch1 3 seconds done....")
                delay(1000L)
                println("Launch1 4 seconds done....")
                delay(1000L)
                println("Launch 1 done")
                countL1++
            }
            val l2 = launch {
                println("Launch 2 starting....")
                delay(3000L)
                println("Launch 2 done")
                countL2++
            }

            // this will basically suspend this current coroutine scope
            // but note: The two launches are still running in parallel....
            // which means as soon as we say join the flow will only conttinue to next line when this l2 has finished
            // l2 takes 3 seconds to finish. which means l1 still has 2 more seconds to finish
            // , which is why the countL1 has not updated.
            // this join would not block any children coroutines or whatever is running there.
            // It basically just stops this "runblocking" coroutine scope flow to process the next lines
            // till this launch gets finished.
            l2.join().also { println("l2jng")  }

            printCounter(countL1, countL2) // 0 1

            l1.join().also { println("l1jng") }

            printCounter(countL1, countL2) // 11
        }
    }

    println("tot time taken $timeForTheProgram")
}

fun printCounter(count: Int, countt2: Int) {
  println("counting")

    println("$count $countt2")

    println("done counting")
}

