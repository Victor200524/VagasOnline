'use client'; // Serve para usar hooks como useState e useRouter
import { useState } from 'react';
import { createVaga, updateVaga } from '../../service';
import styles from '../../styles/TelaCadastroVaga.module.css';
import { useRouter } from 'next/navigation';

export default function TelaCadastroVaga({ empresas, cargos, vagaParaEditar }) {
  const router = useRouter();

  // flag para saber se estamos em modo de edição ou criação
  const isEditMode = !!vagaParaEditar;

  // se caso a flag for para edição, preenche os campos com os dados da vaga
  const [formData, setFormData] = useState({
    empresa: vagaParaEditar?.empresa || null,
    cargo: vagaParaEditar?.cargo || '',
    cidade: vagaParaEditar?.cidade || '',
    estado: vagaParaEditar?.estado || 'SP',
    pre_requisitos: vagaParaEditar?.pre_requisitos || '',
    formacao: vagaParaEditar?.formacao || '',
    conhecimentos_requeridos: vagaParaEditar?.conhecimentos_requeridos || '',
    regime: vagaParaEditar?.regime || 'CLT',
    jornada_trabalho: vagaParaEditar?.jornada_trabalho || '',
    remuneracao: vagaParaEditar?.remuneracao || '',
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleEmpresaChange = (e) => {
    const nomeFantasia = e.target.value;
    const empresaObj = empresas.find(emp => emp.nome_fantasia === nomeFantasia);
    setFormData(prev => ({ ...prev, empresa: empresaObj || null }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.empresa || formData.cargo) {
      setError("Selecione uma empresa e um cargo.");

      setLoading(true);
      setError(null);
      setSuccess(null);

      // Se for edição, reutiliza o registro existente
      const dadosVaga = {
        ...formData,
        registro: isEditMode ? vagaParaEditar.registro : Math.random().toString(36).substring(2, 8).toUpperCase()
      };

      try {
        if (isEditMode) {
          // --- MODO DE EDIÇÃO ---
          await updateVaga(vagaParaEditar.registro, dadosVaga);
          setSuccess("Vaga atualizada com sucesso!");
        }
        else {
          // --- MODO DE CRIAÇÃO ---
          await createVaga(dadosVaga);
          setSuccess("Vaga cadastrada com sucesso!");
        }

        setTimeout(() => {
          router.push('/'); // Volta para a home
          router.refresh(); // Força a atualização dos dados na home
        }, 2000);

      } catch (err) {
        setError(`Erro ao ${isEditMode ? 'atualizar' : 'cadastrar'} vaga: ${err.message}`);
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <div className={styles.formContainer}>
      <h1 className={styles.title}>{isEditMode ? 'Editar Vaga' : 'Cadastrar Nova Vaga'}</h1>

      <form onSubmit={handleSubmit} className={styles.form}>
        <div className={styles.grid}>
          {/* Coluna 1 */}
          <div className={styles.column}>
            <div className={styles.formGroup}>
              <label htmlFor="empresa">Empresa</label>
              <select
                id="empresa"
                name="empresa"
                value={formData.empresa?.nome_fantasia || ''}
                onChange={handleEmpresaChange}
                required
              >
                <option value="">Selecione a empresa</option>
                {empresas.map(e => (
                  <option key={e.nome_fantasia} value={e.nome_fantasia}>{e.nome_fantasia}</option>
                ))}
              </select>
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="cidade">Cidade</label>
              <input type="text" id="cidade" name="cidade" value={formData.cidade} onChange={handleChange} required />
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="regime">Regime</label>
              <select id="regime" name="regime" value={formData.regime} onChange={handleChange}>
                <option value="CLT">CLT</option>
                <option value="PJ">PJ</option>
                <option value="Estágio">Estágio</option>
              </select>
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="formacao">Formação</label>
              <input type="text" id="formacao" name="formacao" value={formData.formacao} onChange={handleChange} />
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="pre_requisitos">Pré-Requisitos</label>
              <textarea id="pre_requisitos" name="pre_requisitos" rows="5" value={formData.pre_requisitos} onChange={handleChange}></textarea>
            </div>
          </div>

          {/* Coluna 2 */}
          <div className={styles.column}>
            <div className={styles.formGroup}>
              <label htmlFor="cargo">Cargo</label>
              <select id="cargo" name="cargo" value={formData.cargo} onChange={handleChange} required>
                <option value="">Selecione o cargo</option>
                {cargos.map(c => (
                  <option key={c.nome} value={c.nome}>{c.nome}</option>
                ))}
              </select>
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="estado">Estado</label>
              <input type="text" id="estado" name="estado" value={formData.estado} onChange={handleChange} maxLength="2" />
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="jornada_trabalho">Jornada de Trabalho</label>
              <input type="text" id="jornada_trabalho" name="jornada_trabalho" value={formData.jornada_trabalho} onChange={handleChange} />
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="remuneracao">Remuneração (R$)</label>
              <input type="text" id="remuneracao" name="remuneracao" value={formData.remuneracao} onChange={handleChange} placeholder="Ex: R$ 8.000,00" />
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="conhecimentos_requeridos">Conhecimentos</label>
              <textarea id="conhecimentos_requeridos" name="conhecimentos_requeridos" rows="5" value={formData.conhecimentos_requeridos} onChange={handleChange}></textarea>
            </div>
          </div>
        </div>

        {error && <p className={styles.messageError}>{error}</p>}
        {success && <p className={styles.messageSuccess}>{success}</p>}

        <button type="submit" className="primary" disabled={loading}>
          {loading ? "Salvando..." : (isEditMode ? "Salvar Alterações" : "Cadastrar Vaga")}
        </button>
      </form>
    </div>
  );
}