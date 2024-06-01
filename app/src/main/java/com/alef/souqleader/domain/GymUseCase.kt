package com.alef.souqleader.domain




import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.SymbolsResponse


import javax.inject.Inject

class GymUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getGym(): SymbolsResponse {
        return  repository.symbols()
    }

    private suspend fun result(
        res: (SymbolsResponse) -> Unit
    ) {
        repository.symbols().symbols
//        flow {
//            emit(repository.symbols())
//        }.flowOn(Dispatchers.IO)
//            .catch {
//                it.message?.let {
//                    Resource.DataError(
//                        null,
//                        0, null
//                    )
//                }
//            }
//            .buffer().collect {
//                res(it)
//            }
    }
}