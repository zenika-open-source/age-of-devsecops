package com.zenika.ageofdevsecops.domain.challenge

data class Player(
        val id: String,
        val main: Instance, val node1: Instance, val node2: Instance, val ci: Instance,
        val openedSecurityGroup: SecurityGroup, val closedSecurityGroup: SecurityGroup, val sshSecurityGroup: SecurityGroup
)