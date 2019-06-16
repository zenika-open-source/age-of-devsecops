package com.zenika.ageofdevsecops.domain.flagsubmission

interface FlagSubmissionRepository {
    fun findByPlayerAndFlag(playerId: String, flag: String): FlagSubmission?
    fun save(flagSubmission: FlagSubmission)
    fun findAllByPlayer(playerId: String): List<FlagSubmission>
}
