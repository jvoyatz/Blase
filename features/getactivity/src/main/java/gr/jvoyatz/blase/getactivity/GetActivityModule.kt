package gr.jvoyatz.blase.getactivity

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object GetActivityModule {

    @Provides
    fun provideInitGetActivityState(): GetActivityUiState = GetActivityUiState()
}

