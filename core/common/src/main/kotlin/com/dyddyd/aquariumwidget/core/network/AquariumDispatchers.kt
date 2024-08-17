package com.dyddyd.aquariumwidget.core.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val aquariumDispatchers: AquariumDispatchers)

enum class AquariumDispatchers {
    Default,
    IO,
}
