package com.foodapp.foodapp.presentation.restaurantScreen.detailScreen

import com.foodapp.foodapp.domain.models.Restaurant

sealed interface DetailScreenAction {
//    data object OnGettingLocation:DetailScreenAction
    data class OnNameChanged(val name:String):DetailScreenAction
    data class OnContactChanged(val contact:String):DetailScreenAction
    data class OnAddressChanged(val address:String):DetailScreenAction
    data class OnTagsChanged(val tags:List<String>):DetailScreenAction
    data object OnImageUploadTrigger:DetailScreenAction
    data class OnImageSelected(val byteArray: ByteArray?):DetailScreenAction
    data object OnAddRestaurant:DetailScreenAction
}