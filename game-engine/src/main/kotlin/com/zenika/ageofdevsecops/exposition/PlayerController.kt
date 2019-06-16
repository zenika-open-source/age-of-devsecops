package com.zenika.ageofdevsecops.exposition

import com.zenika.ageofdevsecops.application.FlagManager
import com.zenika.ageofdevsecops.application.PlayerManager
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/players")
class PlayerController(private val playerManager: PlayerManager, private val flagManager: FlagManager) {

    @GetMapping("/{token}")
    fun playerInfo(@PathVariable token: String, model: Model): String {
        val playerInfo = playerManager.onboardPlayer(token)
        model.addAttribute("player", playerInfo)
        model.addAttribute("flagSubmissionModel", FlagSubmissionModel())
        return "playerInfo"
    }

    @PostMapping("/{token}")
    fun submitFlag(@PathVariable token: String, @ModelAttribute flagSubmissionModel: FlagSubmissionModel, model: Model): String {
        val playerInfo = playerManager.onboardPlayer(token)
        val result = flagManager.submit(token, flagSubmissionModel.submittedFlag)
        model.addAttribute("player", playerInfo)
        model.addAttribute("flagSubmissionModel", flagSubmissionModel)
        model.addAttribute("result", result)
        return "playerInfo"
    }
}
