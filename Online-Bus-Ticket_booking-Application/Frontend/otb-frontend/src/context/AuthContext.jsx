import React, { createContext, useState, useEffect } from "react";
import { registerUser } from "../services/authService"; 

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(() => {
    const token = localStorage.getItem("token") || null;
    const user = JSON.parse(localStorage.getItem("user")) || { id: null, role: "USER" };
    return { token, user };
  });

  useEffect(() => {
    if (auth.token) {
      localStorage.setItem("token", auth.token);
      localStorage.setItem("user", JSON.stringify(auth.user));
    } else {
      localStorage.removeItem("token");
      localStorage.removeItem("user");
    }
  }, [auth]);

  const login = (token, userData) => {
    setAuth({ token, user: { ...userData } });  // Ensure all user details are stored
  };

  const logout = () => {
    setAuth({ token: null, user: { id: null, role: "USER" } });
    localStorage.removeItem("token"); 
    localStorage.removeItem("user");  
  };

  const register = async (userData) => {
    try {
      const response = await registerUser(userData);
      const { token, user } = response.data;
      setAuth({ token, user });
      return { success: true };
    } catch (error) {
      console.error("Registration failed:", error);
      return { success: false, message: error.response?.data?.message || "Registration failed" };
    }
  };

  return (
    <AuthContext.Provider value={{ auth, login, logout, register }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
