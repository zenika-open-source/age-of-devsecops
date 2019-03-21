package com.zenika.ageofdevsecops.domain.challenge.event

interface DomainEventBus {
    fun emit(event: Event)
}
