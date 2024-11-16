package br.com.felipe.gadelha.metodista.api.v1.controller

import br.com.felipe.gadelha.metodista.api.dto.View
import br.com.felipe.gadelha.metodista.api.dto.request.BasketDistributionRq
import br.com.felipe.gadelha.metodista.api.dto.response.BasketDistributionRs
import br.com.felipe.gadelha.metodista.domain.service.BasketDistributionService
import com.fasterxml.jackson.annotation.JsonView
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/v1/basket-distribution"])
class BasketDistributionController(
    private val basketDistributionService: BasketDistributionService
) {
    @JsonView(View.Basic::class)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll() = ResponseEntity
        .ok(basketDistributionService.findAll().map {b -> BasketDistributionRs(b) })

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID) = ResponseEntity
        .ok(BasketDistributionRs(basketDistributionService.findById(id)))

    @PostMapping
    fun save(@RequestBody @Valid basicRq: BasketDistributionRq): ResponseEntity<BasketDistributionRs> {
        val saved = basketDistributionService.save(basicRq.toModel())
        return ResponseEntity<BasketDistributionRs>(BasketDistributionRs(saved), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody @Valid basicRq: BasketDistributionRq): ResponseEntity<BasketDistributionRs> {
        val basic = basketDistributionService.update(id, basicRq.toModel())
        return ResponseEntity.ok(BasketDistributionRs(basic))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) { basketDistributionService.deleteById(id) }
}