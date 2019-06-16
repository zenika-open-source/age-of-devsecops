package com.zenika.ageofdevsecops.application

import com.zenika.ageofdevsecops.domain.flagsubmission.FlagSubmission
import com.zenika.ageofdevsecops.domain.flagsubmission.FlagSubmissionRepository
import com.zenika.ageofdevsecops.domain.player.PlayerInfoRepository
import com.zenika.ageofdevsecops.infrastructure.challenge.checker.FlagRepository

class FlagManager(
        private val playerInfoRepository: PlayerInfoRepository,
        private val flagRepository: FlagRepository,
        private val flagSubmissionRepository: FlagSubmissionRepository
) {
    fun submit(playerToken: String, submittedFlag: String): String {
        val playerInfo = playerInfoRepository.findByToken(playerToken) ?: return "Token d'équipe invalide"
        flagRepository.findById(submittedFlag) ?: return "Flag invalide"
        val duplicateSubmission = flagSubmissionRepository.findByPlayerAndFlag(playerInfo.id, submittedFlag)
        if (duplicateSubmission != null) {
            return "Flag déjà soumis"
        }
        flagSubmissionRepository.save(FlagSubmission(playerInfo.id, submittedFlag))

        return "Excellent ! Flag soumis avec succès: $submittedFlag"
    }

}
