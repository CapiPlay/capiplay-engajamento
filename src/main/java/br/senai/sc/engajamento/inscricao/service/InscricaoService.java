package br.senai.sc.engajamento.inscricao.service;

import br.senai.sc.engajamento.exception.NaoEncontradoException;
import br.senai.sc.engajamento.inscricao.model.command.BuscarUmInscricaoCommand;
import br.senai.sc.engajamento.inscricao.model.command.CriarInscricaoCommand;
import br.senai.sc.engajamento.inscricao.model.entity.Inscricao;
import br.senai.sc.engajamento.inscricao.model.id.InscricaoId;
import br.senai.sc.engajamento.inscricao.repository.InscricaoRepository;
import br.senai.sc.engajamento.usuario.model.entity.Usuario;
import br.senai.sc.engajamento.usuario.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InscricaoService {
    private final InscricaoRepository repository;
    private final UsuarioService usuarioService;

    public void criar(CriarInscricaoCommand cmd) {
        try {
            Usuario usuario = usuarioService.retornaUsuario(cmd.getIdUsuario());
            Usuario canal = usuarioService.retornaUsuario(cmd.getIdCanal());
            if (repository.findByIdUsuarioAndIdCanal(usuario, canal) == null) {
                throw new NaoEncontradoException();
            }

            repository.deleteByIdUsuarioAndIdCanal(usuario, canal);
        } catch (NaoEncontradoException e) {
            Inscricao inscricao = new Inscricao();
            inscricao.setIdUsuario(usuarioService.retornaUsuario(cmd.getIdUsuario()));
            inscricao.setIdCanal(usuarioService.retornaUsuario(cmd.getIdCanal()));

            repository.save(inscricao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Inscricao buscarUm(BuscarUmInscricaoCommand cmd) {
        Usuario usuario = usuarioService.retornaUsuario(cmd.getIdUsuario());
        Usuario canal = usuarioService.retornaUsuario(cmd.getIdCanal());
        return repository.findByIdUsuarioAndIdCanal(usuario, canal);
    }
}
