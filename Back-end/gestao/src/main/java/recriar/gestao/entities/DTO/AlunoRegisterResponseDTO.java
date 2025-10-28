package recriar.gestao.entities.DTO;

import java.io.Serializable;
import java.time.LocalDate;

public class AlunoRegisterResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String matricula;
	private String nome;
	private String sobrenome;
	private String documento;
	private Integer idade;
	private Integer sexo;
	private LocalDate data_matricula;

	private Long responsavel;

	public AlunoRegisterResponseDTO() {
	}

	public AlunoRegisterResponseDTO(String matricula, String nome, String sobrenome, String documento, Integer idade,
			Integer sexo, LocalDate data_matricula, Long responsavel) {
		this.matricula = matricula;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.documento = documento;
		this.idade = idade;
		this.sexo = sexo;
		this.data_matricula = data_matricula;
		this.responsavel = responsavel;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Integer getSexo() {
		return sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}

	public LocalDate getData_matricula() {
		return data_matricula;
	}

	public void setData_matricula(LocalDate data_matricula) {
		this.data_matricula = data_matricula;
	}

	public Long getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Long responsavel) {
		this.responsavel = responsavel;
	}

}
