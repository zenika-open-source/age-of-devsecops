package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.FlagStatuses
import com.zenika.ageofdevsecops.domain.challenge.Player
import com.zenika.ageofdevsecops.domain.flagsubmission.FlagSubmissionRepository
import org.springframework.stereotype.Component

@Component
class FlagsChecker(
        flagRepository: FlagRepository,
        val flagSubmissionRepository: FlagSubmissionRepository
//        private val restTemplate: RestTemplate
) {
    private val secretFlagPrefix = "SECRET_"
    private lateinit var publicFlags: Map<String, Flag>
    private lateinit var secretFlags: Map<String, Flag>

    init {
        val allFlags = flagRepository.findAll()
        publicFlags = allFlags.filter { !it.id.startsWith(secretFlagPrefix) }.map { it.id to it }.toMap()
        secretFlags = allFlags.filter { it.id.startsWith(secretFlagPrefix) }.map { it.id to it }.toMap()
    }

    fun check(player: Player): FlagStatuses {
        return try {
            val playerFlags = flagSubmissionRepository.findAllByPlayer(player.id).map { it.flag }
//            val playerFlags = restTemplate.getForObject<List<String>>("https://${player.main.url}/flags")!!
            val playerPublicFlags = playerFlags.mapNotNull { publicFlags[it] }.distinct()
            val playerSecretFlags = playerFlags.mapNotNull { secretFlags[it] }.distinct()
            val totalDevPoints = playerPublicFlags.map { it.devPoint }.sum() + playerSecretFlags.map { it.devPoint }.sum()
            val totalSecPoints = playerPublicFlags.map { it.secPoint }.sum() + playerSecretFlags.map { it.secPoint }.sum()
            val totalOpsPoints = playerPublicFlags.map { it.opsPoint }.sum() + playerSecretFlags.map { it.opsPoint }.sum()
            FlagStatuses(playerPublicFlags.size, publicFlags.size, playerSecretFlags.size, totalDevPoints, totalSecPoints, totalOpsPoints)
        } catch (e: Exception) {
            FlagStatuses(0, publicFlags.size, 0, 0, 0, 0)
        }
    }

    fun publicFlagsCount(): Int {
        return publicFlags.size
    }
}
