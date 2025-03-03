import api from './api';


export const createBooking = async (bookingData) => {
  const response = await api.post('/bookings', bookingData);
  return response.data;
};

export const getAllBookings = async () => {
  const response = await api.get('/bookings');
  return response.data;
};

export const cancelBooking = async (bookingId) => {
  await api.delete(`/bookings/${bookingId}`);
};

export const confirmBooking = async (bookingId) => {
  const response = await api.post(`/bookings/confirm/${bookingId}`);
  return response.data;
};

// export const getBookingsByUser = async (userId) => {
//   const response = await api.get(`/bookings/user/${userId}`);
//   return response.data;
// };

export const calculateFare = async (busId, seats) => {
  const response = await api.get(`/bookings/fare/${busId}/${seats}`);
  return response.data;
};
