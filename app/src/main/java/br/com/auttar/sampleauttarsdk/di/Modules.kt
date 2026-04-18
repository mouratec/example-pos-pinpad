package br.com.auttar.sampleauttarsdk.di

import android.app.Application

fun modulesAuttarKoin(application: Application) = listOf(
    AuttarSdkModule().provider(application),
    ViewModelModule().provider()
)