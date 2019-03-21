package com.zenika.ageofdevsecops.application

interface InstancesAccessManager {
    fun permitSshAccess()
    fun denySshAccess()
}
