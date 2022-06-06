package com.meeweel.data.remote

import com.meeweel.core.domain.CarBrand
import com.meeweel.core.domain.CarColor
import com.meeweel.core.domain.CarModel
import io.reactivex.rxjava3.core.Single

class FakeRemoteDataSourceImpl : RemoteDataSource {

    private val carList: MutableList<CarModel> = mutableListOf(
        CarModel(
            1,
            CarBrand.BMW,
            "BMW X5 IV (G05)",
            2020,
            "https://farm1.staticflickr.com/692/21559226064_e82043de3a_o.jpg",
            false,
            120000,
            CarColor.BLACK,
            7600000
        ),
        CarModel(
            2,
            CarBrand.BMW,
            "BMW 1 серии 118i II (F20/F21) Рестайлинг 2",
            2017,
            "https://134706.selcdn.ru:443/v1/SEL_39171/freshauto-crm-production-public/system/image/file/4793875/vehicle_slider_DSC_0130%402x.jpg",
            false,
            39420,
            CarColor.RED,
            2156550
        ),
        CarModel(
            3,
            CarBrand.MERCEDES,
            "Mercedes-Benz E-Класс 200 d V (W213, S213, C238)",
            2020,
            "https://yastatic.net/naydex/autoru/U13eRw724/46ed10UwSL/J1vg0GKQYc-I7zijgTaFpujHEYzpE6BdLpvs6UMIoB-q_AJR7YVbXY_eElTclywXWvgHLqxegdoxOgT06khN2TAK8YAg1HnA3_0v6YJ30PGg2QfQrQypZwX3zN5oKIAjwMMjQ7fEDUXLknsy09VKRjabQQ6oFO-pEQl3-64VbYEoSvP3YqEOz57ZRauuP7WV2YM-se1MiXwx4uzrej2iEszPCHCK5AYv3bJhGaDkf2Ibo2AFljbCgjwGVOKENG-GFm8",
            false,
            40051,
            CarColor.BLACK,
            3599000
        ),
        CarModel(
            4,
            CarBrand.MERCEDES,
            "Mercedes-Benz C-Класс 180 IV (W205)",
            2015,
            "https://avatars.mds.yandex.net/get-autoru-vos/6358557/6561f46d1f6306628f89383b49f3a1b5/1200x900n",
            false,
            145000,
            CarColor.WHITE,
            2700000
        ),
        CarModel(
            5,
            CarBrand.AUDI,
            "Audi A5 I (8T) Рестайлинг",
            2016,
            "https://avatars.mds.yandex.net/get-autoru-vos/5966969/619a35eabe79fd6d88ab40a06ac2898f/1200x900n",
            false,
            22160,
            CarColor.WHITE,
            2788888
        ),
        CarModel(
            6,
            CarBrand.AUDI,
            "Audi A4 IV (B8)",
            2008,
            "https://avatars.mds.yandex.net/get-autoru-vos/5378895/3ae07fffa251f5f403eaa1455fdf1429/1200x900n",
            false,
            250000,
            CarColor.BLACK,
            900000
        ),
        CarModel(
            7,
            CarBrand.KIA,
            "Kia Ceed III",
            2019,
            "https://avatars.mds.yandex.net/get-autoru-vos/4076970/839b66e75200d2093f4a1361b465f35c/1200x900n",
            false,
            41740,
            CarColor.BLUE,
            1600000
        ),
        CarModel(
            8,
            CarBrand.KIA,
            "Kia Spectra I Рестайлинг 2",
            2006,
            "https://avatars.mds.yandex.net/get-autoru-vos/6202689/cb2f0fccb1949418426eeffc1e27fad1/1200x900n",
            false,
            190000,
            CarColor.BLUE,
            175000
        ),
        CarModel(
            9,
            CarBrand.UNKNOWN,
            "LADA (ВАЗ) 2121 (4x4) I",
            1982,
            "https://avatars.mds.yandex.net/get-autoru-vos/5309308/67dc110328e00fe5759f3a2145eb8075/1200x900n",
            true,
            0,
            CarColor.UNKNOWN,
            420000
        )
    )

    override fun getCarList(): Single<List<CarModel>> = Single.just(carList)
}