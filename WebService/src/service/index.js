const API_URL = 'http://localhost:8080/apis'; 

export const getEmpresas = async () => {
  const res = await fetch(`${API_URL}/empresas/get-all`);
  if (!res.ok) throw new Error('Falha ao buscar empresas');
  return res.json();
};

export const getCargos = async () => {
  const res = await fetch(`${API_URL}/cargos/get-all`);
  if (!res.ok) throw new Error('Falha ao buscar cargos');
  return res.json();
};

export const getVagas = async () => {
  const res = await fetch(`${API_URL}/vagas/get-all`);
  if (!res.ok) throw new Error('Falha ao buscar vagas');
  return res.json();
};

export const createVaga = async (vagaData) => {
  const response = await fetch(`${API_URL}/vagas`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(vagaData),
  });
  if (!response.ok) throw new Error('Falha ao criar vaga');
  return response.json();
};

export const getVagaByRegistro = async (registro) => {
  const res = await fetch(`${API_URL}/vagas/${registro}`);
  if (!res.ok) throw new Error('Falha ao buscar vaga específica');
  return res.json();
};

export const updateVaga = async (registro, vagaData) => {
  const response = await fetch(`${API_URL}/vagas/${registro}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(vagaData),
  });
  if (!response.ok) throw new Error('Falha ao atualizar vaga');
  return response.json();
};

export const deleteVaga = async (registro) => {
  const response = await fetch(`${API_URL}/vagas/${registro}`, {
    method: 'DELETE',
  });
  if (!response.ok) throw new Error('Falha ao deletar vaga');
  return response.ok;
};