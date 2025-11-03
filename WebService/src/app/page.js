// src/app/page.js
import TelaTabelaVagas from "@/components/TelaTabelaVagas";
import { getVagas } from "@/service"; // Importa do seu service

export default async function Home() {
  // Busca os dados no servidor (Server Component)
  const vagas = await getVagas();

  return (
    <div>
      {/* Passa as vagas como prop para o componente cliente */}
      <TelaTabelaVagas vagasIniciais={vagas} />
    </div>
  );
}