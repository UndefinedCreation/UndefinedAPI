package com.redmagic.undefinedapi.npc

import io.netty.channel.*
import java.net.SocketAddress


/**
 * The EmptyChannel class represents an empty channel implementation that extends the AbstractChannel class.
 * An empty channel does not perform any actual network operations and is always considered to be inactive and closed.
 * It is mainly used as a placeholder or a dummy channel for testing or mocking purposes.
 *
 * @param channel the underlying channel object
 */
class EmptyChannel1_20_4(channel: Channel?): AbstractChannel(channel) {
    private val config: ChannelConfig = DefaultChannelConfig(this)

    override fun config(): ChannelConfig {
        config.setAutoRead(true)
        return config
    }

    @Throws(Exception::class)
    override fun doBeginRead() {
    }

    @Throws(Exception::class)
    override fun doBind(arg0: SocketAddress?) {
    }

    @Throws(Exception::class)
    override fun doClose() {
    }

    @Throws(Exception::class)
    override fun doDisconnect() {
    }

    @Throws(Exception::class)
    override fun doWrite(arg0: ChannelOutboundBuffer?) {
    }

    override fun isActive(): Boolean {
        return false
    }

    override fun isCompatible(arg0: EventLoop?): Boolean {
        return false
    }

    override fun isOpen(): Boolean {
        return false
    }

    override fun localAddress0(): SocketAddress? {
        return null
    }

    override fun metadata(): ChannelMetadata {
        return ChannelMetadata(true)
    }

    override fun newUnsafe(): AbstractUnsafe? {
        return null
    }

    override fun remoteAddress0(): SocketAddress? {
        return null
    }
}