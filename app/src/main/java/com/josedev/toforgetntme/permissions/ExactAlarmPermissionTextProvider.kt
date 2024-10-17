package com.josedev.toforgetntme.permissions

class ExactAlarmPermissionTextProvider: PermissionsProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined){
            "It seems you permanently declined exact alarm permission. " +
                    "You can go to the app settings to grant it."

        } else {
            "This app requires Alarms to make you remember before the event."
        }
    }
}