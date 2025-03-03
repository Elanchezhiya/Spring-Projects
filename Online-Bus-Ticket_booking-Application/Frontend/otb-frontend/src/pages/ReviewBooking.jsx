import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { FaBus, FaCheckCircle, FaArrowRight } from "react-icons/fa";
import "bootstrap/dist/css/bootstrap.min.css";

const ReviewBooking = () => {
  const { state } = useLocation();
  const { passengers, bus, selectedSeats } = state || { passengers: [], bus: {} };
  const navigate = useNavigate();

  // Fix: Ensure bus.price is valid
  const totalAmount = selectedSeats.length * (bus.price || 0);

  const handlePayment = () => {
    navigate("/payment", { state: { passengers, bus, selectedSeats, totalAmount } });
  };

  return (
    <div className="container mt-4">
      <h2 className="text-primary text-center mb-4 mt-5 fw-bold fs-1">
        <FaBus /> Review Your Booking
      </h2>
      <div className="card shadow p-3">
        <h4 className="text-success">
          <FaCheckCircle /> Bus: {bus.name} ({bus.source} → {bus.destination})
        </h4>
        <p>Departure Time: {bus.departureTime}</p>
        <p>Total Seats Selected: {selectedSeats.join(", ")}</p>
      </div>

      <h4 className="mt-4">Passengers:</h4>
      {passengers.map((p, index) => (
        <div key={index} className="card shadow p-2 mb-2">
          <p><strong>Name:</strong> {p.name} | <strong>Age:</strong> {p.age} | 
             <strong>Gender:</strong> {p.gender} </p>
          <p><strong>Email:</strong> {p.email} | <strong>Phone:</strong> {p.phoneNumber}</p>
        </div>
      ))}

      <div className="text-center mt-4">
        <button className="btn btn-success btn-lg" onClick={handlePayment}>
          Proceed to Payment <FaArrowRight />
        </button>
      </div>
    </div>
  );
};

export default ReviewBooking;
