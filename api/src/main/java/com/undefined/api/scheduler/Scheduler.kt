package com.undefined.api.scheduler

import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

/**
 * Schedules a synchronous task that will be executed by the Bukkit server.
 *
 * @param runnable the code to be executed synchronously
 * @return the BukkitTask that represents the scheduled task
 */
fun sync(runnable: BukkitRunnable.() -> Unit): BukkitTask = createRunnable(runnable)
    .runTask(com.undefined.api.API.plugin)

/**
 * Executes the given [runnable] asynchronously on a separate thread.
 *
 * @param runnable the code to be executed asynchronously.
 * @return the [BukkitTask] associated with the scheduled task.
 */
fun async(runnable: BukkitRunnable.() -> Unit): BukkitTask = createRunnable(runnable)
    .runTaskAsynchronously(com.undefined.api.API.plugin)

/**
 * Delays the execution of a given runnable task for a specified number of ticks.
 *
 * @param ticks The number of ticks to delay the execution of the task.
 * @param unit The time unit to use for the delay (default is TimeUnit.TICKS).
 * @param async Determines whether the task should be executed asynchronously (default is false).
 * @param runnable The runnable task to be executed after the delay.
 * @return The BukkitTask representing the delayed task.
 */
fun delay(ticks: Int, unit: TimeUnit = TimeUnit.TICKS, async: Boolean = false, runnable: BukkitRunnable.() -> Unit): BukkitTask {
    return if (async) {
        createRunnable(runnable).runTaskLaterAsynchronously(com.undefined.api.API.plugin, unit.toTicks(ticks.toLong()))
    } else {
        createRunnable(runnable).runTaskLater(com.undefined.api.API.plugin, unit.toTicks(ticks.toLong()))
    }
}

/**
 * Delays the execution of the specified [runnable] by the specified number of [ticks].
 *
 * @param ticks The number of ticks to delay the execution by. Default is 1.
 * @param runnable The lambda function representing the code to be executed after the delay.
 * @return The scheduled BukkitTask that can be used to cancel the delayed execution if needed.
 */
