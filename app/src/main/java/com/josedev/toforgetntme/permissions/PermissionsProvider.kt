package com.josedev.toforgetntme.permissions

interface PermissionsProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}