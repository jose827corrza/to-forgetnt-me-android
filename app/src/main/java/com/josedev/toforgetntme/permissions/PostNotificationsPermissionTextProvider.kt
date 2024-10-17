package com.josedev.toforgetntme.permissions

class PostNotificationsPermissionTextProvider: PermissionsProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined){
            "It seems you permanently declined notifications permission. " +
                    "You can go to the app settings to grant it."

        } else {
            "This app requires Notifications to avoid forgetting things"
        }
    }
}