package com.redmagic.undefinedapi.scheduler

import com.redmagic.undefinedapi.UndefinedAPI
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

fun sync(runnable: BukkitRunnable.() -> Unit): BukkitTask = createRunnable(runnable).runTask(UndefinedAPI.plugin)
fun async(runnable: BukkitRunnable.() -> Unit): BukkitTask = createRunnable(runnable).runTaskAsynchronously(UndefinedAPI.plugin)

fun delay(ticks: Int, unit: TimeUnit = TimeUnit.TICKS, async: Boolean = false, runnable: BukkitRunnable.() -> Unit):BukkitTask{
    return if (async){
        createRunnable(runnable).runTaskLater(UndefinedAPI.plugin, unit.toTicks(ticks.toLong()))
    }else{
        createRunnable(runnable).runTaskLater(UndefinedAPI.plugin, unit.toTicks(ticks.toLong()))
    }
}

fun delay(ticks: Int = 1, runnable: BukkitRunnable.() -> Unit): BukkitTask = delay(ticks, TimeUnit.TICKS, false, runnable)
fun delay(ticks: Int = 1, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask = delay(ticks, TimeUnit.TICKS, async, runnable)


fun repeatingTask(delay: Int, period: Int, times: Int = -1, unit: TimeUnit = TimeUnit.TICKS, async: Boolean = false, runnable: BukkitRunnable.() -> Unit): BukkitTask{
    return if (async){
        createRunnable(times, runnable).runTaskTimerAsynchronously(UndefinedAPI.plugin, unit.toTicks(delay.toLong()), unit.toTicks(period.toLong()))
    }else{
        createRunnable(times ,runnable).runTaskTimer(UndefinedAPI.plugin, unit.toTicks(delay.toLong()), unit.toTicks(period.toLong()))
    }
}
fun repeatingTask(ticks: Int = 1, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(0, ticks, -1, TimeUnit.TICKS, false, runnable)
fun repeatingTask(ticks: Int = 1, times: Int = -1, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(0, ticks, times, TimeUnit.TICKS, false, runnable)
fun repeatingTask(periodTicks: Int = 1, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(periodTicks, periodTicks, -1, TimeUnit.TICKS, async, runnable)
fun repeatingTask(periodTicks: Int = 1, async: Boolean, times: Int = -1, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(periodTicks, periodTicks, times, TimeUnit.TICKS, async, runnable)
fun repeatingTask(period: Int, unit: TimeUnit, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(period, period, -1, unit, false, runnable)
fun repeatingTask(period: Int, unit: TimeUnit, times: Int = -1, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(period, period, times, unit, false, runnable)
fun repeatingTask(period: Int, unit: TimeUnit, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(period, period, -1, unit, async, runnable)
fun repeatingTask(period: Int, unit: TimeUnit, times: Int = -1, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(period, period, times, unit, async, runnable)
fun repeatingTask(delayTicks: Int, periodTicks: Int, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(delayTicks, periodTicks, -1, TimeUnit.TICKS, async, runnable)
fun repeatingTask(delayTicks: Int, periodTicks: Int, times: Int = -1, async: Boolean, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(delayTicks, periodTicks, times, TimeUnit.TICKS, async, runnable)
fun repeatingTask(delay: Int, period: Int, unit: TimeUnit, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(delay, period, -1, unit, false, runnable)
fun repeatingTask(delay: Int, period: Int, times: Int = -1, unit: TimeUnit, runnable: BukkitRunnable.() -> Unit): BukkitTask = repeatingTask(delay, period, times, unit, false, runnable)

private fun createRunnable(runnable: BukkitRunnable.() -> Unit): BukkitRunnable{
    return object : BukkitRunnable(){
        override fun run() {
            runnable()
        }
    }
}

private fun createRunnable(times: Int = -1, runnable: BukkitRunnable.() -> Unit): BukkitRunnable{
    var amount = 0
    return object : BukkitRunnable(){
        override fun run() {
            runnable()
            if (times == -1) return
            amount++
            if (amount >= times) {
                cancel()
            }
        }
    }
}