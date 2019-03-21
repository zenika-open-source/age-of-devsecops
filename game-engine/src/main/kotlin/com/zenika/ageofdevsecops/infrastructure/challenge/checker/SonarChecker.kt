package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.Player
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Repository
class SonarChecker(private val restTemplate: RestTemplate) {

    fun getReport(player: Player): SonarReport {
        val report: Report? = restTemplate.getForObject("http://${player.ci.url}/sonar/api/measures/component?additionalFields=metrics" +
                "&component=com.zenika:uglysystem&metricKeys=bugs,vulnerabilities" +
                ",code_smells,coverage,tests,duplicated_lines_density,ncloc")

        val valueByType = report!!.component.measures.associateBy { it.metric }
        val bugs = valueByType["bugs"]!!.value.toInt()
        val vulnerabilities = valueByType["vulnerabilities"]!!.value.toInt()
        val codeSmells = valueByType["code_smells"]!!.value.toInt()
        val tests = valueByType["tests"]!!.value.toInt()
        val coverage = valueByType["coverage"]!!.value
        val duplicatedLinesDensity = valueByType["duplicated_lines_density"]!!.value
        val linesOfCode = valueByType["ncloc"]!!.value.toInt()

        return SonarReport(bugs, vulnerabilities, codeSmells, tests, coverage, duplicatedLinesDensity,linesOfCode)
    }

    data class SonarReport(val bugs: Int, val vulnerabilities: Int, val codeSmells: Int,
                           val tests: Int, val coverage: Double, val duplicatedLinesDensity: Double, val linesOfCode: Int)

    private data class Report(var component: Component)

    private data class Component(var measures: List<Measure>)

    private data class Measure(var metric: String, var value: Double)
}
