package com.oddlycoder.cryptocur.domain.use_cases.get_coin

import com.oddlycoder.cryptocur.common.Resource
import com.oddlycoder.cryptocur.data.remote.dto.toCoin
import com.oddlycoder.cryptocur.data.remote.dto.toCoinDetail
import com.oddlycoder.cryptocur.domain.model.Coin
import com.oddlycoder.cryptocur.domain.model.CoinDetail
import com.oddlycoder.cryptocur.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success(coin))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check internet connection"))
        }
    }
}