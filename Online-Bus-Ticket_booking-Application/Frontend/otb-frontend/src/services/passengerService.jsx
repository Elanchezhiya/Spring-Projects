import api from './api';

export const addPassenger = async (passengerData) => {
  const response = await api.post('/passengers', passengerData);
  return response.data;
};


export const deletePassenger = async (passengerId) => {
  await api.delete(`/passengers/${passengerId}`);
};

export const updatePassenger = async (passengerId, passengerData) => {
  const response = await api.put(`/passengers/${passengerId}`, passengerData);
  return response.data;
};

export const getAllPassengers = async () => {
  const response = await api.get('/passengers/all');
  return response.data;
};


