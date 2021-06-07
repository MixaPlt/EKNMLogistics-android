package net.eknm.eknmlogistics

import android.content.res.Resources
import net.eknm.eknmlogistics.api.mapsApi.MapsApi
import net.eknm.eknmlogistics.mapInteraction.DriversDrawerService
import net.eknm.eknmlogistics.mapInteraction.MapMarkerDrawerService
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@RunWith(JUnit4::class)
class DriversDrawerServiceTest: BaseTest() {

    @Mock
    lateinit var mockedMapsApi: MapsApi

    @Mock
    lateinit var mockedMapMarkerDriversDrawerService: MapMarkerDrawerService

    @Mock
    lateinit var mockedResources: Resources

    val driversDrawerService = DriversDrawerService.create(mockedMapsApi, mockedMapMarkerDriversDrawerService, mockedResources)

    override fun init() {
        
    }
}
