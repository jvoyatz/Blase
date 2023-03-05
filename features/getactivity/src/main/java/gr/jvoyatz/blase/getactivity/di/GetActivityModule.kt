package gr.jvoyatz.blase.getactivity.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import gr.jvoyatz.blase.getactivity.ui.state.GetActivityUiState

@InstallIn(ViewModelComponent::class)
@Module
object GetActivityModule {

    @Provides
    fun provideInitGetActivityState(): GetActivityUiState = GetActivityUiState()
}

