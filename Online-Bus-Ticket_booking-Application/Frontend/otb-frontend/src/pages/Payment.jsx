import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { BsArrowRight, BsCreditCard2Back, BsArrowLeft } from "react-icons/bs";
import { motion } from "framer-motion";
import api from "../services/api"; // Import API service

const Payment = () => {
  const location = useLocation();
  const navigate = useNavigate();
  
  // Extract data safely with defaults
  const { bus = {}, selectedSeats = [] } = location.state || {}; 

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  // Redirect back if no bus or seats selected
  useEffect(() => {
    if (!bus?.id || !selectedSeats?.length) {
      console.warn("Missing bus or seat selection, redirecting...");
      navigate(-1);
    }
  }, [bus, selectedSeats, navigate]);

  const handlePayment = async () => {
    setLoading(true);
    setError("");

    const bookingData = {
      busId: bus.id,
      bookingDate: new Date().toISOString().split("T")[0], // Format: YYYY-MM-DD
      numberOfSeats: selectedSeats.length,
      totalAmount: bus.fare * selectedSeats.length,
      paymentStatus: "COMPLETED",
      selectedSeats: selectedSeats.join(","), // Convert array to string
    };

    try {
      const response = await api.post("/bookings", bookingData);
      
      if (response?.data?.id) {
        navigate(`/confirmation/${response.data.id}`, { state: { booking: response.data, bus, selectedSeats } });
      } else {
        throw new Error("Invalid response from server");
      }
    } catch (error) {
      console.error("Booking creation failed:", error);
      setError("Payment failed! Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <motion.div 
      className="container mt-5"
      initial={{ opacity: 0, y: -50 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <div className="card shadow-lg p-4">
        <h2 className="text-primary text-center mb-4 fw-bold fs-1">
          <BsCreditCard2Back className="me-2" /> Payment
        </h2>
        <p className="text-center text-muted">
          {bus.source || "Unknown"} <BsArrowRight /> {bus.destination || "Unknown"} | Departure: {bus.departureTime || "N/A"}
        </p>

        <div className="mt-3">
          <h4 className="text-secondary">Selected Seats</h4>
          <p className="fw-bold">{selectedSeats.length ? selectedSeats.join(", ") : "No seats selected"}</p>
        </div>

        <h4 className="mt-4 text-success">
          Total Amount: ₹{bus?.fare ? bus.fare * selectedSeats.length : "0"}
        </h4>

        {error && <p className="text-danger text-center mt-3">{error}</p>}

        <div className="text-center mt-4">
          <button className="btn btn-outline-secondary mx-2" onClick={() => navigate(-1)} disabled={loading}>
            <BsArrowLeft className="me-1" /> Back
          </button>
          <button 
            className="btn btn-success" 
            onClick={handlePayment} 
            disabled={loading}
          >
            {loading ? "Processing..." : "Confirm Payment"}
            <BsCreditCard2Back className="ms-1" />
          </button>
        </div>
      </div>
    </motion.div>
  );
};

export default Payment;
