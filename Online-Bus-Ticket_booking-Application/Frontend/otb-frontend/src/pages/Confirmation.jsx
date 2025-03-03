import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import { BsCheckCircle } from "react-icons/bs";

const Confirmation = () => {
  const navigate = useNavigate();
  const { bookingId } = useParams();

  return (
    <div className="container mt-5 text-center">
      <div className="card shadow-lg p-5">
        <h2 className="text-success">
          <BsCheckCircle className="me-2" /> Payment Successful!
        </h2>
        <p className="fs-5">Your booking ID is <strong>{bookingId}</strong></p>
        <button className="btn btn-primary" onClick={() => navigate("/")}>
          Go to Home
        </button>
      </div>
    </div>
  );
};

export default Confirmation;