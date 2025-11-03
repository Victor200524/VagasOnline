// src/app/service/index.js

// --- SIMULAÇÃO DE DADOS (Baseado nos seus JSONs) ---

// Dados de: empresas.json
const MOCK_EMPRESAS = [
  { "nome_fantasia": "Tech Solutions", "razao_social": "Tech Solutions LTDA", "tipo": "nacional" },
  { "nome_fantasia": "Inova Tech", "razao_social": "Inova Tech S/A", "tipo": "multinacional" },
  { "nome_fantasia": "Web Creators", "razao_social": "Web Creators LTDA", "tipo": "nacional" },
  { "nome_fantasia": "Cyber Security Corp", "razao_social": "Cyber Security Corporation", "tipo": "multinacional" },
  { "nome_fantasia": "Data Analytics Ltda", "razao_social": "Data Analytics LTDA", "tipo": "nacional" },
  // ... (Adicione mais se quiser, copiado do seu JSON)
];

// Dados de: cargos.json
const MOCK_CARGOS = [
  {"nome":"Desenvolvedor Full Stack"},
  {"nome":"Analista de Dados"},
  {"nome":"Designer Gráfico"},
  {"nome":"Especialista em Segurança da Informação"},
  {"nome":"Estagiário em Análise de Dados"},
  {"nome":"Gerente de Projetos"},
  {"nome":"Desenvolvedor Mobile"},
  {"nome":"Cientista de Dados"},
  {"nome":"Analista de Marketing Digital"},
  {"nome":"Engenheiro de Robótica"},
  // ... (Adicione mais se quiser, copiado do seu JSON)
];

// Dados de: vagas.json
let MOCK_VAGAS = [
  {
    "registro":"TECDFS",
    "empresa": { "nome_fantasia": "Tech Solutions", "razao_social": "Tech Solutions LTDA", "tipo": "nacional" },
    "cargo": "Desenvolvedor Full Stack",
    "cidade": "São Paulo",
    "estado": "SP",
    "pre_requisitos": "Experiência em desenvolvimento ágil",
    "formacao": "Superior Completo em Ciência da Computação",
    "conhecimentos_requeridos": "JavaScript, Node.js, React",
    "regime": "CLT",
    "jornada_trabalho": "40 horas semanais",
    "remuneracao": "R$ 8.000,00"
  },
  {
    "registro":"INOAD",
    "empresa": { "nome_fantasia": "Inova Tech", "razao_social": "Inova Tech S/A", "tipo": "multinacional" },
    "cargo": "Analista de Dados",
    "cidade": "Campinas",
    "estado": "SP",
    "pre_requisitos": "Conhecimento em Big Data",
    "formacao": "Superior em Engenharia da Computação ou áreas afins",
    "conhecimentos_requeridos": "Python, SQL, R",
    "regime": "PJ",
    "jornada_trabalho": "40 horas semanais",
    "remuneracao": "R$ 10.000,00"
  },
  {
    "registro":"WCRDG",
    "empresa": { "nome_fantasia": "Web Creators", "razao_social": "Web Creators LTDA", "tipo": "nacional" },
    "cargo": "Designer Gráfico",
    "cidade": "Rio de Janeiro",
    "estado": "RJ",
    "pre_requisitos": "Portfólio com trabalhos anteriores",
    "formacao": "Superior em Design ou áreas relacionadas",
    "conhecimentos_requeridos": "Adobe Photoshop, Illustrator",
    "regime": "CLT",
    "jornada_trabalho": "38 horas semanais",
    "remuneracao": "R$ 6.000,00"
  },
  // ... (Adicione mais se quiser, copiado do seu JSON)
];
// --- FIM DA SIMULAÇÃO ---


// Funções do serviço (agora usam os dados reais)

export const getEmpresas = async () => {
  // Quando o backend estiver pronto:
  // return fetch(`${API_URL}/empresas`).then(res => res.json());
  return Promise.resolve(MOCK_EMPRESAS);
};

export const getCargos = async () => {
  // Quando o backend estiver pronto:
  // return fetch(`${API_URL}/cargos`).then(res => res.json());
  return Promise.resolve(MOCK_CARGOS);
};

export const getVagas = async () => {
  // Quando o backend estiver pronto:
  // return fetch(`${API_URL}/vagas`).then(res => res.json());
  return Promise.resolve(MOCK_VAGAS);
};

export const createVaga = async (vagaData) => {
  // vagaData virá do formulário. Precisamos formatá-lo como o vagas.json
  
  // 1. Encontrar o objeto empresa completo com base no nome_fantasia
  const empresaObj = MOCK_EMPRESAS.find(e => e.nome_fantasia === vagaData.empresa);

  // 2. Gerar um registro aleatório
  const registro = Math.random().toString(36).substring(2, 8).toUpperCase();

  const novaVaga = {
    registro: registro,
    empresa: empresaObj, // Embeda o objeto empresa
    cargo: vagaData.cargo, // O cargo já é a string correta
    cidade: vagaData.cidade,
    estado: vagaData.estado,
    pre_requisitos: vagaData.pre_requisitos,
    formacao: vagaData.formacao,
    conhecimentos_requeridos: vagaData.conhecimentos_requeridos,
    regime: vagaData.regime,
    jornada_trabalho: vagaData.jornada_trabalho,
    remuneracao: vagaData.remuneracao // O formulário agora envia a string "R$ 8.000,00"
  };

  // Quando o backend estiver pronto:
  // const response = await fetch(`${API_URL}/vagas`, {
  //   method: 'POST',
  //   headers: { 'Content-Type': 'application/json' },
  //   body: JSON.stringify(novaVaga),
  // });
  // return response.json();

  // Simulação
  console.log("CRIANDO VAGA:", novaVaga);
  MOCK_VAGAS.push(novaVaga);
  return Promise.resolve(novaVaga);
};

export const deleteVaga = async (registro) => { // Agora usamos 'registro' como ID
  // Quando o backend estiver pronto:
  // const response = await fetch(`${API_URL}/vagas/${registro}`, {
  //   method: 'DELETE',
  // });
  // return response.ok;

  // Simulação
  console.log("DELETANDO VAGA:", registro);
  MOCK_VAGAS = MOCK_VAGAS.filter(v => v.registro !== registro);
  return Promise.resolve(true);
};