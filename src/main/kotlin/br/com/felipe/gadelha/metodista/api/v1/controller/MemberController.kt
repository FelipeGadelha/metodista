package br.com.felipe.gadelha.metodista.api.v1.controller

import br.com.felipe.gadelha.metodista.api.dto.request.MemberRq
import br.com.felipe.gadelha.metodista.domain.model.Member
import br.com.felipe.gadelha.metodista.domain.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/members")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping
    fun findAll(): MutableList<Member> {
        return memberService.findAll()
    }

    @PostMapping
    fun save(@RequestBody request: MemberRq): ResponseEntity<Member> {
        val saved = memberService.save(request.toModel())
        return ResponseEntity.ok(saved)
    }
}