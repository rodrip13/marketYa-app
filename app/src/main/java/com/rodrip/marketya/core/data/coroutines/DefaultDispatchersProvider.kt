package com.rodrip.marketya.core.data.coroutines

import com.rodrip.marketya.core.coroutines.DispatchersProvider
import javax.inject.Inject

class DefaultDispatchersProvider @Inject constructor() : DispatchersProvider {
    override val main = kotlinx.coroutines.Dispatchers.Main
    override val io = kotlinx.coroutines.Dispatchers.IO
    override val default = kotlinx.coroutines.Dispatchers.Default
}