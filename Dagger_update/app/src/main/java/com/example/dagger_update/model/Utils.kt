package com.example.dagger_update.model

import android.content.Context
import com.example.dagger_update.deps.qualifier.ApplicationContext
import javax.inject.Inject

class Utils @Inject constructor(
    @ApplicationContext context: Context
) {
}