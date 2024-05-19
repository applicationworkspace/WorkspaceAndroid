//package com.workspaceandroid.data.di
//
//import android.content.Context
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ActivityComponent
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object SharedPrefModule {
//
//    @Provides
//    fun provideSharedPref(@ApplicationContext context: Context): SharedPrefs {
//        return SharedPrefs(context)
//    }
//}