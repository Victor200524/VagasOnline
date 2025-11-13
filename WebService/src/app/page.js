import TelaTabelaVagas from "@/components/TelaTabelaVagas";
import { getVagas } from "@/service";

export default async function Home() {
  const vagas = await getVagas();
  
  return (
    <div>
      <TelaTabelaVagas vagasIniciais={vagas} />
    </div>
  );
}