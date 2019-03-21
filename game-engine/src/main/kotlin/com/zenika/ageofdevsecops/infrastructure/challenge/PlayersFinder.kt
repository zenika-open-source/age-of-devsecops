package com.zenika.ageofdevsecops.infrastructure.challenge

import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.services.ec2.model.DescribeInstancesRequest
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest
import com.amazonaws.services.ec2.model.Filter
import com.amazonaws.services.ec2.model.Tag
import com.zenika.ageofdevsecops.domain.challenge.Instance
import com.zenika.ageofdevsecops.domain.challenge.Player
import com.zenika.ageofdevsecops.domain.challenge.SecurityGroup

class PlayersFinder(private val ec2: AmazonEC2) {

    private val players = mutableListOf<Player>()

    private val teamTagKey = "team"
    private val usageTagKey = "usage"
    private val nodeOpenedUsageTagValue = "nodeopened"
    private val nodeClosedUsageTagValue = "nodeclosed"
    private val sshUsageTagValue = "ssh"

    fun setUp() {
        val instanceRequest = DescribeInstancesRequest().withFilters(
                Filter("instance-state-name").withValues("running")
        )

        val teamsInstances = mutableMapOf<String, MutableMap<String, Instance>>()
        do {
            val response = ec2.describeInstances(instanceRequest)

            response.reservations.stream()
                    .flatMap { reservation -> reservation.instances.stream() }
                    .forEach { instance ->
                        var teamTag: Tag? = null
                        var usageTag: Tag? = null

                        for (tag in instance.tags) {
                            if (tag.key == teamTagKey) {
                                teamTag = tag
                            }
                            if (tag.key == usageTagKey) {
                                usageTag = tag
                            }
                        }

                        teamTag.notNull {
                            val teamInstances = teamsInstances.computeIfAbsent(it.value) { mutableMapOf() }

                            teamInstances[usageTag!!.value] = Instance(instance.instanceId, instance.privateIpAddress)
                        }

                    }

            instanceRequest.nextToken = response.nextToken

        } while (instanceRequest.nextToken != null)

        teamsInstances.map { entry -> getSecurityGroupAndBuildPlayer(entry) }
                .forEach { players.add(it) }
    }

    private fun getSecurityGroupAndBuildPlayer(teamInstance: Map.Entry<String, MutableMap<String, Instance>>): Player {
        val securityGroupRequest = DescribeSecurityGroupsRequest().withFilters(
                Filter("tag:$teamTagKey").withValues(teamInstance.key),
                Filter("tag:$usageTagKey").withValues(nodeOpenedUsageTagValue, nodeClosedUsageTagValue, sshUsageTagValue)
        )

        var openedSecurityGroup: SecurityGroup? = null
        var closedSecurityGroup: SecurityGroup? = null
        var sshSecurityGroup: SecurityGroup? = null

        do {
            val response = ec2.describeSecurityGroups(securityGroupRequest)

            response.securityGroups.stream()
                    .forEach { securityGroup ->
                        var teamTag: Tag? = null
                        var usageTag: Tag? = null

                        for (tag in securityGroup.tags) {
                            if (tag.key == teamTagKey) {
                                teamTag = tag
                            }
                            if (tag.key == usageTagKey) {
                                usageTag = tag
                            }
                        }

                        teamTag.notNull {
                            if (usageTag!!.value == nodeOpenedUsageTagValue) {
                                openedSecurityGroup = SecurityGroup(securityGroup.groupId)
                            }

                            if (usageTag.value == nodeClosedUsageTagValue) {
                                closedSecurityGroup = SecurityGroup(securityGroup.groupId)
                            }

                            if (usageTag.value == sshUsageTagValue) {
                                sshSecurityGroup = SecurityGroup(securityGroup.groupId)
                            }
                        }
                    }

            securityGroupRequest.nextToken = response.nextToken

        } while (securityGroupRequest.nextToken != null)

        return Player(teamInstance.key,
                teamInstance.value["loadbalancer"]!!, teamInstance.value["node1"]!!, teamInstance.value["node2"]!!,
                teamInstance.value["build"]!!, openedSecurityGroup!!, closedSecurityGroup!!, sshSecurityGroup!!)
    }

    fun getPlayers(): List<Player> {
        return players
    }

    private fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
        if (this != null) f(this)
    }
}

