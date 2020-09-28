package ru.cardsmobile.hmstest.di

import android.app.Application
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.safetynet.SafetyNet
import com.google.firebase.iid.FirebaseInstanceId
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.api.HuaweiApiAvailability
import com.huawei.hms.support.api.safetydetect.SafetyDetect
import ru.cardsmobile.hmstest.data.repository.MobileServicesRepositoryImpl
import ru.cardsmobile.hmstest.data.repository.PushRepositoryImpl
import ru.cardsmobile.hmstest.data.repository.SecurityRepositoryImpl
import ru.cardsmobile.hmstest.data.source.*
import ru.cardsmobile.hmstest.data.source.availability.EmuiDataSource
import ru.cardsmobile.hmstest.data.source.availability.GoogleServicesAvailabilityDataSource
import ru.cardsmobile.hmstest.data.source.availability.HuaweiServicesAvailabilityDataSource
import ru.cardsmobile.hmstest.domain.repository.MobileServicesRepository
import ru.cardsmobile.hmstest.domain.repository.PushRepository
import ru.cardsmobile.hmstest.domain.repository.SecurityRepository
import ru.cardsmobile.hmstest.domain.usecase.GetPushTokenWithTimeout
import ru.cardsmobile.hmstest.domain.usecase.GetSecurityCheckResult
import ru.cardsmobile.hmstest.domain.usecase.OnPushTokenUpdated
import ru.cardsmobile.hmstest.domain.usecase.SelectMobileServiceType
import ru.cardsmobile.hmstest.presentation.map.GeoMapFactory

object Di {

    lateinit var application: Application

    val selectMobileServiceType: SelectMobileServiceType
        get() = SelectMobileServiceType(mobileServicesRepository)

    val getPushTokenWithTimeout: GetPushTokenWithTimeout
        get() = GetPushTokenWithTimeout(pushRepository, selectMobileServiceType)

    val onPushTokenUpdated: OnPushTokenUpdated
        get() = OnPushTokenUpdated(pushRepository)

    val getSecurityCheckResult: GetSecurityCheckResult
        get() = GetSecurityCheckResult(securityRepository, selectMobileServiceType)

    val geoMapFactory: GeoMapFactory by lazy {
        GeoMapFactory()
    }

    private val pushRepository: PushRepository by lazy {
        PushRepositoryImpl(
            fcmDataSource,
            hmsDataSource
        )
    }

    private val securityRepository: SecurityRepository by lazy {
        SecurityRepositoryImpl(
            safetyNetDataSource,
            safetyDetectDataSource
        )
    }

    private val googleServicesAvailabilityDataSource: GoogleServicesAvailabilityDataSource by lazy {
        GoogleServicesAvailabilityDataSource(
            application,
            GoogleApiAvailability.getInstance()
        )
    }

    private val huaweiServicesAvailabilityDataSource: HuaweiServicesAvailabilityDataSource by lazy {
        HuaweiServicesAvailabilityDataSource(
            application,
            HuaweiApiAvailability.getInstance(),
            emuiDataSource
        )
    }

    private val mobileServicesRepository: MobileServicesRepository by lazy {
        MobileServicesRepositoryImpl(
            listOf(
                googleServicesAvailabilityDataSource,
                huaweiServicesAvailabilityDataSource
            )
        )
    }

    private val emuiDataSource: EmuiDataSource by lazy {
        EmuiDataSource()
    }

    private val fcmDataSource by lazy {
        val firebaseInstanceId = FirebaseInstanceId.getInstance()
        FcmDataSource(firebaseInstanceId)
    }

    private val hmsDataSource: HmsDataSource by lazy {
        HmsDataSource(
            hmsInstanceId = HmsInstanceId.getInstance(application),
            agConnectServicesConfig = AGConnectServicesConfig.fromContext(application)
        )
    }

    private val securityBackendDataSource by lazy {
        SecurityBackendDataSource()
    }

    private val safetyNetDataSource by lazy {
        val safetyNetClient = SafetyNet.getClient(application)
        SafetyNetDataSource(safetyNetClient, securityBackendDataSource)
    }

    private val safetyDetectDataSource by lazy {
        val safetyDetectClient = SafetyDetect.getClient(application)
        SafetyDetectDataSource(
            safetyDetectClient = safetyDetectClient,
            agConnectServicesConfig = AGConnectServicesConfig.fromContext(application),
            securityBackendDataSource = securityBackendDataSource
        )
    }
}
