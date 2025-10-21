package recriar.gestao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import recriar.gestao.entities.Usuario;
import recriar.gestao.entities.DTO.AuthResponseDTO;
import recriar.gestao.entities.DTO.ProfessorRegisterDTO;
import recriar.gestao.entities.DTO.UsuarioInfoDTO;
import recriar.gestao.entities.DTO.UsuarioLoginDTO;
import recriar.gestao.entities.DTO.UsuarioPasswordDTO;
import recriar.gestao.entities.DTO.UsuarioProfileDTO;
import recriar.gestao.entities.DTO.UsuarioRegisterDTO;
import recriar.gestao.entities.enums.Tipo;
import recriar.gestao.repositories.UsuarioRepository;
import recriar.gestao.security.GerarToken;
import recriar.gestao.service.exceptions.CredenciaisInvalidasException;
import recriar.gestao.service.exceptions.CriacaoNegadaException;
import recriar.gestao.service.exceptions.UsuarioInexistenteException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repositor;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private GerarToken gerarToken;
	
	/* ----------------------------------------------------------------------------------------------*/
	
	public AuthResponseDTO register(UsuarioRegisterDTO user) {
		
		Usuario entidade = converter(user);
		
		if (repositor.existsByEmail(entidade.getEmail())) {
			throw new CriacaoNegadaException("E-mail já cadastrado!");
		}
		
		Usuario userSalvo = repositor.save(entidade);
		UsuarioInfoDTO dados = new UsuarioInfoDTO(userSalvo);
		String token = gerarToken.gerarToken(userSalvo);
		
		return new AuthResponseDTO(token, dados);
	}
	
	private Usuario converter(UsuarioRegisterDTO obj) {
		Usuario entidade = new Usuario();
		
		entidade.setNome(obj.getNome());
		entidade.setEmail(obj.getEmail());
		entidade.setSenha_hash(passwordEncoder.encode(obj.getPassword()));
		entidade.setTipo(Tipo.valueof(obj.getTipo()));
		
		return entidade;		
	}
	
	public AuthResponseDTO register(ProfessorRegisterDTO user) {
		
		Usuario entidade = converter(user);
		
		if (repositor.existsByEmail(entidade.getEmail())) {
			throw new CriacaoNegadaException("E-mail já cadastrado!");
		}
		
		Usuario userSalvo = repositor.save(entidade);
		UsuarioInfoDTO dados = new UsuarioInfoDTO(userSalvo);
		String token = gerarToken.gerarToken(userSalvo);
		
		return new AuthResponseDTO(token, dados);
	}
	
	private Usuario converter(ProfessorRegisterDTO obj) {
		Usuario entidade = new Usuario();
		
		entidade.setEmail(obj.getEmail());
		entidade.setSenha_hash(passwordEncoder.encode(obj.getPassword()));
		entidade.setTipo(Tipo.PROFESSOR);
		
		return entidade;		
	}
	
	/* ----------------------------------------------------------------------------------------------*/
	
	public AuthResponseDTO login(UsuarioLoginDTO obj) {
		Usuario user = repositor.findByEmail(obj.getEmail());
		
		if(!passwordEncoder.matches(obj.getPassword(), user.getSenha_hash())) {
			throw new CredenciaisInvalidasException("E-mail ou Senha inválidos");
		}
		
		String token = gerarToken.gerarToken(user);
		UsuarioInfoDTO entidade = new UsuarioInfoDTO(user);
		
		return new AuthResponseDTO(token, entidade);
	}
	
	/* ----------------------------------------------------------------------------------------------*/
	
	public UsuarioInfoDTO profile(Long id) {
		Usuario entidade = repositor.findById(id).orElseThrow(() -> new UsuarioInexistenteException("Usuário não encontrado!"));
		return new UsuarioInfoDTO(entidade);
	}
	
	/* ----------------------------------------------------------------------------------------------*/
	
	public Usuario updateProfile(Long id, UsuarioProfileDTO obj) {
		
		if(repositor.existsByEmail(obj.getEmail()) && repositor.existsByNome(obj.getNome())) {
			throw new CredenciaisInvalidasException("E-mail ou Nome já existentes");
		}
		
		Usuario entidade = repositor.getReferenceById(id);
		atualizar(entidade, obj);
		
		return repositor.save(entidade);
		
	}
	
	private void atualizar(Usuario entidade, UsuarioProfileDTO obj) {
		
		entidade.setNome(obj.getNome());
		entidade.setEmail(obj.getEmail());
		
	}
	
	/* ----------------------------------------------------------------------------------------------*/
	
	public Usuario updatePassword (Long id, UsuarioPasswordDTO obj) {
		
		Usuario entidade = repositor.getReferenceById(id);
		
		if (!passwordEncoder.matches(obj.getSenhaAtual(), entidade.getSenha_hash())) {
		    throw new CredenciaisInvalidasException("SENHA INCORRETA");
		}

		if (passwordEncoder.matches(obj.getNovaSenha(), entidade.getSenha_hash())) {
		    throw new CredenciaisInvalidasException("SENHA ANTERIOR NÃO PODE SER USADA");
		}
		
		entidade.setSenha_hash(passwordEncoder.encode(obj.getNovaSenha()));
		
		return repositor.save(entidade);
	}
	
	/* ----------------------------------------------------------------------------------------------*/
}
