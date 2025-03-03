import React from "react";
import { useNavigate } from "react-router-dom";
import { FaBus, FaMapMarkerAlt, FaClock, FaChair, FaRupeeSign } from "react-icons/fa";

const BusList = ({ buses }) => {
  const navigate = useNavigate();

  return (
    <div className="container mt-4">
      <div className="row">
        {buses.map((bus) => (
          <div key={bus.id} className="col-md-6 col-lg-4 mb-4">
            <div className="card shadow-lg">
              <div className="card-body">
                <h5 className="card-title">
                  <FaBus className="text-primary me-2" />
                  {bus.busNumber} ({bus.busType})
                </h5>
                <p className="card-text">
                  <FaMapMarkerAlt className="text-danger me-2" />
                  {bus.source} → {bus.destination}
                </p>
                <p className="card-text">
                  <FaClock className="text-success me-2" />
                  {bus.departureTime} - {bus.arrivalTime}
                </p>
                <p className="card-text">
                  <FaChair className="text-warning me-2" />
                  Seats Available: <strong>{bus.availableSeats}</strong>
                </p>
                <p className="card-text">
                  <FaRupeeSign className="text-info me-2" />
                  Fare: <strong>₹{bus.fare.toFixed(2)}</strong>
                </p>
                <button 
                  className="btn btn-primary w-100 mt-2"
                  onClick={() => navigate(`/booking/${bus.id}`)}
                >
                  Book Now
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default BusList;
