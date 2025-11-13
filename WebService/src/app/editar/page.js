import TelaCadastroVaga from "../../components/TelaCadastroVaga";
import { getEmpresas, getCargos, getVagaByRegistro } from "@/service";
import { headers } from 'next/headers';

export default async function EditarPage({ searchParams }) {
  headers();
  const { registro } = await searchParams; //le o registo da URL

  if (!registro) {
    return (
      <div style={{padding: '2rem', textAlign: 'center'}}>
        <h1>Nenhuma vaga selecionada</h1>
        <p>Volte para a lista e clique em "Editar" em uma vaga.</p>
      </div>
    );
  }

  try {
    //Buscamos TODOS os dados necessários
    const [vaga, empresas, cargos] = await Promise.all([
      getVagaByRegistro(registro), // Busca a vaga específica
      getEmpresas(),             // Busca todas as empresas
      getCargos()                // Busca todos os cargos
    ]);

    return (
      <TelaCadastroVaga 
        empresas={empresas} 
        cargos={cargos} 
        vagaParaEditar={vaga} // Passa a vaga que será editada
      />
    );

  } catch (error) {
    console.error("Falha ao carregar dados da página de edição:", error);
    return (
      <div style={{padding: '2rem', textAlign: 'center'}}>
        <h1>Erro ao carregar vaga</h1>
        <p>Não foi possível encontrar os dados da vaga. Verifique se o backend está rodando.</p>
      </div>
    );
  }
}