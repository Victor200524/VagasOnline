'use client';
import { useState } from 'react';
import { deleteVaga } from '../../service';
import styles from '../../styles/TelaTabelaVagas.module.css';
import { useRouter } from 'next/navigation';
import Link from 'next/link';

export default function TelaTabelaVagas({ vagasIniciais }) {
  const router = useRouter();
  const [vagas, setVagas] = useState(vagasIniciais || []);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleDelete = async (registro) => {
    if (confirm("Tem certeza que deseja excluir esta vaga?")) {

      setLoading(true);
      setError(null);

      try {
        const success = await deleteVaga(registro);

        if (success) {
          setVagas(vagas.filter(vaga => vaga.registro !== registro));
          router.refresh();
        } 
        else {
          throw new Error("Falha ao deletar a vaga.");
        }
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
  };

  let tableContent; // Conteúdo da tabela a ser renderizado

  if (!vagas || vagas.length === 0) {
    tableContent = (
      <tr>
        <td colSpan="6" className={styles.empty}>Nenhuma vaga cadastrada.</td>
      </tr>
    );
  } 
  else {
    tableContent = vagas.map((vaga) => (
      <tr key={vaga.registro}>
        <td>{vaga.cargo}</td>
        <td>{vaga.empresa?.nome_fantasia || 'N/A'}</td>
        <td>{vaga.cidade} - {vaga.estado}</td>
        <td>{vaga.regime}</td>
        <td>{vaga.remuneracao}</td>
        <td className={styles.actions}>
          <Link href={`../editar?registro=${vaga.registro}`} className={styles.editButton}>
            Editar
          </Link>
          <button
            className="danger"
            onClick={() => handleDelete(vaga.registro)}
            disabled={loading}
          >
            {loading ? "..." : "Excluir"}
          </button>
        </td>
      </tr>
    ));
  }

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
          {tableContent}
        </tbody>
      </table>
    </div>
  );
}