import api from './api';

export const getAllBuses = async () => {
    const response = await api.get('/buses');
    return response.data;
  };
  
  export const getBusById = async (busId) => {
    const response = await api.get(`/buses/${busId}`);
    return response.data;
  };
  
  export const searchBuses = async (source, destination, date) => {
    const response = await api.get(`/buses/available/${source}/${destination}/${date}`);
    return response.data;
  };
  
  export const addBus = async (busData) => {
    const response = await api.post('/buses', busData);
    return response.data;
  };
  
  export const updateBus = async (busData) => {
    const response = await api.put(`/buses/${busData.id}`, busData);
    return response.data;
  };
  
  export const deleteBus = async (busId) => {
    await api.delete(`/buses/${busId}`);
  };
  
  export const findBusesByRoute = async (source, destination) => {
    const response = await api.get(`/buses/search/${source}/${destination}`);
    return response.data;
  };
  
  export const findAvailableBuses = async (source, destination, date) => {
    const response = await api.get(`/buses/available/${source}/${destination}/${date}`);
    return response.data;
  };
  
  export const updateSeatAvailability = async (busId, seatsBooked) => {
    const response = await api.post(`/buses/${busId}/update-seats?seatsBooked=${seatsBooked}`);
    return response.data;
  };