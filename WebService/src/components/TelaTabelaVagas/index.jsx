// src/app/components/TelaTabelaVagas/index.jsx
'use client'; 

import { useState } from 'react';
import { deleteVaga } from '@/service'; 
import styles from '../../styles/TelaTabelaVagas.module.css';

export default function TelaTabelaVagas({ vagasIniciais }) {
  const [vagas, setVagas] = useState(vagasIniciais);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleDelete = async (registro) => {
    if (!confirm("Tem certeza que deseja excluir esta vaga?")) {
      return;
    }

    setLoading(true);
    setError(null);
    
    try {
      const success = await deleteVaga(registro);
      if (success) {
        setVagas(vagas.filter(vaga => vaga.registro !== registro));
      } else {
        throw new Error("Falha ao deletar a vaga.");
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // --- INÍCIO DA CORREÇÃO ---
  // Vamos criar o conteúdo do <tbody> em uma variável
  
  let tableContent;

  if (vagas.length === 0) {
    tableContent = (
      <tr className={styles.emptyRow}>
        <td colSpan="6" className={styles.empty}>Nenhuma vaga cadastrada.</td>
      </tr>
    );
  } else {
    tableContent = vagas.map((vaga) => (
      // Removi os comentários JSX daqui de dentro para garantir
      <tr key={vaga.registro}>
        <td>{vaga.cargo}</td>
        <td>{vaga.empresa?.nome_fantasia || 'N/A'}</td>
        <td>{vaga.cidade} - {vaga.estado}</td>
        <td>{vaga.regime}</td>
        <td>{vaga.remuneracao}</td>
        <td>
          <button 
            className="danger"
            onClick={() => handleDelete(vaga.registro)}
            disabled={loading}
          >
            Excluir
          </button>
        </td>
      </tr>
    ));
  }
  // --- FIM DA CORREÇÃO ---

  return (
    <div className={styles.tableContainer}>
      <h1 className={styles.title}>Vagas Disponíveis</h1>
      
      {error && <p className={styles.error}>{error}</p>}
      
      <table className={styles.table}>
        <thead>
          <tr>
            <th>Cargo</th>
            <th>Empresa</th>
            <th>Localização</th>
            <th>Regime</th>
            <th>Remuneração</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {/* Agora o tbody renderiza apenas a variável, 
              o que evita o erro de hidratação. */}
          {tableContent}
        </tbody>
      </table>
    </div>
  );
}