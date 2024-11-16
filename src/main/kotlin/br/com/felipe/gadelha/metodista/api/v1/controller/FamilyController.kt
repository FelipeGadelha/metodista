package br.com.felipe.gadelha.metodista.api.v1.controller

import br.com.felipe.gadelha.metodista.api.dto.request.FamilyRq
import br.com.felipe.gadelha.metodista.api.dto.request.MemberRq
import br.com.felipe.gadelha.metodista.domain.model.Family
import br.com.felipe.gadelha.metodista.domain.model.Member
import br.com.felipe.gadelha.metodista.domain.service.FamilyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/families")
class FamilyController(
    private val familyService: FamilyService
) {

    @GetMapping
    fun findAll(): MutableList<Family> {
        return familyService.findAll()
    }

    @PostMapping
    fun save(@RequestBody request: FamilyRq): ResponseEntity<Family> {
        val saved = familyService.save(request.toModel())
        return ResponseEntity.ok(saved)
    }
}