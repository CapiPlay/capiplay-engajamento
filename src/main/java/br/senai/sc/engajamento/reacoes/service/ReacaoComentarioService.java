package br.senai.sc.engajamento.reacoes.service;

import br.senai.sc.engajamento.comentario.model.entity.Comentario;
import br.senai.sc.engajamento.comentario.service.ComentarioService;
import br.senai.sc.engajamento.exception.NaoEncontradoException;
import br.senai.sc.engajamento.reacoes.model.command.reacaoComentario.BuscarTodosPorComentarioReacaoComentarioCommand;
import br.senai.sc.engajamento.reacoes.model.command.reacaoComentario.BuscarUmReacaoComentarioCommand;
import br.senai.sc.engajamento.reacoes.model.command.reacaoComentario.CriarReacaoComentarioCommand;
import br.senai.sc.engajamento.reacoes.model.entity.ReacaoComentario;
import br.senai.sc.engajamento.reacoes.repository.ReacaoComentarioRepository;
import br.senai.sc.engajamento.usuario.model.entity.Usuario;
import br.senai.sc.engajamento.usuario.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReacaoComentarioService {
    private final ReacaoComentarioRepository repository;
    private final UsuarioService usuarioService;
    private final ComentarioService comentarioService;

    public void criar(CriarReacaoComentarioCommand cmd) {
        try {
            Usuario usuario = usuarioService.retornaUsuario(cmd.getIdUsuario());
            Comentario comentario = comentarioService.retornaComentario(cmd.getIdComentario());
            ReacaoComentario reacao = repository.findByIdUsuarioAndIdComentario(usuario, comentario);
            if (reacao == null) {
                throw new NaoEncontradoException();
            } else if (reacao.isCurtida() == cmd.getCurtida()) {
                repository.deleteByIdUsuarioAndIdComentario(usuario, comentario);
            } else {
                reacao.setCurtida(!reacao.isCurtida());
            }
        } catch (NaoEncontradoException e) {
            ReacaoComentario reacao = new ReacaoComentario();
            reacao.setIdComentario(comentarioService.retornaComentario(cmd.getIdComentario()));
            reacao.setIdUsuario(usuarioService.retornaUsuario(cmd.getIdUsuario()));
            reacao.setCurtida(cmd.getCurtida());

            repository.save(reacao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ReacaoComentario buscarUm(BuscarUmReacaoComentarioCommand cmd) {
        ReacaoComentario reacaoComentario = null;
        try {
            Usuario usuario = usuarioService.retornaUsuario(cmd.getIdUsuario());
            Comentario comentario = comentarioService.retornaComentario(cmd.getIdComentario());
            reacaoComentario = repository.findByIdUsuarioAndIdComentario(usuario, comentario);
            if (reacaoComentario == null) {
                throw new NaoEncontradoException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return reacaoComentario;
    }

    public List<ReacaoComentario> buscarTodosPorComentario(BuscarTodosPorComentarioReacaoComentarioCommand cmd) {
        return repository.findAllByIdComentario(comentarioService.retornaComentario(cmd.getIdComentario()));
    }
}
