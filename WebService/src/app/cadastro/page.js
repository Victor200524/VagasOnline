import TelaCadastroVaga from "@/components/TelaCadastroVaga";
import { getCargos, getEmpresas } from "@/service";

export default async function CadastroPage() {
  
  // Busca os dados no servidor antes de renderizar a página
  const empresas = await getEmpresas();
  const cargos = await getCargos();

  return (
    <div>
      <TelaCadastroVaga empresas={empresas} cargos={cargos} />
    </div>
  );
}