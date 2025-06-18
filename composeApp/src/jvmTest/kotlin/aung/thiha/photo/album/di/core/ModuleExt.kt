package aung.thiha.photo.album.di.core

import org.koin.core.module.Module

inline fun <reified I : Any, reified C : I> Module.fake(noinline factory: () -> C) {
    val scoped = ThreadScoped(factory)

    single<I> { scoped.instance }
    single<C> { scoped.instance }
}

class ThreadScoped<T>(private val factory: () -> T) {
    private val threadLocal = ThreadLocal<T>()

    val instance: T
        get() = threadLocal.get() ?: factory().also { threadLocal.set(it) }

    fun set(value: T) {
        threadLocal.set(value)
    }

    fun clear() {
        threadLocal.remove()
    }
}
