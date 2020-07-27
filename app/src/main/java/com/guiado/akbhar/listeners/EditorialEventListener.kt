package com.guiado.akbhar.listeners

import com.guiado.akbhar.viewmodel.MagazineViewModel
import com.guiado.akbhar.viewmodel.MoroccoViewModel

interface EditorialEventListener {


        fun onClickAdSearchListItem(countriesViewModel : MagazineViewModel, position: Int)
        fun launchNews(countriesViewModel : MagazineViewModel, position: Int)



}