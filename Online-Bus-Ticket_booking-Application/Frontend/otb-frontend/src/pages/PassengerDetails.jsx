import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Form, Button, Container } from "react-bootstrap";
import api from "../services/api";

const PassengerDetails = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { selectedSeats, bus } = location.state || {};
  
  const [passengers, setPassengers] = useState(
    selectedSeats.map(() => ({ name: "", age: "", gender: "", email: "", phoneNumber: "" }))
  );

  const handleInputChange = (index, field, value) => {
    const updatedPassengers = [...passengers];
    updatedPassengers[index][field] = value;
    setPassengers(updatedPassengers);
  };

  const handleConfirm = async () => {
    if (passengers.some(p => !p.name || !p.age || !p.gender || !p.email || !p.phoneNumber)) {
      alert("Please fill all passenger details!");
      return;
    }

    try {
      await Promise.all(
        passengers.map(passenger =>
          api.post("/passengers", {
            ...passenger,
            age: Number(passenger.age),
          })
        )
      );

      navigate(`/review-booking`, { state: { selectedSeats, passengers, bus } });
    } catch (error) {
      console.error("Error saving passengers:", error);
      alert("Failed to save passengers. Try again.");
    }
  };

  return (
    <Container className="mt-4">
      <h2 className="text-primary text-center mb-4 mt-5 fw-bold fs-1">Passenger Details</h2>
      <p className="text-center text-muted">
        <strong>{bus.source}</strong> → <strong>{bus.destination}</strong> | Seats: {selectedSeats.join(", ")}
      </p>

      {selectedSeats.map((seat, index) => (
        <Form key={index} className="border p-3 mb-3 rounded shadow-sm bg-light">
          <h5>Seat {seat}</h5>
          <Form.Group className="mb-2">
            <Form.Label>Name</Form.Label>
            <Form.Control
              type="text"
              value={passengers[index].name}
              onChange={(e) => handleInputChange(index, "name", e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group className="mb-2">
            <Form.Label>Age</Form.Label>
            <Form.Control
              type="number"
              value={passengers[index].age}
              onChange={(e) => handleInputChange(index, "age", e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group className="mb-2">
            <Form.Label>Gender</Form.Label>
            <Form.Select
              value={passengers[index].gender}
              onChange={(e) => handleInputChange(index, "gender", e.target.value)}
              required
            >
              <option value="">Select Gender</option>
              <option value="MALE">MALE</option>
              <option value="FEMALE">FEMALE</option>
              <option value="OTHER">OTHER</option>
            </Form.Select>
          </Form.Group>
          <Form.Group className="mb-2">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="email"
              value={passengers[index].email}
              onChange={(e) => handleInputChange(index, "email", e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group className="mb-2">
            <Form.Label>Phone Number</Form.Label>
            <Form.Control
              type="tel"
              value={passengers[index].phoneNumber}
              onChange={(e) => handleInputChange(index, "phoneNumber", e.target.value)}
              required
            />
          </Form.Group>
        </Form>
      ))}

      <div className="d-flex justify-content-between">
        <Button variant="secondary" onClick={() => navigate(-1)}>Back</Button>
        <Button variant="primary" onClick={handleConfirm}>Confirm & Proceed to Payment</Button>
      </div>
    </Container>
  );
};

export default PassengerDetails;
