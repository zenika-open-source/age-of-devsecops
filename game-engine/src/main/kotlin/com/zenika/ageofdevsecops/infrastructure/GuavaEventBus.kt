package com.zenika.ageofdevsecops.infrastructure

import com.google.common.eventbus.EventBus
import com.zenika.ageofdevsecops.domain.challenge.event.DomainEventBus
import com.zenika.ageofdevsecops.domain.challenge.event.Event

class GuavaEventBus(val eventBus: EventBus) : DomainEventBus {

    override fun emit(event: Event) {
        eventBus.post(event)
    }

}
