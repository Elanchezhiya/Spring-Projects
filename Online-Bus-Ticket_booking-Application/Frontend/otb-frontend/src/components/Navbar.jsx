import React, { useContext, useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import {
  FaBus,
  FaUser,
  FaSignInAlt,
  FaUserPlus,
  FaSignOutAlt,
  FaUserShield,
  FaSun,
  FaMoon,
} from "react-icons/fa";

const Navbar = () => {
  const { auth, logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const [darkMode, setDarkMode] = useState(() => localStorage.getItem("darkMode") === "true");

  useEffect(() => {
    document.body.classList.toggle("dark-theme", darkMode);
  }, [darkMode]);

  const handleLogout = () => {
    logout();
    navigate("/", { replace: true });
    window.location.reload(); // Ensure state updates before navigation
  };

  const toggleTheme = () => {
    setDarkMode((prev) => {
      const newMode = !prev;
      localStorage.setItem("darkMode", newMode);
      document.body.classList.toggle("dark-theme", newMode);
      return newMode;
    });
  };

  return (
    <nav className={`navbar navbar-expand-lg ${darkMode ? "navbar-dark bg-dark" : "navbar-light bg-light"} fixed-top shadow`}>
      <div className="container">
        <Link className="navbar-brand fw-bold d-flex align-items-center" to="/">
          <FaBus className="me-2 text-warning" /> Elan Bus Booking
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/">
                <FaBus className="me-1" /> Home
              </Link>
            </li>

            {auth.token ? (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/profile">
                    <FaUser className="me-1" /> {auth.user?.name || auth.user?.username}
                  </Link>
                </li>

                {auth.user?.role === "ADMIN" && (
                  <li className="nav-item">
                    <Link className="nav-link" to="/admin">
                      <FaUserShield className="me-1" /> Admin Dashboard
                    </Link>
                  </li>
                )}

                <li className="nav-item">
                  <button className="btn btn-outline-danger ms-3" onClick={handleLogout}>
                    <FaSignOutAlt className="me-1" /> Logout
                  </button>
                </li>
              </>
            ) : (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/login">
                    <FaSignInAlt className="me-1" /> Login
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/register">
                    <FaUserPlus className="me-1" /> Register
                  </Link>
                </li>
              </>
            )}

            <li className="nav-item">
              <button className="btn btn-outline-secondary ms-3" onClick={toggleTheme}>
                {darkMode ? <FaSun className="me-1" /> : <FaMoon className="me-1" />} {darkMode ? "Light" : "Dark"} Mode
              </button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
