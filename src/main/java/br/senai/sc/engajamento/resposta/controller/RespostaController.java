package br.senai.sc.engajamento.resposta.controller;

import br.senai.sc.engajamento.resposta.model.command.BuscarTodosPorComentarioRespostaCommand;
import br.senai.sc.engajamento.resposta.model.command.BuscarUmaRespostaCommand;
import br.senai.sc.engajamento.resposta.model.command.CriarRespostaCommand;
import br.senai.sc.engajamento.resposta.model.command.DeletarRespostaCommand;
import br.senai.sc.engajamento.resposta.model.entity.Resposta;
import br.senai.sc.engajamento.resposta.service.RespostaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/engajamento/resposta")
public class RespostaController {

    private RespostaService respostaService;

    @PostMapping()
    private ResponseEntity<Resposta> criar(
            @RequestBody @Valid CriarRespostaCommand cmd
    ) {
        return ResponseEntity.ok(respostaService.criar(cmd));
    }

    @GetMapping()
    private ResponseEntity<Resposta> buscarUm(
            @RequestBody @Valid BuscarUmaRespostaCommand cmd
    ) {
        return ResponseEntity.ok(respostaService.buscarUm(cmd));
    }

    /*Pega todos os comentários referentes a um vídeo*/
    @GetMapping("/buscar-todos-por-comentario")
    private ResponseEntity<List<Resposta>> buscarTodosPorComentario(
            @RequestBody @Valid BuscarTodosPorComentarioRespostaCommand cmd
    ) {
        return ResponseEntity.ok(respostaService.buscarTodosPorComentario(cmd));
    }

    @DeleteMapping()
    private ResponseEntity<Void> deletar(
            @RequestBody DeletarRespostaCommand cmd
    ) {
        respostaService.deletar(cmd);
        return ResponseEntity.ok().build();
    }

}
