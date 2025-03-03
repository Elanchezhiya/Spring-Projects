import React, { useState, useContext } from "react";
import { useNavigate, Link } from "react-router-dom";
import { FaUser, FaEnvelope, FaLock, FaPhone, FaEye, FaEyeSlash } from "react-icons/fa";
import { toast } from "react-toastify";
import AuthContext from "../context/AuthContext";  // Import AuthContext

const Register = () => {
  const { register } = useContext(AuthContext);
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // Validation functions
  const validateEmail = (email) => /\S+@\S+\.\S+/.test(email);
  const validatePassword = (password) => password.length >= 6 && /[!@#$%^&*]/.test(password);
  const validateUsername = (username) => /^[A-Za-z ]{3,}$/.test(username.trim());
  const validatePhoneNumber = (phoneNumber) => /^\d{10}$/.test(phoneNumber);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    // Trim inputs
    const trimmedUsername = username.trim();
    const trimmedEmail = email.trim();
    const trimmedPhoneNumber = phoneNumber.trim();
    const trimmedPassword = password.trim();

    // Validation
    if (!validateUsername(trimmedUsername)) {
      toast.error("Username should contain only letters and be at least 3 characters.");
      setLoading(false);
      return;
    }
    if (!validateEmail(trimmedEmail)) {
      toast.error("Please enter a valid email.");
      setLoading(false);
      return;
    }
    if (!validatePhoneNumber(trimmedPhoneNumber)) {
      toast.error("Phone number must be exactly 10 digits.");
      setLoading(false);
      return;
    }
    if (!validatePassword(trimmedPassword)) {
      toast.error("Password must be at least 6 characters and include a special character (!@#$%^&*).");
      setLoading(false);
      return;
    }

    // API Call
    const response = await register({ username: trimmedUsername, email: trimmedEmail, password: trimmedPassword, phoneNumber: trimmedPhoneNumber });

    if (response.success) {
      toast.success("Registration successful! Redirecting...");
      navigate("/login");
      window.location.reload();
    } else {
      toast.error(response.message);
    }
    setLoading(false);
  };

  return (
    <div className="container d-flex justify-content-center align-items-center vh-100">
      <div className="card shadow p-4 w-50">
        <h2 className="text-center mb-4">Register</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Username</label>
            <div className="input-group">
              <span className="input-group-text"><FaUser /></span>
              <input type="text" className="form-control" placeholder="Enter full name" required value={username} onChange={(e) => setUsername(e.target.value)} />
            </div>
          </div>

          <div className="mb-3">
            <label className="form-label">Email</label>
            <div className="input-group">
              <span className="input-group-text"><FaEnvelope /></span>
              <input type="email" className="form-control" placeholder="Enter email" required value={email} onChange={(e) => setEmail(e.target.value)} />
            </div>
          </div>

          <div className="mb-3">
            <label className="form-label">Phone Number</label>
            <div className="input-group">
              <span className="input-group-text"><FaPhone /></span>
              <input type="text" className="form-control" placeholder="Enter phone number" required value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} />
            </div>
          </div>

          <div className="mb-3">
            <label className="form-label">Password</label>
            <div className="input-group">
              <span className="input-group-text"><FaLock /></span>
              <input type={showPassword ? "text" : "password"} className="form-control" placeholder="Enter password" required value={password} onChange={(e) => setPassword(e.target.value)} />
              <button type="button" className="btn btn-outline-secondary" onClick={() => setShowPassword(!showPassword)}>
                {showPassword ? <FaEyeSlash /> : <FaEye />}
              </button>
            </div>
          </div>

          <button type="submit" className="btn btn-primary w-100" disabled={loading}>
            {loading ? "Registering..." : "Register"}
          </button>
        </form>

        <p className="mt-3 text-center">
          Already have an account? <Link to="/login">Login here</Link>
        </p>
      </div>
    </div>
  );
};

export default Register;
