// src/app/components/TelaCadastroVaga/index.jsx
'use client';

import { useState } from 'react';
import { createVaga } from '@/service';
import styles from '../../styles/TelaCadastroVaga.module.css';
import { useRouter } from 'next/navigation'; 

export default function TelaCadastroVaga({ empresas, cargos }) {
  const router = useRouter();
  
  // O estado do formulário agora reflete 100% os campos do vagas.json
  const [formData, setFormData] = useState({
    empresa: '', // Vai guardar o nome_fantasia
    cargo: '',   // Vai guardar o nome do cargo
    cidade: '',
    estado: 'SP',
    pre_requisitos: '', // Novo campo
    formacao: '',
    conhecimentos_requeridos: '', // Novo campo
    regime: 'CLT',
    jornada_trabalho: '', // Novo campo
    remuneracao: '',      // Agora é uma string
  });
  
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(null);

    // O service agora espera os dados neste formato
    const dadosVaga = {
      ...formData,
    };

    try {
      await createVaga(dadosVaga);
      setSuccess("Vaga cadastrada com sucesso!");
      // Limpa o formulário para os novos campos
      setFormData({
        empresa: '', cargo: '', cidade: '', estado: 'SP',
        pre_requisitos: '', formacao: '', conhecimentos_requeridos: '',
        regime: 'CLT', jornada_trabalho: '', remuneracao: '',
      });
      
      setTimeout(() => {
        router.push('/'); 
      }, 2000);

    } catch (err) {
      setError("Erro ao cadastrar vaga. Tente novamente.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.formContainer}>
      <h1 className={styles.title}>Cadastrar Nova Vaga</h1>
      <p className={styles.subtitle}>
        Preencha as informações abaixo para registrar uma nova vaga de emprego.
      </p>

      <form onSubmit={handleSubmit} className={styles.form}>
        <div className={styles.grid}>
          {/* Coluna 1 */}
          <div className={styles.column}>
            <div className={styles.formGroup}>
              <label htmlFor="empresa">Empresa</label>
              <select id="empresa" name="empresa" value={formData.empresa} onChange={handleChange} required>
                <option value="">Selecione a empresa</option>
                {/* Agora usa 'nome_fantasia' como valor e chave */}
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
              <label htmlFor="regime">Regime de Contratação</label>
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
                {/* Agora usa 'nome' como valor e chave */}
                {cargos.map(c => (
                  <option key={c.nome} value={c.nome}>{c.nome}</option>
                ))}
              </select>
            </div>

             <div className={styles.formGroup}>
              <label htmlFor="estado">Estado</label>
              <select id="estado" name="estado" value={formData.estado} onChange={handleChange}>
                <option value="SP">SP</option>
                <option value="RJ">RJ</option>
                <option value="MG">MG</option>
                <option value="PR">PR</option>
                <option value="BA">BA</option>
                <option value="SC">SC</option>
                <option value="RS">RS</option>
                <option value="DF">DF</option>
              </select>
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="jornada_trabalho">Jornada de Trabalho</label>
              <input type="text" id="jornada_trabalho" name="jornada_trabalho" value={formData.jornada_trabalho} onChange={handleChange} placeholder="Ex: 40 horas semanais" />
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="remuneracao">Remuneração (R$)</label>
              <input type="text" id="remuneracao" name="remuneracao" value={formData.remuneracao} onChange={handleChange} placeholder="Ex: R$ 8.000,00" />
            </div>

             <div className={styles.formGroup}>
              <label htmlFor="conhecimentos_requeridos">Conhecimentos Requeridos</label>
              <textarea id="conhecimentos_requeridos" name="conhecimentos_requeridos" rows="5" value={formData.conhecimentos_requeridos} onChange={handleChange}></textarea>
            </div>
          </div>
        </div>

        {error && <p className={styles.messageError}>{error}</p>}
        {success && <p className={styles.messageSuccess}>{success}</p>}

        <button type="submit" className="primary" disabled={loading}>
          {loading ? "Cadastrando..." : "Cadastrar Vaga"}
        </button>
      </form>
    </div>
  );
}