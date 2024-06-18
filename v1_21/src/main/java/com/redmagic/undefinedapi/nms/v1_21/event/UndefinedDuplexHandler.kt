package com.redmagic.undefinedapi.nms.v1_21.event

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.network.protocol.Packet

/**
 * A custom ChannelDuplexHandler that allows performing custom logic on incoming and outgoing channel events.
 *
 * @property read A lambda function that determines whether to cancel the read event.
 * @property write A lambda function that determines whether to cancel the write event.
 */
class UndefinedDuplexHandler(private val read: Packet<*>.() -> Boolean, private val write: Any.() -> Boolean): ChannelDuplexHandler() {

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        if (msg == null) {
            super.channelRead(ctx, msg)
            return
        }
        if (msg is Packet<*>){
            val cancel = read.invoke(msg)
            if (cancel) return
        }
        super.channelRead(ctx, msg)
    }

    override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
        if (msg == null) {
            super.write(ctx, msg, promise)
            return
        }
        val cancel = write.invoke(msg)
        if (cancel) return

        super.write(ctx, msg, promise)
    }
}