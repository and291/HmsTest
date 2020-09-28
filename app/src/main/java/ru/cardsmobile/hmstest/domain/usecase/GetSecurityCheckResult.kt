package ru.cardsmobile.hmstest.domain.usecase

import io.reactivex.rxjava3.core.Maybe
import ru.cardsmobile.hmstest.domain.repository.SecurityRepository
import ru.cardsmobile.hmstest.domain.usecase.SelectMobileServiceType.*

class GetSecurityCheckResult(
    private val securityRepository: SecurityRepository,
    private val selectEnvironment: SelectMobileServiceType
) {

    operator fun invoke(): Maybe<String> = selectEnvironment(Case.Security)
        .flatMapSingle { securityRepository.getSecurityCheckResult(it) }
}
