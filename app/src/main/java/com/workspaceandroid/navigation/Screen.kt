package com.workspaceandroid.navigation

const val DETAILS_ARGUMENT_KEY = "id"
const val DETAILS_ARGUMENT_KEY2 = "name"

sealed class Screen(val route: String) {

    object Login: Screen(route = "login_screen")
    object SignUp: Screen(route = "sign_up_screen")

    object Collection: Screen(route = "collection_screen")
    object AddPhrase: Screen(route = "add_phrase_screen")

    object Groups: Screen(route = "groups_screen")
    object AddGroup: Screen(route = "add_group_screen")

    object Home: Screen(route = "home_screen")
    object Details: Screen(route = "details_screen/{$DETAILS_ARGUMENT_KEY}/{$DETAILS_ARGUMENT_KEY2}") {
        fun passNameAndId(id: Int = 0,
                          name: String = ""): String {
            return "details_screen/$id/$name"
        }
    }

}
