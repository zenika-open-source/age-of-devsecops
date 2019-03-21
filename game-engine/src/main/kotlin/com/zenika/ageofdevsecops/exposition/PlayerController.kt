package com.zenika.ageofdevsecops.exposition

import com.zenika.ageofdevsecops.application.PlayerManager
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/players")
class PlayerController(private val playerManager: PlayerManager) {

    @GetMapping
    fun fetchPlayerWithToken(@RequestParam("token") token: String, model: Model): String {
        val playerInfo = playerManager.onboardPlayer(token)
        model.addAttribute("player", playerInfo)
        return "playerInfo"
    }
}
