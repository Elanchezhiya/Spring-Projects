import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import { toast } from "react-toastify";
import { FaUser, FaEnvelope, FaPhone, FaEdit, FaSave, FaCamera, FaIdBadge, FaArrowLeft, FaMoon, FaSun } from "react-icons/fa";
import { Spinner } from "react-bootstrap";

const Profile = () => {
  const [user, setUser] = useState(null);
  const [editMode, setEditMode] = useState(false);
  const [updatedUser, setUpdatedUser] = useState({});
  const [loading, setLoading] = useState(true);
  const [previewImage, setPreviewImage] = useState("");
  const [isDarkMode, setIsDarkMode] = useState(localStorage.getItem("darkMode") === "true");
  const navigate = useNavigate();

  useEffect(() => {
    fetchUserProfile();
  }, []);

  const fetchUserProfile = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        toast.error("Authentication required");
        navigate("/login");
        return;
      }
  
      console.log("Fetching user profile...");
      const response = await api.get("/users/me", {
        headers: { Authorization: `Bearer ${token}` },
      });
  
      console.log("API Response:", response.data);
  
      if (response.data) {
        const {  username, email, phoneNumber, profilePicture } = response.data;
        setUser({ username, email, phoneNumber, profilePicture });
        setUpdatedUser({ username, phoneNumber, profilePicture });
        setPreviewImage(profilePicture || "https://cdn.pixabay.com/photo/2023/05/02/10/35/avatar-7964945_1280.png");
      } else {
        toast.error("Invalid response from server");
      }
    } catch (error) {
      console.error("Error fetching profile:", error);
      toast.error("Failed to load profile.");
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateProfile = async () => {
    if (!updatedUser.name.trim() || !updatedUser.phone.trim()) {
      toast.error("Name and Phone cannot be empty!");
      return;
    }

    try {
      const token = localStorage.getItem("token");
      await api.put("/users/update", updatedUser, {
        headers: { Authorization: `Bearer ${token}` }, // Include token
      });

      setUser({ ...user, ...updatedUser });
      setEditMode(false);
      toast.success("Profile updated successfully!");
    } catch (error) {
      console.error("Error updating profile:", error);
      toast.error("Failed to update profile.");
    }
  };

  const handleFileUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => setPreviewImage(reader.result);
      reader.readAsDataURL(file);
    }
  };

  const toggleDarkMode = () => {
    const newMode = !isDarkMode;
    setIsDarkMode(newMode);
    localStorage.setItem("darkMode", newMode);
  };

  if (loading) {
    return <Spinner animation="border" className="d-block mx-auto mt-5 text-primary" />;
  }

  return (
    <div className={`container mt-5 ${isDarkMode ? "bg-dark text-white" : "bg-light text-dark"} p-4 rounded`}>
      <div className="card shadow-lg p-4" style={{ backgroundColor: isDarkMode ? "#333" : "#fff", color: isDarkMode ? "#fff" : "#000" }}>
        <div className="d-flex justify-content-between align-items-center">
          <button className="btn btn-outline-primary" onClick={() => navigate(-1)}>
            <FaArrowLeft className="me-2" /> Back
          </button>
          <h2 className="text-center">
            <FaUser className="me-2" /> My Profile
          </h2>
          <button className="btn btn-outline-warning" onClick={toggleDarkMode}>
            {isDarkMode ? <FaSun className="me-2" /> : <FaMoon className="me-2" />} {isDarkMode ? "Light Mode" : "Dark Mode"}
          </button>
        </div>

        {/* Profile Picture Section */}
        <div className="text-center mb-3">
          <img src={previewImage} alt="Profile" className="rounded-circle" width="100" height="100" />
          {editMode && (
            <div className="mt-2">
              <input type="file" className="d-none" id="fileUpload" onChange={handleFileUpload} />
              <label htmlFor="fileUpload" className="btn btn-sm btn-outline-secondary">
                <FaCamera className="me-1" /> Change Picture
              </label>
            </div>
          )}
        </div>

        <div className="card-body">
          {/* Name Field */}
          <div className="mb-3">
            <label className="form-label">
              <FaUser className="me-2" /> Name
            </label>
            {editMode ? (
              <input
                type="text"
                className="form-control"
                value={updatedUser.name}
                onChange={(e) => setUpdatedUser({ ...updatedUser, name: e.target.value })}
              />
            ) : (
              <p className="form-control-plaintext">{user.username}</p>
            )}
          </div>

          {/* Email Field */}
          <div className="mb-3">
            <label className="form-label">
              <FaEnvelope className="me-2" /> Email
            </label>
            <p className="form-control-plaintext">{user.email}</p>
          </div>

          {/* Phone Field */}
          <div className="mb-3">
            <label className="form-label">
              <FaPhone className="me-2" /> Phone
            </label>
            {editMode ? (
              <input
                type="text"
                className="form-control"
                value={updatedUser.phone}
                onChange={(e) => setUpdatedUser({ ...updatedUser, phone: e.target.value })}
              />
            ) : (
              <p className="form-control-plaintext">{user.phoneNumber}</p>
            )}
          </div>

          {/* Buttons */}
          <div className="d-flex justify-content-between">
            {editMode ? (
              <button className="btn btn-success" onClick={handleUpdateProfile}>
                <FaSave className="me-2" /> Save
              </button>
            ) : (
              <button className="btn btn-primary" onClick={() => setEditMode(true)}>
                <FaEdit className="me-2" /> Edit Profile
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