fun delay(ticks: Int = 1, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    delay(ticks, TimeUnit.TICKS, false, runnable)

/**
 * Delays the execution of the specified function by the given number of ticks.
 *
 * @param ticks The number of ticks to delay the execution by (default is 1).
 * @param async Whether to execute the function asynchronously (default is false).
 * @param runnable The function to delay.
 * @return The Bukkit task representing the delayed execution.
 */
fun delay(ticks: Int = 1, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    delay(ticks, TimeUnit.TICKS, async, runnable)

/**
 * Schedules a repeating task with the given delay, period, and other optional parameters.
 *
 * @param delay The delay before the task starts, in the specified time unit.
 * @param period The time between each execution of the task, in the specified time unit.
 * @param times The number of times the task should repeat. Use -1 for indefinitely. Defaults to -1.
 * @param unit The time unit used for the delay and period. Defaults to TimeUnit.TICKS.
 * @param async Specifies whether to run the task asynchronously or synchronously. Defaults to false.
 * @param runnable The code to be executed by the task.
 *
 * @return The BukkitTask representing the scheduled repeating task.
 */
fun repeatingTask(delay: Int, period: Int, times: Int = -1, unit: TimeUnit = TimeUnit.TICKS, async: Boolean = false, runnable: BukkitRunnable.() -> Unit): BukkitTask {
    return if (async) {
        createRunnable(times, runnable).runTaskTimerAsynchronously(com.undefined.api.API.plugin, unit.toTicks(delay.toLong()), unit.toTicks(period.toLong()))
    } else {
        createRunnable(times, runnable).runTaskTimer(com.undefined.api.API.plugin, unit.toTicks(delay.toLong()), unit.toTicks(period.toLong()))
    }
}
/**
 * Executes a repeating task with the given ticks interval.
 *
 * @param ticks the number of ticks between each execution of the task (default is 1 tick)
 * @param runnable the code to be executed by the task
 * @return the BukkitTask representing the scheduled repeating task
 */
fun repeatingTask(ticks: Int = 1, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(0, ticks, -1, TimeUnit.TICKS, false, runnable)

/**
 * Schedules a repeating task with the specified options.
 *
 * @param ticks the delay before the task starts, in ticks (default is 1)
 * @param times the number of times the task should repeat (-1 for indefinitely, default is -1)
 * @param runnable the code to be executed repeatedly
 * @return the BukkitTask representing the scheduled repeating task
 */
fun repeatingTask(ticks: Int = 1, times: Int = -1, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(
        0,
        ticks,
        times,
        TimeUnit.TICKS,
        false,
        runnable
    )
/**
 * Schedules a repeating task to be executed with a specified delay between each execution.
 *
 * @param periodTicks The delay between each execution in ticks (default is 1).
 * @param async Specifies whether to run the task asynchronously or synchronously.
 * @param runnable The code to be executed in the task, defined as an extension function on [BukkitRunnable].
 * @return The scheduled BukkitTask instance representing the task.
 */
fun repeatingTask(periodTicks: Int = 1, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(
        periodTicks,
        periodTicks,
        -1,
        TimeUnit.TICKS,
        async,
        runnable
    )

/**
 * Executes a repeating task with the specified parameters.
 *
 * @param periodTicks The number of ticks between each execution.
 * @param async Whether the task should be executed asynchronously.
 * @param times The number of times the task should be executed. Use -1 for unlimited executions.
 * @param runnable The code to be executed by the task.
 *
 * @return The BukkitTask representing the scheduled repeating task.
 */
fun repeatingTask(periodTicks: Int = 1, async: Boolean, times: Int = -1, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(
        periodTicks,
        periodTicks,
        times,
        TimeUnit.TICKS,
        async,
        runnable
    )

/**
 * Schedules a repeating task with the specified period and unit.
 *
 * @param period the time between each execution of the task
 * @param unit the unit of time used for the period
 * @param runnable the code to be executed by the task
 * @return the BukkitTask representing the scheduled task
 */
fun repeatingTask(period: Int, unit: TimeUnit, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(period, period, -1, unit, false, runnable)

/**
 * Executes a repeating task with the specified period and times.
 *
 * @param period The time between each execution of the task.
 * @param unit The time unit of the period.
 * @param times The number of times the task should repeat. Defaults to -1 which means the task will repeat indefinitely.
 * @param runnable The code to be executed by the task.
 *
 * @return The BukkitTask representing the repeating task.
 */
fun repeatingTask(period: Int, unit: TimeUnit, times: Int = -1, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(period, period, times, unit, false, runnable)

/**
 * Schedule a repeating task with the specified period and unit.
 *
 * @param period the time between each execution of the task
 * @param unit the time unit of the period value
 * @param async whether to run the task asynchronously or not
 * @param runnable the code to be executed by the task
 * @return the BukkitTask representing the scheduled task
 */
fun repeatingTask(period: Int, unit: TimeUnit, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(period, period, -1, unit, async, runnable)

/**
 * Executes a repeating task asynchronously or synchronously in a Bukkit server.
 *
 * @param period The period between each task execution.
 * @param unit The time unit of the period.
 * @param times The number of times the task should be executed.
 *              If set to -1, the task will repeat indefinitely until cancelled.
 *              Default value is -1.
 * @param async Specifies whether the task should be executed asynchronously or synchronously.
 *              If set to true, the task will be executed asynchronously.
 *              If set to false, the task will be executed synchronously.
 * @param runnable The code to be executed for each task execution.
 *
 * @return The BukkitTask representing the repeating task.
 */
fun repeatingTask(period: Int, unit: TimeUnit, times: Int = -1, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(period, period, times, unit, async, runnable)

/**
 * Schedules a repeating task on the Bukkit scheduler.
 *
 * @param delayTicks The number of ticks to wait before the task is first executed.
 * @param periodTicks The number of ticks between each execution of the task.
 * @param async Whether the task should be executed asynchronously.
 * @param runnable The code to be executed by the task. This code is specified as a lambda expression
 *                that takes a [BukkitRunnable] object as its receiver.
 * @return The Bukkit task that has been scheduled.
 */
fun repeatingTask(delayTicks: Int, periodTicks: Int, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(
        delayTicks,
        periodTicks,
        -1,
        TimeUnit.TICKS,
        async,
        runnable
    )

/**
 * Schedules a repeating task with the specified delay, period, and number of times to repeat.
 *
 * @param delayTicks the delay in ticks before the task should start running
 * @param periodTicks the delay in ticks between each execution of the task
 * @param times the number of times the task should repeat (-1 for infinite)
 * @param async if true, the task will run asynchronously; otherwise, it will run synchronously
 * @param runnable the code to be executed by the task
 * @return a BukkitTask representing the scheduled task
 */
fun repeatingTask(delayTicks: Int, periodTicks: Int, times: Int = -1, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(
        delayTicks,
        periodTicks,
        times,
        TimeUnit.TICKS,
        async,
        runnable
    )

/**
 * Schedules a repeating task with the given delay, period, time unit, and runnable function.
 *
 * @param delay    the initial delay before the task is executed, in the specified time unit
 * @param period   the time between consecutive executions of the task, in the specified time unit
 * @param unit     the time unit of the delay and period parameters
 * @param runnable the function to be run repeatedly
 * @return the BukkitTask representing the scheduled task
 */
fun repeatingTask(delay: Int, period: Int, unit: TimeUnit, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(delay, period, -1, unit, false, runnable)

/**
 * Schedules a repeating task with the given delay, period, times, unit, and runnable.
 *
 * @param delay The delay in time units before the task is executed for the first time.
 * @param period The time period in time units between subsequent task executions.
 * @param times The number of times the task should be repeated. A value of -1 means the task will repeat indefinitely.
 * @param unit The time unit for the delay and period parameters.
 * @param runnable The code to be executed as part of the task. It is defined as a lambda expression with the BukkitRunnable as the receiver.
 * @return The BukkitTask object representing the scheduled repeating task.
 */
fun repeatingTask(delay: Int, period: Int, times: Int = -1, unit: TimeUnit, runnable: BukkitRunnable.() -> Unit): BukkitTask =
    repeatingTask(delay, period, times, unit, false, runnable)

/**
 * Creates a new instance of [BukkitRunnable] using the given lambda expression as the run method.
 *
 * @param runnable the lambda expression to be executed when the [BukkitRunnable] runs.
 * @return a new instance of [BukkitRunnable] with the specified run method.
 */
private fun createRunnable(runnable: BukkitRunnable.() -> Unit): BukkitRunnable {
    return object : BukkitRunnable() {
        override fun run() {
            runnable()
        }
    }
}

/**
 * Creates a `BukkitRunnable` with the specified number of times to run and a lambda expression to execute.
 *
 * @param times The number of times to run the `BukkitRunnable`. Defaults to -1, which means the `BukkitRunnable` will run indefinitely.
 * @param runnable The lambda expression to execute when the `BukkitRunnable` is run.
 * @return The created `BukkitRunnable`.
 */
private fun createRunnable(times: Int = -1, runnable: BukkitRunnable.() -> Unit): BukkitRunnable {
    var amount = 0
    return object : BukkitRunnable() {
        override fun run() {
            runnable()
            if (times == -1) return
            amount++
            if (amount >= times) cancel()
        }
    }
}