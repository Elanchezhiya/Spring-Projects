import api from './api';

export const registerUser = (userData) => api.post('/auth/register', userData);

export const loginUser = (credentials) => api.post('/auth/login', credentials);

//export const logoutUser = () => api.post('/auth/logout');