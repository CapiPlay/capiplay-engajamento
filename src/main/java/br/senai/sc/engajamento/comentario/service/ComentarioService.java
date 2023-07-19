package br.senai.sc.engajamento.comentario.service;

import br.senai.sc.engajamento.comentario.model.command.*;
import br.senai.sc.engajamento.comentario.model.entity.Comentario;
import br.senai.sc.engajamento.comentario.repository.ComentarioRepository;
import br.senai.sc.engajamento.exception.NaoEncontradoException;
import br.senai.sc.engajamento.usuario.model.entity.Usuario;
import br.senai.sc.engajamento.usuario.service.UsuarioService;
import br.senai.sc.engajamento.video.model.entity.Video;
import br.senai.sc.engajamento.video.service.VideoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class ComentarioService {

    private ComentarioRepository comentarioRepository;
    private UsuarioService usuarioService;
    private VideoService videoService;

    public Comentario criar(
            CriarComentarioCommand cmd
    ) {
        Video video = videoService.retornaVideo(cmd.getIdVideo());
        Usuario usuario = usuarioService.retornaUsuario(cmd.getIdUsuario());
        Comentario comentario = new Comentario(
                cmd.getTexto(),
                usuario,
                video
        );
        return comentarioRepository.save(comentario);
    }

    public Comentario buscarUm(
            BuscarUmComentarioCommand cmd
    ) {
        return comentarioRepository.findByIdComentario(cmd.getIdComentario()).orElseThrow(NaoEncontradoException::new);
//        return retornaComentario(cmd.getIdComentario());
    }

    public List<Comentario> buscarTodosPorVideo(
            BuscarTodosPorVideoComentarioCommand cmd
    ) {
        return comentarioRepository.findAllByIdVideo(videoService.retornaVideo(cmd.getIdVideo()));
    }

    public void adicionarResposta(
            AdicionarRespostaComentarioCommand cmd
    ) {
        Comentario comentario = retornaComentario(cmd.getIdComentario());
        comentario.setQtdRespostas(
                comentario.getQtdRespostas() + 1
        );
        comentarioRepository.save(comentario);
    }

    public void deletar(
            DeletarComentarioCommand cmd
    ) {
        try {
            Comentario comentario = retornaComentario(cmd.getIdComentario());
            if (!(cmd.getIdUsuario().equals(comentario.getIdUsuario().getIdUsuario()))) {
                throw new NaoEncontradoException();
            }
            comentarioRepository.delete(comentario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Comentario retornaComentario(String idComentario) {
        return comentarioRepository.findByIdComentario(idComentario).orElseThrow(NaoEncontradoException::new);
    }

}