import api from "./api";

const userService = {
  // ✅ Register User
  register: async (userData) => {
    try {
      const response = await api.post("/users/register", userData);
      return response.data;
    } catch (error) {
      throw error.response?.data || "Registration failed!";
    }
  },

  // ✅ Login User
  login: async (credentials) => {
    try {
      const response = await api.post("/auth/login", credentials);
      if (response.data.token) {
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("user", JSON.stringify(response.data.user));
      }
      return response.data;
    } catch (error) {
      throw error.response?.data || "Login failed!";
    }
  },

  // ✅ Get Current User Profile
  getCurrentUser: async () => {
    try {
      const response = await api.get("/users/me");
      return response.data;
    } catch (error) {
      throw error.response?.data || "Failed to fetch user data!";
    }
  },

  // ✅ Get User by ID
  getUserById: async (userId) => {
    try {
      const response = await api.get(`/users/${userId}`);
      return response.data;
    } catch (error) {
      throw error.response?.data || "User not found!";
    }
  },

  // ✅ Get All Users (Admin Only)
  getAllUsers: async () => {
    try {
      const response = await api.get("/users");
      return response.data;
    } catch (error) {
      throw error.response?.data || "Failed to fetch users!";
    }
  },

  // ✅ Update User
  updateUser: async (userData) => {
    try {
      const response = await api.put("/users/update", userData);
      return response.data;
    } catch (error) {
      throw error.response?.data || "Update failed!";
    }
  },

  // ✅ Delete User (Admin Only)
  deleteUser: async (userId) => {
    try {
      await api.delete(`/users/${userId}`);
      return { message: "User deleted successfully!" };
    } catch (error) {
      throw error.response?.data || "Delete failed!";
    }
  },

  // ✅ Change Password
  changePassword: async (newPassword) => {
    try {
      const response = await api.post("/users/change-password", { newPassword });
      return response.data;
    } catch (error) {
      throw error.response?.data || "Password change failed!";
    }
  },
};

export default userService;
