// src/app/cadastro/page.js
import TelaCadastroVaga from "@/components/TelaCadastroVaga";
import { getEmpresas, getCargos } from "@/service";

export default async function CadastroPage() {
  // Busca dados no servidor para os dropdowns
  const empresas = await getEmpresas();
  const cargos = await getCargos();

  return (
    <div>
      <TelaCadastroVaga empresas={empresas} cargos={cargos} />
    </div>
  );
}