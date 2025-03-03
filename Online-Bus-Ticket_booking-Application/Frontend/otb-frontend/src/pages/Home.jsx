import React, { useEffect, useState } from "react";
import { motion } from "framer-motion";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import BusList from "../components/BusList";
import { getAllBuses } from "../services/busService";
import "../assets/css/styles.css";
import { FaBus, FaSearch } from "react-icons/fa";

const Home = () => {
  const [buses, setBuses] = useState([]);
  const [filteredBuses, setFilteredBuses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");
  const isLoggedIn = localStorage.getItem("token") !== null;

  useEffect(() => {
    if (isLoggedIn) {
      const fetchBuses = async () => {
        try {
          const data = await getAllBuses();
          setBuses(data);
          setFilteredBuses(data);
        } catch (err) {
          setError("⚠️ Failed to load buses. Please try again later.");
        } finally {
          setLoading(false);
        }
      };
      fetchBuses();
    } else {
      setLoading(false);
    }
  }, [isLoggedIn]);

  // Handle Bus Search
  const handleSearch = (event) => {
    const query = event.target.value.toLowerCase();
    setSearchQuery(query);
    setFilteredBuses(
      buses.filter(
        (bus) =>
          bus.source.toLowerCase().includes(query) ||
          bus.destination.toLowerCase().includes(query)
      )
    );
  };

  return (
    <>
      <Navbar />

      {/* Hero Section */}
      <motion.header 
        className="hero text-center text-white bg-primary py-5"
        initial={{ opacity: 0, y: -50 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.7 }}
      >
        <h2 className="display-4 fw-bold mb-4 ">
          <FaBus className="me-2" /> Welcome to Online Bus Booking
        </h2>
        <p className="lead mb-4 ">Book your bus ticket, select your seat, and enjoy your journey.</p>
        {isLoggedIn ? (
          <motion.button 
            className="btn btn-light mt-3"
            onClick={() => window.scrollTo(0, 600)}
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
          >
            View Buses
          </motion.button>
        ) : (
          <p className="mt-3 ">Sign up to explore available buses and book tickets.</p>
        )}
      </motion.header>

      <main className="container my-4">
        {!isLoggedIn ? (
          <section className="text-center">
            <h3 className="text-primary">How It Works</h3>
            <p>1️⃣ Register an account.</p>
            <p>2️⃣ Search for available buses.</p>
            <p>3️⃣ Select your seat and confirm your booking.</p>
            <p>4️⃣ Enjoy your journey!</p>
            <a href="/register" className="btn btn-primary mt-3">Get Started</a>
          </section>
        ) : (
          <>
            <h3 className="text-center text-primary mb-3">Available Buses</h3>
            {/* Search Bar */}
            <div className="d-flex justify-content-center mb-3">
              <input 
                type="text" 
                className="form-control w-50" 
                placeholder="Search by source or destination..." 
                value={searchQuery}
                onChange={handleSearch}
              />
              <button className="btn btn-primary ms-2">
                <FaSearch />
              </button>
            </div>

            {/* Loading Spinner */}
            {loading && (
              <motion.div 
                className="text-center my-4"
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                transition={{ duration: 0.5 }}
              >
                <div className="spinner-border text-primary" role="status">
                  <span className="visually-hidden">Loading...</span>
                </div>
                <p className="mt-2">Fetching available buses...</p>
              </motion.div>
            )}

            {/* Error Message */}
            {error && <p className="alert alert-danger text-center">{error}</p>}

            {/* No Buses Available */}
            {!loading && !error && filteredBuses.length === 0 && (
              <p className="text-center text-muted">No buses available at the moment.</p>
            )}

            {/* Display Bus List */}
            {!loading && !error && filteredBuses.length > 0 && <BusList buses={filteredBuses} />}
          </>
        )}
      </main>

    </>
  );
};

export default Home;
