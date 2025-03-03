import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../services/api";
import { FaChair } from "react-icons/fa";
import { Button, Container, Row, Col } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

const SeatSelection = () => {
  const { busId } = useParams();
  const navigate = useNavigate();
  const [bus, setBus] = useState(null);
  const [selectedSeats, setSelectedSeats] = useState([]);

  useEffect(() => {
    api.get(`/buses/${busId}`)
      .then((res) => setBus(res.data))
      .catch((err) => console.error(err));
  }, [busId]);

  const toggleSeat = (seatNumber) => {
    setSelectedSeats((prev) =>
      prev.includes(seatNumber)
        ? prev.filter((s) => s !== seatNumber)
        : [...prev, seatNumber]
    );
  };

  const handleProceed = () => {
    if (selectedSeats.length === 0) {
      alert("Please select at least one seat!");
      return;
    }
    navigate(`/passenger-details`, { state: { bus, selectedSeats } });
  };

  if (!bus) return <div className="text-center mt-4">Loading bus details...</div>;

  return (
    <Container className="mt-4">
      <h2 className="text-primary text-center mb-4 mt-5 fw-bold fs-1">Select Your Seats</h2>
      <p className="text-center text-muted">
        <strong>{bus.source}</strong> → <strong>{bus.destination}</strong> | Departure: {bus.departureTime}
      </p>

      <Row className="justify-content-center">
        <Col md={8} className="d-flex flex-wrap justify-content-center">
          {[...Array(bus.availableSeats)].map((_, i) => (
            <Button
              key={i}
              variant={selectedSeats.includes(i + 1) ? "success" : "outline-success"}
              className="m-2 seat-button"
              onClick={() => toggleSeat(i + 1)}
            >
              <FaChair className="me-2" /> {i + 1}
            </Button>
          ))}
        </Col>
      </Row>

      <div className="text-center mt-4">
        <Button variant="primary" size="lg" onClick={handleProceed}>
          Proceed to Passenger Details <i className="fas fa-arrow-right ms-2"></i>
        </Button>
      </div>
    </Container>
  );
};

export default SeatSelection;
